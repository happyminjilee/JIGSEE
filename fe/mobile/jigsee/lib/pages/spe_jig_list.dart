import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:jigsee/api/dio_instance.dart';
import 'package:jigsee/api/provider.dart';
import 'package:jigsee/components/header.dart';
import 'package:jigsee/consts/size.dart';

class SpeJigList extends ConsumerWidget {
  const SpeJigList({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {
    final equipmentName = ref.watch(equipmentProvider)[0]['facilitySerialNo'];
    final equipmentId = ref.watch(equipmentProvider)[0]['id'];
    final DioClient dioClient = ref.read(dioClientProvider);

    Future<List<dynamic>> fetchEquipmentDetails() async {
      try {
        var response = await dioClient.get(
            '/facility-item/inspection/jig-item?facility-id=$equipmentId',
        );
        List<dynamic> data = response.data['result']['serialNos'];
        // return data;
        return data;
      } catch (e) {
        return [
          'Error'
        ];
      }
    }

    return Scaffold(
      appBar: const CustomAppBar(),
      body: Column(
        children: [
          const SizedBox(height: largeGap),
          const Text('교체 지그 리스트', style: TextStyle(
            fontSize: 24,
            fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: largeGap),
          Text(equipmentName, style: const TextStyle(
              fontSize: 20,
              fontWeight: FontWeight.bold,
            ),
          ),
          const SizedBox(height: largeGap),
          Expanded(
            child: Container(
              padding: const EdgeInsets.only(left: 12, right: 12),
              child: FutureBuilder<List<dynamic>>(
                future: fetchEquipmentDetails(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.done) {
                    if (snapshot.hasError) {
                      return const Text('불러 오기 실패');
                    }
                    List<dynamic> details = snapshot.data ?? [];
                    if (details.isNotEmpty && details[0] == 'Error') {
                      return Container(
                          width: 1000,
                          alignment: Alignment.center,
                          child: const Text('네트워크 오류 발생')
                      );
                    }
                    return ListView.builder(
                      itemCount: details.length,
                      itemBuilder: (context, index) => Card(
                        child: ListTile(
                          title: Text('S/N: ${details[index]}'),
                          trailing: const Icon(Icons.arrow_forward_ios),
                          onTap: () {
                            ref.read(selectedJigProvider.notifier).state = details[index];
                            Navigator.pushNamed(context, '/ocr');
                          },
                        ),
                      ),
                    );
                  } else {
                    return const Center(child: CircularProgressIndicator());
                  }
                },
              ),
            )
          ),
        ],
      )
    );
  }
}
