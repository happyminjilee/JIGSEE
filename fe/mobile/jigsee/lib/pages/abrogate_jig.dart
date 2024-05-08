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
  String? verificationImagePath;  // 검증 결과에 따라 변경될 이미지 경로

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
        scannedText += line.text + "\n";
      }
    }
    setState(() {});
    sendJigInfo();  // 텍스트 인식 후 서버에 정보 요청
  }

  void sendJigInfo() async {
    final DioClient dioClient = ref.read(dioClientProvider);
    final jigName = ref.watch(selectedJigProvider);

    try {
      var response = await dioClient.get(
          '/jiginfo',
          queryParameters: {
            'scannedText': scannedText,
            'jigName': jigName
          }
      );
      if (response.statusCode == 200) {
        bool isValid = response.data == 'true';
        // 응답에 따라 이미지 경로 설정
        setState(() {
          verificationImagePath = isValid ? "availableMark" : "notAvailableMark";
        });
      } else {
        setState(() {
          verificationImagePath = "notAvailableMark";
        });
      }
    } catch (e) {
      setState(() {
        verificationImagePath = "notAvailableMark";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: const CustomAppBar(),
        body: Padding(
          padding: const EdgeInsets.all(20),
          child: Column(
            children: [
              const Text('지그 정보'),
              Expanded(
                child: Column(
                    children: [
                      Container(
                        alignment: Alignment.center,
                        padding: const EdgeInsets.all(12),
                        decoration: BoxDecoration(
                          border: Border.all(
                            color: const Color.fromARGB(255, 47, 118, 255),
                            width: 1,
                          ),
                          borderRadius: BorderRadius.circular(8),
                        ),
                        child: Row(
                          children: [
                            Image.file(File(widget.imagePath), scale: 2.5,),
                            const SizedBox(height: 20),
                            Column(
                              mainAxisAlignment: MainAxisAlignment.start,
                              children: [
                                Text(ref.watch(selectedJigProvider as ProviderListenable<String>)),
                                Text(scannedText.isEmpty ? "Error scanning" : scannedText),
                              ]
                            )
                          ],
                        ),
                      ),
                      const SizedBox(height: largeGap),
                      ElevatedButton(
                        onPressed: _showDeleteDialog,
                        child: const Text('폐기', style: TextStyle(fontSize: 20)),
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
                        onPressed: () => Navigator.pushNamed(context, '/home'),
                        child: const Text('취소', style: TextStyle(fontSize: 20)),
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
        )
    );
  }

  Future<void> deleteJig(String serialNum) async {
    final DioClient dioClient = ref.read(dioClientProvider);
    try {
      var response = await dioClient.delete(
          '/jig-item',
          data: {
            'serialNo': serialNum,
          }
      );
      if (response.statusCode != 200) {
        log(response.data);
      }
    } catch (e) {
      log(e as String);
    }
  }

  void _showDeleteDialog() {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text(
            '폐기 하시겠습니까?',
            textAlign: TextAlign.center,
            style: TextStyle(
              color: Color.fromARGB(255, 25, 30, 40),
              fontSize: 20,
            ),
          ),
          content: Text(
            'S/N: $scannedText',
            textAlign: TextAlign.center,
            style: const TextStyle(
              color: Color.fromARGB(255, 25, 30, 40),
              fontSize: 16,
            ),
          ),
          actions: <Widget>[
            TextButton(
              onPressed: () async {
                try {
                  await deleteJig(scannedText);
                  Navigator.of(context).pop();
                  Navigator.pushNamed(context, "/home");
                } catch (e) {
                  print('Error: $e');  // 오류 출력
                }
              },
              child: const Text('폐기'),
            ),
            const SizedBox(height: smallGap,),
            TextButton(
              onPressed: () => Navigator.of(context).pop(),
              child: const Text('취소'),
            ),
          ],
        );
      },
    );
  }
}
