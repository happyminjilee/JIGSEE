import 'dart:async';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_svg/svg.dart';
import 'package:google_ml_kit/google_ml_kit.dart';
import 'package:jigsee/components/header.dart';
import 'package:jigsee/api/provider.dart';
import 'package:jigsee/api/dio_instance.dart';
import 'package:jigsee/pages/spe_jig_list.dart';
import 'package:jigsee/consts/size.dart';

class ReadSerialNum extends StatefulWidget {
  const ReadSerialNum({Key? key, required this.camera}) : super(key: key);
  final CameraDescription camera;

  @override
  State<ReadSerialNum> createState() => _ReadSerialNumState();
}

class _ReadSerialNumState extends State<ReadSerialNum> {
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
            }),
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
    scannedText = scannedText == "" ? "Error" : scannedText;
    setState(() {});
    sendJigInfo();  // 텍스트 인식 후 서버에 정보 요청
  }

  void sendJigInfo() async {
    final DioClient dioClient = ref.read(dioClientProvider);
    final equipmentName = ref.watch(equipmentProvider)[0]['facilitySerialNo'];

    try {
      var response = await dioClient.get(
          '/jig-item/usable?facility-model=$equipmentName&jig-serial-no=$scannedText',
      );
      print('Mark!!!!!!!!!!!!!!!${response.data}');
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
        verificationImagePath = null;
      });
    }
  }

  Future<bool> changeJig() async {
    final DioClient dioClient = ref.read(dioClientProvider);
    final equipmentId = ref.watch(equipmentProvider)[0]['id'];
    final jigName = ref.watch(selectedJigProvider);

    try {
      var response = await dioClient.put(
        '/jig-item/exchange',
        data: {
          "facilitySerialNo": equipmentId,
          "beforeSerialNo": jigName,
          "afterSerialNo": scannedText
        }
      );
      print('Mark!!!!!!!!!!!!!!!${response.data}');
      if (response.statusCode == 200) {
        bool isValid = response.data == 'true';
        return true;
      } else {
        return false;
      }
    } catch (e) {
      return false;
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
            const Text('지그 정보', style: TextStyle(fontSize: 28)),
            const SizedBox(height: sixteen),
            Expanded(
              child: Column(
                  children: [
                    Container(
                      alignment: Alignment.center,
                      padding: const EdgeInsets.all(12),
                      height: 228,
                      decoration: BoxDecoration(
                        border: Border.all(
                          color: const Color.fromARGB(255, 25, 30, 40),
                          width: 3,
                        ),
                        borderRadius: BorderRadius.circular(8),
                      ),
                      child: Row(
                        children: [
                          Image.file(File(widget.imagePath), scale: 2.5,),
                          const SizedBox(width: 20),
                          Expanded(
                              child: Column(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text('설비 이름 : ${ref.watch(equipmentProvider)[0]['facilitySerialNo']}'),
                                    Text('기존 지그 S/N: ${ref.watch(selectedJigProvider)}'),
                                    Text(scannedText.isEmpty ? "Error scanning" : scannedText),
                                  ]
                              )
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 28.0,),
                    if (verificationImagePath != null) SvgPicture.asset('assets/$verificationImagePath.svg')
                    else SvgPicture.asset('assets/notAvailableMark.svg'),
                    const SizedBox(height: 32.0,),
                    if (verificationImagePath == "availableMark") const Text('사용 가능', style: TextStyle(fontSize: 20),)
                    else if (verificationImagePath == null) const Text('인식 오류 입니다.\n다시 촬영해주세요', style: TextStyle(fontSize: 20),)
                    else const Text('사용 할 수 없는 지그 입니다.', style: TextStyle(fontSize: 20),),
                    const SizedBox(height: 32.0,),
                    ElevatedButton(
                      onPressed: () async {
                        if (verificationImagePath == null) {
                          Navigator.of(context).pop();
                        }
                        else {
                          if (verificationImagePath == 'availableMark') {
                            changeJig();
                          }
                          Navigator.push(context, MaterialPageRoute(builder: (context) => const SpeJigList()));
                        }
                      },
                      child: Text(verificationImagePath != null ? 
                        verificationImagePath == 'availableMark' ? '교체' : '확인'
                        : '재촬영',
                          style: const TextStyle(fontSize: 20)
                      ),
                      style: ElevatedButton.styleFrom(
                          minimumSize: const Size(300, 50),
                          foregroundColor: const Color.fromARGB(255, 248, 250, 252),
                          backgroundColor: const Color.fromARGB(255, 47, 118, 255),
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
}
