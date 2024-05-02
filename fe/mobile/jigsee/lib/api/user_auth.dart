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

  Future<bool> login(String employeeNo, String password) async {
    UserPreference().initSharedPreferences();

    try {
      var response = await http.post(
        Uri.parse(Constants.backUrl + '/login'),
        headers: headers,
        body: jsonEncode({'employeeNo': employeeNo, 'password': password}),
      );

      if (response.statusCode == 200) {
        String accessToken = response.headers['authorization']!;
        String refreshToken = response.headers['refreshtoken']!;

        await _storage.write(key: 'accessToken', value: accessToken);
        await _storage.write(key: 'refreshToken', value: refreshToken);
        await _storage.write(key: 'userName', value: response.body['result']['name']);
        await _storage.write(key: 'employeeNo', value: response.body['result']['employeeNo']);
        await _storage.write(key: 'role', value: response.body['result']['role']);

        // await UserPreference().saveData('userName', info['name']);
        // await UserPreference().saveData('empNo', info['employeeNo']);
        // await UserPreference().saveData('role', info['role']);

        return true;
      }
      return false;
    } catch (e) {
      log('Login error: $e');
      return false;
    }
  }

  /// 로그인 유지 체크 함수
  Future<bool> isLogin() async {
    try {
      final String? refreshToken = await _storage.read(key:'refreshToken');
      var response = await http.get(
        Uri.parse(Constants.backUrl + '/refresh'),
        headers: {'Authorization' : '$refreshToken'}
      );
      if (response.statusCode == 200) {
        await _storage.write(key:'accessToken', value: response.headers['AccessToken']);
        await _storage.write(key:'refreshToken', value: response.headers['RefreshToken']);
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

  Future<bool> validateToken(String accessToken) async {
    try {
      var response = await http.get(
        Uri.parse(Constants.backUrl + '/refresh'),
        headers: {'Authorization': accessToken},
      );

      if (response.statusCode == 200) {
        return true; // Access token is valid
      } else if (response.statusCode == 401) {
        // Access token is invalid, try to refresh it
        return await isValidRefreshToken();
      }
      return false;
    } catch (e) {
      log('Token validation error: $e');
      return false;
    }
  }

  Future<bool> isValidRefreshToken() async {
    String? refreshToken = await _storage.read(key: 'refreshToken');

    if (refreshToken == null) return false;

    try {
      var response = await http.post(
        Uri.parse(Constants.backUrl + '/refresh-token'),
        headers: {'Authorization': 'Bearer $refreshToken'},
      );

      if (response.statusCode == 200) {
        String newAccessToken = response.headers['access-token']!;
        await _storage.write(key: 'accessToken', value: newAccessToken);
        return true;
      } else {
        await logout(); // Refresh token is also invalid
        return false;
      }
    } catch (e) {
      log('Refresh token error: $e');
      return false;
    }
  }

  Future<void> logout() async {
    await _storage.deleteAll();
    // Additional logout operations like navigating to the login screen
  }
}
