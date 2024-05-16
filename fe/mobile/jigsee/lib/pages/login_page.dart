import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/components/custom_form.dart';
import 'package:jigsee/consts/size.dart';

class LoginPage extends ConsumerWidget {

  const LoginPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    return const PopScope(
        child: Scaffold(
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
        ),
        canPop: false,
    );
  }
}