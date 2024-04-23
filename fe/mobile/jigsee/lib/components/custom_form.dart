import 'package:flutter/material.dart';
import 'package:jigsee/components/custom_text_form_field.dart';
import 'package:jigsee/size.dart';

class CustomForm extends StatelessWidget {
  // global key
  final _formkey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Form(
      // global key를 form에 지정하여 해당 key로 form의 상태를 관리
      key: _formkey,
      child: Column(
        children: [
          CustomTextFormField("Email"),
          SizedBox(height: medium_gap,),
          CustomTextFormField("Password"),
          SizedBox(height: large_gap,),
          // 로그인 버튼
          TextButton(
            onPressed: (){
              /// 유효성 검사
              if (_formkey.currentState!.validate()) {
                /// 네비게이터로 화면 이동, routes의 이름을 적어 이동한다
                Navigator.pushNamed(context, "/home");
              }
            },
            child: Text("Login"),
          )
        ],
      ),
    );
  }
}