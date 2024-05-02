import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:jigsee/pages/will_change_list.dart';
import 'package:jigsee/stores/usePreference.dart';
import 'package:jigsee/components/header.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _HomePageState();
}
class _HomePageState extends State<HomePage> {
  final _storage = const FlutterSecureStorage();

  Future<String> _loadUserName() async {
    return await _storage.read(key: 'userName') as String;
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<String> (
      future: _loadUserName(),
      builder: (BuildContext context, AsyncSnapshot<String> snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          if (snapshot.hasError) {
            return Scaffold(
              appBar: const CustomAppBar(),
              body: Padding(
                padding: const EdgeInsets.all(16.0),
                child: ListView(
                  children: [
                    Text('Error: ${snapshot.error}'),
                  ],
                )
              ),
            );
          }
          return Scaffold(
            appBar: const CustomAppBar(),
            body: Padding(
              padding: const EdgeInsets.all(16.0),
              child: ListView(
                children: [
                  const SizedBox(height: 200,),
                  Text('userName : ${snapshot.data}'),
                  const SizedBox(height: 50,),
                  TextButton(
                      onPressed: (){
                        Navigator.of(context).push(
                          MaterialPageRoute(
                            builder: (context) => const WillChangeJigs()
                          )
                        );
                      },
                      child: const Text("교체 지그 목록"))
                ],
              ),
            ),
          );
        } else {
          return Scaffold(
            appBar: const CustomAppBar(),
            body: Padding(
              padding: const EdgeInsets.all(16.0),
              child: ListView(
                children: const [
                  CircularProgressIndicator(),
                ],
              ),
            ),
          );
        }
      }
    );
  }
}