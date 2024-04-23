import 'package:flutter/material.dart';

class HomePage extends StatelessWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: ListView(
          children: [
            SizedBox(height: 200,),
            SizedBox(height: 50,),
            TextButton(
                onPressed: (){
                  Navigator.pop(context);
                },
                child: Text("로그인 페이지로 이동"))
          ],
        ),
      ),
    );
  }
}