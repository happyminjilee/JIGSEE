import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:http/http.dart' as http;

import 'package:jigsee/consts/constants.dart';

class Jigs {
  final _storage = const FlutterSecureStorage();
  final String apiUrl = Constants.backUrl;

  Map<String, String> headers = {
    'content-type' : 'application/json',
  };
}