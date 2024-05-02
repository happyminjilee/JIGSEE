import 'dart:async';
import 'dart:io';

import 'package:camera/camera.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:google_ml_kit/google_ml_kit.dart';
import 'package:jigsee/components/header.dart';

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
          TextButton(onPressed: () async {
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
              if (kDebugMode) {
                print(e);
              }
            };
          }, child: const Text("촬영"),)
        ],
      )
    );
  }
}

class DisplayPictureScreen extends StatefulWidget {
  final String imagePath;
  const DisplayPictureScreen({Key? key, required this.imagePath}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _DisplayPictureScreenState();
}

class _DisplayPictureScreenState extends State<DisplayPictureScreen> {
  late String scannedText = "";

  @override
  void initState() {
    super.initState();
    getRecognizedText();
  }

  void getRecognizedText() async {
    final InputImage inputImage = InputImage.fromFilePath(widget.imagePath);

    final textRecognizer =
    GoogleMlKit.vision.textRecognizer(script: TextRecognitionScript.latin);

    RecognizedText recognizedText =
    await textRecognizer.processImage(inputImage);

    await textRecognizer.close();

    for (TextBlock block in recognizedText.blocks) {
      for (TextLine line in block.lines) {
        scannedText = scannedText + line.text + "\n";
      }
    }
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: const CustomAppBar(),
        body: Column(
          children: [
            const Text('지그 정보'),
            Image.file(File(widget.imagePath)),
            const SizedBox(height: 20),
            Text(scannedText == "" ? "Error" : scannedText),
          ],
        )
    );
  }
}