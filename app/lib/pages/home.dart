import 'dart:async';

import 'package:app/controllers/stage.dart';
import 'package:app/models/stage.dart';
import 'package:app/src/common.dart';
import 'package:app/src/widgets.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../states/application.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final List<Province> provinces = Province.values;
  late Widget appBarBody;

  @override
  Widget build(BuildContext context) {
    return Consumer<ApplicationState>(
        builder: (context, appState, child) {
          if (appState.stageDO == null) {
            appBarBody = const ConnectionFailedWidget(
                message: "Connection Failed: Stage Service is unavailable"
            );
          } else {
            appBarBody = ListView(
              padding: const EdgeInsets.all(20.0),
              children: <Widget>[
                StageCard(stage: appState.stageDO!.stage),
                const SizedBox(height: 15.0),
                const TitleCard(title: "Provinces"),
                const SizedBox(height: 5.0),
                ListView.builder(
                  shrinkWrap: true,
                  itemCount: provinces.length,
                  itemBuilder: (context, index) {
                    return ProvinceTile(province: provinces[index]);
                  },
                ),
              ],
            );
          }
          return Scaffold(
            appBar: AppBar(
              leading: const Icon(Icons.emoji_objects_outlined),
              title: const Text("Load Shedding"),
            ),
            body: appBarBody,
          );
        }
    );
  }
}
