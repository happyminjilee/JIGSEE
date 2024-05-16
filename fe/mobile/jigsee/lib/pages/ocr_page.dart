import 'dart:async';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:dio/dio.dart';
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
  static Map<String, dynamic> jigData = {};

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
    scannedText = scannedText == "" ? "Error" : scannedText.toLowerCase();
    setState(() {});
    sendJigInfo();  // 텍스트 인식 후 서버에 정보 요청
  }

  void sendJigInfo() async {
    final DioClient dioClient = ref.read(dioClientProvider);
    final equipmentName = ref.watch(equipmentProvider)[0]['facilitySerialNo'];

    try {
      var response = await dioClient.get(
      '/jig-item/usable?facility-serial-no=$equipmentName&jig-serial-no=$scannedText',
      );
      if (response.statusCode == 200) {
        bool isValid = response.data['result']['isUsable'] == true;
        setState(() {
          verificationImagePath = isValid ? "availableMark" : "notAvailableMark";
          jigData['isUsable'] = response.data['result']['isUsable'];
          jigData['useCount'] = response.data['result']['data']['useCount'];
          jigData['useTime'] = response.data['result']['data']['useAccumulationTime'];
          jigData['repairCount'] = response.data['result']['data']['repairCount'];
        });
      } else {
        setState(() {
          verificationImagePath = "notAvailableMark";
        });
      }
    } on DioException catch (e) {
      setState(() {
        verificationImagePath = null;
      });
    }
  }

  Future<bool> changeJig() async {
    final DioClient dioClient = ref.read(dioClientProvider);
    final equipmentId = ref.watch(equipmentProvider)[0]['facilitySerialNo'];
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
      if (response.statusCode == 200) {
        return true;
      } else {
        return false;
      }
    } catch (e) {
      return false;
    }
  }
  
  showSnackBar(BuildContext context) {
    final snackBar = SnackBar(
      content:  const Text(
        '다시 시도해 주세요',
        style: TextStyle(
          color: Colors.white,
          fontSize: 16,
          height: 1,
        ),
      ),
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(10))),
      backgroundColor: const Color(0xff9E9E9E),
      behavior: SnackBarBehavior.floating,
      duration: const Duration(seconds: 2),
      action:
      SnackBarAction(label: '', textColor: Colors.white, onPressed: () {}),
    );

    ScaffoldMessenger.of(context).showSnackBar(snackBar);
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
                                  mainAxisAlignment: MainAxisAlignment.center,
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text('설비 이름 : ${ref.watch(equipmentProvider)[0]['facilitySerialNo']}'),
                                    Text(scannedText.isEmpty ?
                                      "Error scanning" :
                                      'S/N : $scannedText'
                                    ),
                                    if (verificationImagePath != null) Column(
                                      mainAxisAlignment: MainAxisAlignment.center,
                                      crossAxisAlignment: CrossAxisAlignment.start,
                                      children: [
                                        const SizedBox(height: 12),
                                        Container( height:1.0,
                                          width:500.0,
                                          color:Colors.black),
                                        const SizedBox(height: 12),
                                        Text('사용 횟수: ${jigData['useCount']}'),
                                        Text('수리 횟수${jigData['repairCount']}'),
                                        Text('사용량: ${jigData['useTime'].toString().substring(0, 11)}'),
                                      ],
                                    ),
                                  ]
                              )
                          ),
                        ],
                      ),
                    ),
                    const SizedBox(height: 28.0,),
                    if (verificationImagePath != null) SvgPicture.asset('assets/$verificationImagePath.svg')
                    else SvgPicture.asset('assets/raiseErrorMark.svg'),
                    Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        const SizedBox(height: 32.0,),
                        if (verificationImagePath == "availableMark") const Text('사용 가능', style: TextStyle(fontSize: 20),)
                        else if (verificationImagePath == null) const Text('인식 오류 입니다.\n다시 촬영해주세요', style: TextStyle(fontSize: 20),)
                        else const Text('다른 지그를 사용해 주세요', style: TextStyle(fontSize: 20),),
                      ],
                    ),
                    const SizedBox(height: 32.0,),
                    ElevatedButton(
                      onPressed: () async {
                        jigData = {};
                        // 인식 에러
                        if (verificationImagePath == null) {
                          Navigator.of(context).pop();
                        }
                        // 인식 성공
                        else {
                          // 적합
                          if (verificationImagePath == 'availableMark') {
                            if (await changeJig()) {
                              Navigator.push(context, MaterialPageRoute(builder: (context) => const SpeJigList()));
                            } else {
                              showSnackBar(context);
                            }
                          }
                          // 부적합
                          else {
                            Navigator.push(context, MaterialPageRoute(builder: (context) => const SpeJigList()));
                          }
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
