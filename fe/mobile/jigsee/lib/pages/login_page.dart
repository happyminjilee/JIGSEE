import 'package:flutter/material.dart';
import 'package:jigsee/components/custom_form.dart';
import 'package:jigsee/conts/size.dart';

// stless 단축키로 자동 생성..
class LoginPage extends StatelessWidget {

  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Scaffold(
        resizeToAvoidBottomInset : false,
        body: SingleChildScrollView(
          padding: EdgeInsets.all(30.0),
          child: Center(
            child: Column (
              children: [
                SizedBox(height: xlargeGap,),
                Image(image: AssetImage('assets/SDI_LOGO.png'), width: 228, height: 28,),
                SizedBox(height: 40,),
                Text("지그 관리 시스템", style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                ),),
                SizedBox(height: xlargeGap,),
                CustomForm(),
              ],
            )
          ),
        )
    );
  }
}