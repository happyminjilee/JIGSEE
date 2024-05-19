import 'package:flutter/material.dart';
import 'package:jigsee/pages/home_page.dart';

class NoAnimationRoute<T> extends MaterialPageRoute<T> {
  NoAnimationRoute({required WidgetBuilder builder, required RouteSettings settings})
      : super(
    builder: builder,
    settings: settings,
    maintainState: true,
    fullscreenDialog: false,
  );

  @override
  Widget buildTransitions(BuildContext context, Animation<double> animation, Animation<double> secondaryAnimation, Widget child) {
    return child;
  }
}