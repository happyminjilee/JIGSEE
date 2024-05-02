import 'package:dio/dio.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

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
    options.headers["Authorization"] = "Bearer $accessToken";
    return super.onRequest(options, handler);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    if (err.response?.statusCode == 401) {
      RequestOptions options = err.response!.requestOptions;

      var refreshToken = await secureStorage.read(key: "refreshToken");
      var newAccessToken = await refreshAccessToken(refreshToken!);
      await secureStorage.write(key: "accessToken", value: newAccessToken);

      options.headers["Authorization"] = newAccessToken;
      dio.fetch(options).then(
            (r) => handler.resolve(r),
        onError: (e) => handler.reject(e),
      );
    } else {
      return super.onError(err, handler);
    }
  }

  Future<String> refreshAccessToken(String refreshToken) async {
    // 여기에 토큰을 갱신하는 API 요청을 구현하세요.
    // 이 예제에서는 간단히 새 토큰을 반환하고 있습니다.
    await secureStorage.deleteAll();
    return "new_access_token";
  }
}
