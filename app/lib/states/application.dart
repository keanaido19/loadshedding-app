import 'dart:async';

import 'package:app/controllers/stage.dart';
import 'package:app/models/town.dart';
import 'package:flutter/material.dart';

import '../models/stage.dart';

Stream<StageDO?> getStageStream() {
  late final StreamController<StageDO?> controller;
  final stageController = StageController();

  bool shouldContinue = true;

  controller = StreamController(
    onListen: () async {
      while (shouldContinue) {
        try {
          controller.add(await stageController.stage);
        } catch (err) {
          controller.add(null);
        }
        await Future<void>.delayed(const Duration(seconds: 1));
      }
    },
    onCancel: () => shouldContinue = false,
  );
  return controller.stream;
}

class ApplicationState extends ChangeNotifier {
  StreamSubscription<StageDO?>? _stageStreamSubscription;

  StageDO? stageDO = StageDO(stage: 0);

  ApplicationState() {
    init();
  }

  void init() {
    _stageStreamSubscription = getStageStream()
        .listen((event) {
          stageDO = event;
          notifyListeners();
        });

    notifyListeners();
  }

  @override
  void dispose() {
    _stageStreamSubscription?.cancel();
    super.dispose();
  }
}