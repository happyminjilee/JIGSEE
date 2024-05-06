import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:jigsee/consts/constants.dart';

class DioClient {
  late final Dio dio;
  final String baseUrl;
  final FlutterSecureStorage secureStorage;

  DioClient({required this.baseUrl, required this.secureStorage}) {
    dio = Dio(BaseOptions(
      baseUrl: baseUrl,
      connectTimeout: const Duration(seconds: 5),
      receiveTimeout: const Duration(seconds: 3),
    ));

    dio.interceptors.add(TokenInterceptor(dio, secureStorage));
  }

  Future<Response> get(String path, {Map<String, dynamic>? queryParameters}) async {
    return dio.get(path, queryParameters: queryParameters);
  }

  Future<Response> post(String path, {data, Map<String, dynamic>? queryParameters}) async {
    return dio.post(path, data: data, queryParameters: queryParameters);
  }
}

class TokenInterceptor extends Interceptor {
  Dio dio;
  final FlutterSecureStorage secureStorage;

  TokenInterceptor(this.dio, this.secureStorage);

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    var accessToken = await secureStorage.read(key: "accessToken");
    options.headers["Authorization"] = accessToken;
    return super.onRequest(options, handler);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    if (err.response?.statusCode == 401) {
      RequestOptions options = err.response!.requestOptions;

      var refreshToken = await secureStorage.read(key: "refreshToken");
      await refreshAccessToken(refreshToken!);

      options.headers["Authorization"] = await secureStorage.read(key: 'accessToken');
      dio.fetch(options).then(
            (r) => handler.resolve(r),
        onError: (e) => handler.reject(e),
      );
    } else {
      return super.onError(err, handler);
    }
  }

  Future<void> refreshAccessToken(String refreshToken) async {
    // url = /refresh
    Map<String, String> headers = {
      'content-type' : 'application/json',
      'refreshToken' : refreshToken,
    };

    var response = await http.post(
      Uri.parse(Constants.backUrl + '/refresh'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      await secureStorage.write(key: 'accessToken', value: response.headers['authorization']);
      await secureStorage.write(key: 'refreshToken', value: response.headers['refreshtoken']);
    }
  }
}
