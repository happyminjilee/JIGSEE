import 'package:flutter/material.dart';
import '../components/custom_form.dart';
import '../size.dart';

// stless 단축키로 자동 생성..
class LoginPage extends StatelessWidget {

  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Padding(
          padding: const EdgeInsets.all(16.0),
          child: ListView(
            children: [
              SizedBox(height: xlarge_gap,),
              SizedBox(height: large_gap,),
              CustomForm(),
            ],
          ),
        )
    );
  }
}