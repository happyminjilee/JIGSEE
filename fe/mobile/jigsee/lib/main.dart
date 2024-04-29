import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/pages/home_page.dart';
import 'package:jigsee/pages/login_page.dart';
import 'package:jigsee/components/navigate_animation.dart';
import 'package:jigsee/pages/ocr_page.dart';

void main() {
  runApp(const ProviderScope(child: MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'JIG:SEE',
      theme: ThemeData(
        // 전체 TextButton 테마를 지정하면 모든 TextButton에 테마가 적용된다.
        textButtonTheme: TextButtonThemeData(
            style: TextButton.styleFrom(
                backgroundColor: Colors.blue,
                // primary: Colors.white,
                shape: RoundedRectangleBorder(
                  borderRadius: BorderRadius.circular(8),
                ),
                minimumSize: const Size(400, 60)
            )
        ),
      ),
      initialRoute: "/ocr", // 초기 경로 설정
      onGenerateRoute: (RouteSettings settings) {
        switch (settings.name) {
          case '/home':
            return NoAnimationRoute(builder: (_) => const HomePage(), settings: settings);
          case '/login':
            return NoAnimationRoute(builder: (_) => const LoginPage(), settings: settings);
          case '/ocr':
            return NoAnimationRoute(builder: (_) => const ReadSerialNum(), settings: settings);
          default:
            return MaterialPageRoute(builder: (_) => const HomePage());
        }
      },
    );
  }
}