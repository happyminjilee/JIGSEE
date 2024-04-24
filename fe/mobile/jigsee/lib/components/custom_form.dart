import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:jigsee/components/custom_text_form_field.dart';
import 'package:jigsee/size.dart';

class CustomForm extends StatefulWidget {

  @override
  _FormPageState createState() => _FormPageState();
}
class _FormPageState extends State<CustomForm> {
  // global key
  final _formkey = GlobalKey<FormState>();
  static String id = '';
  static String password = '';

  void _saveForm() {
    if (_formkey.currentState!.validate()) {
      _formkey.currentState!.save();  // 폼의 save 메소드 호출, 여기서 onSaved 콜백이 실행됩니다.
    }
  }

  @override
  Widget build(BuildContext context) {
    return Form(
      // global key를 form에 지정하여 해당 key로 form의 상태를 관리
      key: _formkey,
      child: Column(
        children: <Widget>[
          CustomTextFormField("사원 번호",
            onSaved: (String value) {
            id = value;
          },),
          const SizedBox(height: medium_gap,),
          CustomTextFormField("비밀번호",
            onSaved: (String value) {
            password = value;
          },),
          const SizedBox(height: large_gap,),
          // 로그인 버튼
          TextButton(
            onPressed: (){
              /// 유효성 검사
              if (_formkey.currentState!.validate()) {
                /// 네비게이터로 화면 이동, routes의 이름을 적어 이동한다
                Navigator.pushNamed(context, "/home");
              }
              _formkey.currentState!.save();
              log('Logged ID: $id, Password: $password');
            },
            child: Text("Login", style: TextStyle(color: Colors.white),),
          )
        ],
      ),
    );
  }
}