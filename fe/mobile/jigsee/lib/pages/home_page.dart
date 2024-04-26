import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:jigsee/stores/usePreference.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _HomePageState();
}
class _HomePageState extends State<HomePage> {
  final _storage = const FlutterSecureStorage();
  // static String? userName = UserPreference().loadData('userName');


  @override
  Widget build(BuildContext context) {
    String? userName = _storage.read(key: 'refreshToken') as String?;

    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            const SizedBox(height: 200,),
            Text('userName : $userName'),
            const SizedBox(height: 50,),
            TextButton(
                onPressed: (){
                  Navigator.pop(context);
                },
                child: const Text("로그인 페이지로 이동"))
          ],
        ),
      ),
    );
  }
}