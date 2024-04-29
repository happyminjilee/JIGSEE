import 'dart:developer';
import 'package:flutter/material.dart';
import 'package:jigsee/components/custom_text_form_field.dart';
import 'package:jigsee/api/user_auth.dart';
import 'package:jigsee/consts/size.dart';

class CustomForm extends StatefulWidget {
  const CustomForm({Key? key}) : super(key: key);

  @override
  _FormPageState createState() => _FormPageState();
}
class _FormPageState extends State<CustomForm> {
  // global key
  final _formkey = GlobalKey<FormState>();

  static String id = '';
  static String password = '';

  showSnackBar(BuildContext context) {
    final snackBar = SnackBar(
      content:  const Text(
        '정보를 정확히 입력해주세요',
        style: TextStyle(
          color: Colors.white,
          fontSize: 16,
          height: 1,
        ),
      ),
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(10))),	//	둥글게
      backgroundColor: const Color(0xff9E9E9E),
      behavior: SnackBarBehavior.floating,	//	아래 플로팅 띄우기
      duration: const Duration(seconds: 2),
      action:
      SnackBarAction(label: '', textColor: Colors.white, onPressed: () {}),
    );

    ScaffoldMessenger.of(context).showSnackBar(snackBar);
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
          const SizedBox(height: mediumGap,),
          CustomTextFormField("비밀번호",
            onSaved: (String value) {
            password = value;
          },),
          const SizedBox(height: largeGap,),
          // 로그인 버튼
          TextButton(
            onPressed: () async {
              _formkey.currentState!.save();
              log('Logged ID: $id, Password: $password');

              /// 유효성 검사
              if (_formkey.currentState!.validate()) {
                if (await AuthService().login(id, password)) {
                  /// 네비게이터로 화면 이동, routes의 이름을 적어 이동한다
                  Navigator.pushNamed(context, "/home");
                } else {
                  showSnackBar(context);
                }
              } else {
                showSnackBar(context);
              }
            },
            child: const Text("Login", style: TextStyle(color: Colors.white),),
          )
        ],
      ),
    );
  }
}