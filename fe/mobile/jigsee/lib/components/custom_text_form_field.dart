import 'package:flutter/material.dart';
import '../size.dart';
// import 'package:flutter/size.dart';

class CustomTextFormField extends StatelessWidget {
  final String text;
  const CustomTextFormField(this.text);

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(text),
        SizedBox(height: small_gap,),
        TextFormField(
          // 1. [유효성 체크]
          // 공백일 경우 메시지 출력..
          validator: (value) => value!.isEmpty
              ? "필수항목입니다"
              : null,

          // 2. [마킹처리]
          // text가 Password일 경우 마킹 처리 true
          obscureText: text == "Password" ? true : false,

          // 3. [데코레이션]
          // 힌트 문자나 여러가지 데코레이션 기능 추가
          decoration: InputDecoration(
              hintText: "Enter $text.", // 힌트문자
              enabledBorder: OutlineInputBorder(
                // 기본 모양
                borderRadius: BorderRadius.circular(20),
              ),
              focusedBorder: OutlineInputBorder(
                // 포커스 되었을 경우 모양
                borderRadius: BorderRadius.circular(20),
              ),
              errorBorder: OutlineInputBorder(
                // 에러 발생 시 모양
                  borderRadius: BorderRadius.circular(20)
              ),
              focusedErrorBorder: OutlineInputBorder(
                // 에러 발생 후 포커스 되었을 경우 모양
                  borderRadius: BorderRadius.circular(20)
              )
          ),
        )
      ],
    );
  }
}