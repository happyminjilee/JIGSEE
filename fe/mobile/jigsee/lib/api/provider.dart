import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/api/dio_instance.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:jigsee/consts/constants.dart';

final equipmentProvider = StateProvider<List<String>>((ref) {
  return [];
});

final selectedJigProvider = StateProvider<String>((ref) {
  return '';
});

final dioClientProvider = Provider<DioClient>((ref) {
  const secureStorage = FlutterSecureStorage();
  return DioClient(baseUrl: Constants.backUrl, secureStorage: secureStorage);
});

final userNameProvider = FutureProvider<String>((ref) async {
  const secureStorage = FlutterSecureStorage();
  String? userName = await secureStorage.read(key: 'userName');
  return userName ?? 'Unknown User';
});
