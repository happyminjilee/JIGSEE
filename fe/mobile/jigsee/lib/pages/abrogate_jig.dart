import 'dart:async';
import 'dart:developer';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:google_ml_kit/google_ml_kit.dart';
import 'package:jigsee/api/dio_instance.dart';
import 'package:jigsee/api/provider.dart';
import 'package:jigsee/components/header.dart';
import 'package:jigsee/consts/size.dart';

class AbrogateJig extends ConsumerStatefulWidget {
  const AbrogateJig({Key? key, required this.camera}) : super(key: key);
  final CameraDescription camera;

  @override
  ConsumerState createState() => _AbrogateJigState();
}

class _AbrogateJigState extends ConsumerState<AbrogateJig> {
  late CameraController _controller;
  late Future<void> _initializeControllerFuture;

  @override
  void initState() {
    super.initState();
    _controller = CameraController(
      widget.camera,
      ResolutionPreset.medium,
    );
    _initializeControllerFuture = _controller.initialize();
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Column(
          children: [
            FutureBuilder(
              future: _initializeControllerFuture,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.done) {
                  return CameraPreview(_controller);
                } else {
                  return const Center(child: CircularProgressIndicator());
                }
              }
            ),
            const SizedBox(height: largeGap,),
            ElevatedButton(
              onPressed: () async {
                try {
                  await _initializeControllerFuture;
                  final image = await _controller.takePicture();
                  if(!mounted) return;
                  await Navigator.of(context).push(
                      MaterialPageRoute(
                          builder: (context) => DisplayPictureScreen (
                            imagePath: image.path,
                          )
                      )
                  );
                } catch (e) {
                  print(e);
                };
              },
              child: const Text('촬영', style: TextStyle(fontSize: 20)),
              style: ElevatedButton.styleFrom(
                  minimumSize: const Size(360, 50),
                  foregroundColor: const Color.fromARGB(255, 248, 250, 252),
                  backgroundColor: const Color.fromARGB(255, 47, 118, 255),
                  padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(8)
                  )
              ),
            ),
          ],
        )
    );
  }
}

class DisplayPictureScreen extends ConsumerStatefulWidget {
  final String imagePath;
  const DisplayPictureScreen({Key? key, required this.imagePath}) : super(key: key);

  @override
  ConsumerState<DisplayPictureScreen> createState() => _DisplayPictureScreenState();
}

class _DisplayPictureScreenState extends ConsumerState<DisplayPictureScreen> {
  late String scannedText = "";
  String? verificationImagePath;
  static Map<String, dynamic> jigData = {};
  final Completer<void> _sendJigInfoCompleter = Completer<void>();

  @override
  void initState() {
    super.initState();
    getRecognizedText();
  }

  void getRecognizedText() async {
    final InputImage inputImage = InputImage.fromFilePath(widget.imagePath);
    final textRecognizer = GoogleMlKit.vision.textRecognizer(script: TextRecognitionScript.latin);
    RecognizedText recognizedText = await textRecognizer.processImage(inputImage);
    await textRecognizer.close();

    for (TextBlock block in recognizedText.blocks) {
      for (TextLine line in block.lines) {
        scannedText += line.text;
        break;
      }
      break;
    }
    setState(() {});
    searchJigInfo();  // 텍스트 인식 후 서버에 정보 요청
  }

  Future<void> searchJigInfo() async {
    final DioClient dioClient = ref.read(dioClientProvider);

    try {
      var response = await dioClient.get(
          '/jig-item?serial-no=$scannedText',
      );
      if (response.statusCode == 200) {
        // 응답에 따라 이미지 경로 설정
        setState(() {
          jigData = {
            "model": response.data['result']['model'],
            "serialNo": response.data['result']['serialNo'],
            "status": response.data['result']['status'],
            "expectLife": response.data['result']['expectLife'],
            "useCount": response.data['result']['useCount'],
            "useTime": response.data['result']['useAccumulationTime'],
            "repairCount": response.data['result']['repairCount'],
          };
        });
      } else {
        setState(() {
          jigData = {};
        });
      }
    } catch (e) {
      setState(() {
        jigData = {
          'Error': 'Error'
        };
      });
    }finally {
      _sendJigInfoCompleter.complete();
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: const CustomAppBar(),
        body: FutureBuilder<void>(
          future: _sendJigInfoCompleter.future,
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.done) {
              if (snapshot.hasError) {
                return const Text('네트워크 에러 발생');
              }
              return Padding(
                padding: const EdgeInsets.all(20),
                child: Column(
                  children: [
                    const Text('지그 정보', style: TextStyle(
                      fontSize: 32,
                    ),
                    ),
                    const SizedBox(height: 20),
                    Expanded(
                      child: Column(
                          children: [
                            Container(
                              alignment: Alignment.center,
                              padding: const EdgeInsets.all(12),
                              height: 300,
                              decoration: BoxDecoration(
                                border: Border.all(
                                  color: const Color.fromARGB(255, 25, 30, 40),
                                  width: 3,
                                ),
                                borderRadius: BorderRadius.circular(8),
                              ),
                              child: Row(
                                children: [
                                  Image.file(File(widget.imagePath), scale: 3,),
                                  const SizedBox(width: 10),
                                  Expanded(
                                      child: Column(
                                          mainAxisAlignment: MainAxisAlignment.center,
                                          crossAxisAlignment: CrossAxisAlignment.start,
                                          children: [
                                            if (jigData.isNotEmpty && jigData['Error'] == null) Column(
                                              mainAxisAlignment: MainAxisAlignment.center,
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              children: [
                                                Text('제품 명: ${jigData['model']}'),
                                                Text('S / N : ${jigData['serialNo']}'),
                                                Text('현 상태: ${jigData['status']}'),
                                                Text('예상 수명: ${jigData['useCount']}'),
                                                Text('사용 횟수: ${jigData['useCount']}'),
                                                Text('수리 횟수: ${jigData['repairCount']}'),
                                              ],
                                            )
                                            else if (jigData.isEmpty) const Text('인식 오류')
                                            else const Text('인식 오류'),
                                          ]
                                      )
                                  ),
                                ],
                              ),
                            ),
                            const SizedBox(height: 100),
                            ElevatedButton(
                              onPressed: () {
                                Navigator.pushNamed(context, '/home');
                              },
                              child: const Text('확인', style: TextStyle(fontSize: 20)),
                              style: ElevatedButton.styleFrom(
                                  minimumSize: const Size(360, 50),
                                  foregroundColor: const Color.fromARGB(255, 248, 250, 252),
                                  backgroundColor: const Color.fromARGB(255, 47, 118, 255),
                                  padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(8)
                                  )
                              ),
                            ),
                            const SizedBox(height: largeGap),
                            ElevatedButton(
                              onPressed: () {
                                Navigator.of(context).pop();
                              },
                              child: const Text('재촬영', style: TextStyle(fontSize: 20)),
                              style: ElevatedButton.styleFrom(
                                  minimumSize: const Size(360, 50),
                                  foregroundColor: const Color.fromARGB(255, 25, 30, 40),
                                  backgroundColor: const Color.fromARGB(255, 217, 217, 217),
                                  padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
                                  shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(8)
                                  )
                              ),
                            ),
                          ]
                      ),
                    )
                  ],
                ),
              );
            } else {
              return Container(
                  color: Colors.white,
                  child: const Center(
                      child: CircularProgressIndicator(
                        valueColor: AlwaysStoppedAnimation<Color>(Color.fromARGB(255, 47, 118, 255)),
                      )
                  )
              );
            }
          }
        )
    );
  }
}
