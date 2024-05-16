import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/pages/abrogate_jig.dart';
import 'package:jigsee/pages/home_page.dart';
import 'package:jigsee/pages/login_page.dart';
import 'package:jigsee/components/navigate_animation.dart';
import 'package:jigsee/pages/ocr_page.dart';
import 'package:jigsee/api/user_auth.dart';


import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter_local_notifications/flutter_local_notifications.dart';

Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  print("백그라운드 메시지 처리.. ${message.notification!.body!}");
}

void initializeNotification() async {
  FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

  final flutterLocalNotificationsPlugin = FlutterLocalNotificationsPlugin();
  await flutterLocalNotificationsPlugin
      .resolvePlatformSpecificImplementation<
      AndroidFlutterLocalNotificationsPlugin>()
      ?.createNotificationChannel(const AndroidNotificationChannel(
      'high_importance_channel', 'high_importance_notification',
      importance: Importance.max));

  await flutterLocalNotificationsPlugin.initialize(const InitializationSettings(
    android: AndroidInitializationSettings("@mipmap/ic_launcher"),
  ));

  await FirebaseMessaging.instance.setForegroundNotificationPresentationOptions(
    alert: true,
    badge: true,
    sound: true,
  );
}

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  final cameras = await availableCameras();
  final firstCamera = cameras.first;
  await Firebase.initializeApp();
  initializeNotification();
  runApp(ProviderScope(child: MyApp(camera: firstCamera)));
}

class MyApp extends StatelessWidget {
  final CameraDescription camera;

  const MyApp({Key? key, required this.camera}) : super(key: key);
  // const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'JIG:SEE',
      home: FutureBuilder<bool>(
        future: AuthService().isLogin(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.done) {
            if (snapshot.data == true) {
              return const HomePage();
            } else {
              return const LoginPage();
            }
          } else {
            return const Center(child: CircularProgressIndicator());
          }
        },
      ),
      onGenerateRoute: (RouteSettings settings) {
        switch (settings.name) {
          case '/home':
            return NoAnimationRoute(builder: (_) => const HomePage(), settings: settings);
          case '/login':
            return NoAnimationRoute(builder: (_) => const LoginPage(), settings: settings);
          case '/ocr':
            return NoAnimationRoute(builder: (_) => ReadSerialNum(camera: camera), settings: settings);
          case '/abrogate':
            return NoAnimationRoute(builder: (_) => AbrogateJig(camera: camera), settings: settings);
          default:
            return MaterialPageRoute(builder: (_) => const HomePage());
        }
      },
    );
  }
}