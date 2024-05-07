import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/pages/abrogate_jig.dart';
import 'package:jigsee/pages/home_page.dart';
import 'package:jigsee/pages/login_page.dart';
import 'package:jigsee/components/navigate_animation.dart';
import 'package:jigsee/pages/ocr_page.dart';
import 'package:jigsee/api/user_auth.dart';

Future<void> main() async {
  WidgetsFlutterBinding.ensureInitialized();
  final cameras = await availableCameras();
  final firstCamera = cameras.first;

  runApp(ProviderScope(child: MyApp(camera: firstCamera)));
}

class MyApp extends StatelessWidget {
  final CameraDescription camera;

  const MyApp({Key? key, required this.camera}) : super(key: key);

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
