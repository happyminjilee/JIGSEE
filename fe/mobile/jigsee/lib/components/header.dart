import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';

class CustomAppBar extends StatefulWidget implements PreferredSizeWidget {
  const CustomAppBar({Key? key}) : super(key: key);

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);

  @override
  State<StatefulWidget> createState() => _CustomAppBar();
}

class _CustomAppBar extends State<CustomAppBar> {
  @override
  Widget build(BuildContext context) {
    return AppBar(
      leadingWidth: 176,
      leading: GestureDetector(
        onTap: () {
          Navigator.pushNamed(context, '/home');
        },
        child: Container(
          margin: const EdgeInsets.all(8), // 로고 주변 여백 조정
          child: Image.asset(
            'assets/SDI_LOGO.png',
            width: 160,
            height: 50,
          ),
        ),
      ),
      // actions: [
      //   IconButton(
      //     icon: SvgPicture.asset('assets/notification.svg'),
      //     onPressed: () {
      //       // Navigator.pushNamed(context, '/notifications');
      //     },
      //   ),
      // ],
      // elevation: 2.0,
    );
  }
}

