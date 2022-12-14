import 'dart:convert';

import 'package:app/models/stage.dart';
import 'package:http/http.dart';

import '../src/common.dart';

class StageController {
  Future<StageDO> get stage async {
    final response = await get(Uri.parse('http://localhost:5000/stage'));

    if (response.statusCode == 200) {
      return StageDO.fromJson(jsonDecode(response.body));
    } else {
      throw ConnectionFailed('Stage Service is unavailable');
    }
  }
}