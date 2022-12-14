import 'dart:convert';

import 'package:app/models/town.dart';
import 'package:app/src/common.dart';
import 'package:http/http.dart';

class TownController {
  Future<List<TownDO>> towns(String province) async {
    final response = await get(Uri.parse('http://localhost:5000/towns/$province'));

    List<TownDO> towns = [];

    if (response.statusCode == 200) {
      for (var json in jsonDecode(response.body)) {
        towns.add(TownDO.fromJson(json));
      }
      return towns;
    } else {
      throw ConnectionFailed('PlaceName Service is unavailable');
    }
  }
}