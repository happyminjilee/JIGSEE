import 'package:flutter/material.dart';
import 'package:jigsee/pages/home_page.dart';
import 'package:jigsee/pages/login_page.dart';

void main() {
  runApp(const MyApp());
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
      initialRoute: "/login", // 초기 경로 설정
      routes: { // 경로 목록
        "/login" : (context) => const LoginPage(),
        "/home" : (context) => const HomePage()
      },
    );
  }
}