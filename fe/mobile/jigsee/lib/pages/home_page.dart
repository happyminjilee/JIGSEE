import 'package:dio/dio.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:jigsee/api/user_auth.dart';
import 'package:jigsee/components/header.dart';
import 'package:jigsee/pages/spe_jig_list.dart';
import 'package:jigsee/api/provider.dart';
import 'package:jigsee/api/dio_instance.dart';
import 'package:jigsee/consts/size.dart';

class HomePage extends ConsumerStatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  ConsumerState<HomePage> createState() => _HomePageState();
}

class _HomePageState extends ConsumerState<HomePage> {
  late Future<List<dynamic>> _equipmentsFuture;
  var messageString = "";

  Future<String?> getMyDeviceToken() async {
    final token = await FirebaseMessaging.instance.getToken();
    return token;
  }

  /*
  * POST
  * /api/v1/notification/fcm/token
  * body => "token" : {deviceToken}
  */
  void sendTokenToServer(String deviceToken) async {
    try {
      DioClient dioClient = ref.read(dioClientProvider);
      Response response = await dioClient.post(
          '/notification/fcm/token',
        data: {
            'token': deviceToken
        }
      );
    } catch (e) {
      print("$e");
    }
  }

  @override
  void initState() {
    Future<String?> token = getMyDeviceToken(); // device-token 가져옴
    token
        .then((value) => sendTokenToServer(value!)); // 가져온 deveice-token을 서버에 저장함

    FirebaseMessaging.onMessage.listen((RemoteMessage message) async {
      RemoteNotification? notification = message.notification;

      if (notification != null) {
        FlutterLocalNotificationsPlugin().show(
          notification.hashCode,
          notification.title,
          notification.body,
          const NotificationDetails(
            android: AndroidNotificationDetails(
              'high_importance_channel',
              'high_importance_notification',
              importance: Importance.max,
            ),
          ),
        );
        setState(() {
          messageString = message.notification!.body!;
          print("Foreground 메시지 수신: $messageString");
        });
      }
    });

    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage? message) {
      if (message != null) {
        RemoteNotification? notification = message.notification;
        if (notification != null) {
          FlutterLocalNotificationsPlugin().show(
            notification.hashCode,
            notification.title,
            notification.body,
            const NotificationDetails(
              android: AndroidNotificationDetails(
                'high_importance_channel',
                'high_importance_notification',
                importance: Importance.max,
              ),
            ),
          );
        }
      }
    });

    FirebaseMessaging.instance
        .getInitialMessage()
        .then((RemoteMessage? message) {
      if (message != null) {
        RemoteNotification? notification = message.notification;
        if (notification != null) {
          FlutterLocalNotificationsPlugin().show(
            notification.hashCode,
            notification.title,
            notification.body,
            const NotificationDetails(
              android: AndroidNotificationDetails(
                'high_importance_channel',
                'high_importance_notification',
                importance: Importance.max,
              ),
            ),
          );
        }
      }
    });
    super.initState();
    _equipmentsFuture = _fetchEquipments();
  }

  Future<List<dynamic>> _fetchEquipments() async {
    try {
      DioClient dioClient = ref.read(dioClientProvider);
      Response response = await dioClient.get('/facility-item/inspection');
      if (response.statusCode == 200) {
        // List<dynamic> data = response.data;
        List<dynamic> data = response.data['result']['list'];
        return data;
        // return data.map<String>((e) => e.toString()).toList();
      } else {
        throw Exception('Failed to load equipments');
      }
    } on DioException catch (e) {
      throw Exception('Dio error: ${e.message}');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            Consumer(
              builder: (context, ref, child) {
                return Container(
                  decoration: BoxDecoration(
                    color: const Color.fromARGB(255, 217, 217, 217),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: ref.watch(userNameProvider).when(
                    data: (userName) => ListTile(
                      title: Text('$userName 프로'),
                      trailing: IconButton(
                        icon: const Icon(Icons.logout),
                        onPressed: _showLogoutDialog,
                      ),
                    ),
                    error: (e, _) => const Text('재 로그인 해 주세요'),
                    loading: () => const CircularProgressIndicator(),
                  ),
                );
              },
            ),
            const SizedBox(height: largeGap,),
            const SizedBox(
              width: 1000,
              child: Text("점검 대상 목록", textAlign: TextAlign.left,),
            ),
            const SizedBox(height: mediumGap,),
            Expanded(
              child : Container(
                padding: const EdgeInsets.only(left: 10, right: 10),
                decoration: BoxDecoration(
                  border: Border.all(
                    color: const Color.fromARGB(255, 47, 118, 255),
                    width: 1,
                  ),
                  borderRadius: BorderRadius.circular(8),
                ),
                child: FutureBuilder<List<dynamic>>(
                  future: _equipmentsFuture,
                  builder: (context, snapshot) {
                    if (snapshot.connectionState == ConnectionState.done) {
                      if (snapshot.hasError) {
                        return Container(
                          width: 1000,
                          alignment: Alignment.center,
                          child: const Text('네트워크 오류 발생')
                        );
                      }
                      List<dynamic> equipments = snapshot.data ?? [];
                      if (equipments.isEmpty) {
                        return Container(
                            width: 1000,
                            alignment: Alignment.center,
                            child: const Text('점검 대상 지그가 없습니다.')
                        );
                      }
                      return ListView.builder(
                        itemCount: equipments.length,
                        itemBuilder: (context, index) => Card(
                          margin: const EdgeInsets.symmetric(vertical: 8),
                          child: ListTile(
                            title: Text('설비: ${equipments[index]['facilitySerialNo']}', style: const TextStyle(fontSize: 16)),
                            onTap: () {
                              ref.read(equipmentProvider.notifier).state = [equipments[index]];
                              Navigator.push(context, MaterialPageRoute(builder: (context) => const SpeJigList()));
                            },
                          ),
                        ),
                      );
                    } else {
                      // 로딩중
                      return const Center(child: CircularProgressIndicator());
                    }
                  },
                ),
              ),
            ),
            const SizedBox(height: 28),
            ElevatedButton(
              onPressed: () {
                Navigator.pushNamed(context, '/abrogate');
              },
              child: const Text('지그 조회', style: TextStyle(fontSize: 20)),
              style: ElevatedButton.styleFrom(
                minimumSize: const Size(10000, 50),
                foregroundColor: const Color.fromARGB(255, 248, 250, 252),
                backgroundColor: const Color.fromARGB(255, 47, 118, 255),
                padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8)
                )
              ),
            ),
            const SizedBox(height: 40)
          ],
        ),
      ),
    );
  }

  void _showLogoutDialog() {
    const storage = FlutterSecureStorage();

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text(
            '로그아웃',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Color.fromARGB(255, 25, 30, 40),
              fontSize: 20,
            ),
          ),
          content: const Text(
            '로그아웃 하시겠습니까?',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Color.fromARGB(255, 25, 30, 40),
              fontSize: 16,
            ),
          ),
          actions: <Widget>[
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('취소'),
            ),
            TextButton(
              onPressed: () async {
                try {
                  // await AuthService().logout();
                  // await storage.deleteAll();
                  await storage.delete(key: 'authorization');
                  await storage.delete(key: 'refreshToken');
                  await storage.delete(key: 'userName');
                  await storage.delete(key: 'employeeNo');
                  await storage.delete(key: 'role');
                  ref.refresh(userNameProvider);
                  Navigator.of(context).pop();
                  Navigator.pushNamed(context, "/login");
                } catch (e) {
                  print('Error: $e');  // 오류 출력
                }
              },
              child: const Text('확인'),
            ),
          ],
        );
      },
    );
  }
}
