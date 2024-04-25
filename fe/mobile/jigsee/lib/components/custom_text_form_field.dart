import 'package:flutter/material.dart';
import 'package:jigsee/size.dart';
import 'package:flutter_svg/flutter_svg.dart';

class CustomTextFormField extends StatefulWidget {
  final String text;
  final Function(String)? onSaved;

  const CustomTextFormField(this.text, {Key? key, this.onSaved}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _CustomTextFormFieldState();
}

class _CustomTextFormFieldState extends State<CustomTextFormField> {
  bool _passwordVisible = true;

  final TextEditingController _controller = TextEditingController();

  String? validatePassword(String? value) {
    if (value == null || value.isEmpty) {
      return '비밀번호를 입력해주세요.';
    }
    String pattern =
        r'^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[\W_]).+$';
    RegExp regExp = RegExp(pattern);
    if (!regExp.hasMatch(value)) {
      return '올바른 비밀번호를 입력해주세요';
    }
    return null;
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Text(widget.text),
        const SizedBox(height: smallGap),
        TextFormField(
          controller: _controller,
          onSaved: (String? value) {
            if (widget.onSaved != null && value != null) {
              widget.onSaved!(value);
            }
          },
          validator: (String? value) {
            if (widget.text == '비밀번호') {
              return validatePassword(value);
            } else {
              return value!.isEmpty ? "필수항목입니다" : null;
            }
          },
          obscureText: widget.text == '비밀번호' ? _passwordVisible : false,
          decoration: InputDecoration(
            hintText: widget.text == '비밀번호' ? "비밀번호 입력" : "사원번호 입력",
            suffixIcon: widget.text == '비밀번호' ? IconButton(
              icon: SvgPicture.asset(
                  _passwordVisible ? 'assets/invisible_icon.svg' : 'assets/visible_icon.svg',
                  key: ValueKey(_passwordVisible)  // Ensure icon updates correctly
              ),
              onPressed: () {
                setState(() {
                  _passwordVisible = !_passwordVisible;
                });
              },
            ) : null,
          ),
        )
      ],
    );
  }
}

