import 'package:flutter/material.dart';
import 'package:jigsee/components/header.dart';

class WillChangeJigs extends StatefulWidget {
  const WillChangeJigs({Key? key}) : super(key: key);

  @override
  State<WillChangeJigs> createState() => _WillChangeJigsState();
}

class _WillChangeJigsState extends State<WillChangeJigs> {
  late List<String> jigList;

  Future<List<String>> getJigList() async {
    return jigList;
  }
 // 이거 수정 해야함
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(),
      body: Padding(
        padding: const EdgeInsets.all(34.0),
        child: FutureBuilder(
            future: getJigList(),
            builder: (context, snapshot) {
              const Column(
                children: [
                  Text('123')
                ]
              );
            }
        ),
      )
    );
  }
}
