import 'dart:convert';
import 'dart:developer';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;
import 'package:jigsee/consts/constants.dart';
import 'package:jigsee/stores/usePreference.dart';

class AuthService {
  final _storage = const FlutterSecureStorage();
  final String apiUrl = Constants.backUrl;

  Map<String, String> headers = {
    'content-type' : 'application/json',
  };
  
  /// 로그인 함수
  /// return bool
  Future<bool> login(String employeeNo, String password) async {
    UserPreference().initSharedPreferences();

    try {
      var response = await http.post(
        Uri.parse(Constants.backUrl + '/login'),
        headers: headers,
        body: jsonEncode({'employeeNo': employeeNo, 'password': password}),
      );

      if (response.statusCode == 200) {
        var data = jsonDecode(response.body);
        await _storage.write(key: 'accessToken', value: response.headers['authorization']);
        await _storage.write(key: 'refreshToken', value: response.headers['refreshtoken']);
        await _storage.write(key: 'userName', value: data['result']['name']);
        await _storage.write(key: 'employeeNo', value: data['result']['employeeNo']);
        await _storage.write(key: 'role', value: data['result']['role']);

        return true;
      }
      return false;
    } catch (e) {
      log('Login error: $e');
      return false;
    }
  }

  /// 로그인 유지 체크 함수
  /// 로그인 여부를 판단한 후 로그인 되어 있는 상태면 accessToken 을 재발급 받는다
  /// return bool
  Future<bool> isLogin() async {
    try {
      final String? refreshToken = await _storage.read(key:'refreshToken');
      var response = await http.get(
        Uri.parse(Constants.backUrl + '/refresh'),
        headers: {'Authorization' : '$refreshToken'}
      );
      if (response.statusCode == 200) {
        await _storage.write(key:'accessToken', value: response.headers['authorization']);
        await _storage.write(key:'refreshToken', value: response.headers['refreshtoken']);
      } else if (response.statusCode == 401) {
        await logout();
        return false;
      }
      return true;
    } catch(e) {
      await logout();
      return false;
    }
  }

  Future<void> logout() async {
    await _storage.deleteAll();
    // Additional logout operations like navigating to the login screen
  }
}
