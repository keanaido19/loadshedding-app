import 'dart:convert';

import 'package:app/models/schedule.dart';
import 'package:http/http.dart';

import '../src/common.dart';

class ScheduleController {
  Future<ScheduleDO> schedule(String province, String place, int stage) async {
    final response = await get(
        Uri.parse('http://localhost:5000/$province/$place/$stage')
    );
    if (response.statusCode == 200) {
      return ScheduleDO.fromJson(jsonDecode(response.body));
    } else {
      throw ConnectionFailed('Schedule Service is unavailable');
    }
  }
}