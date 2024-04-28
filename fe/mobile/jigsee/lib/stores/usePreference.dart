import 'dart:developer';

import 'package:shared_preferences/shared_preferences.dart';

class UserPreference {
  static late SharedPreferences _prefs;

  Future<void> initSharedPreferences() async {
    _prefs = await SharedPreferences.getInstance();
  }

  Future<void> saveData(String valueType, String value) async {
    _prefs.setString(valueType, value);
  }

  String? loadData(String keyValue) {
    final myData = _prefs.getString(keyValue);
    return myData;
  }
}
