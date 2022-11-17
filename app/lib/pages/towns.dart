import 'package:app/controllers/towns.dart';
import 'package:app/models/town.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../src/common.dart';
import '../src/widgets.dart';
import '../states/application.dart';

class TownsPage extends StatefulWidget {
  const TownsPage({Key? key, required this.province}) : super(key: key);
  final Province province;

  @override
  State<TownsPage> createState() => _TownsPageState();
}

class _TownsPageState extends State<TownsPage> {
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
          appBarBody = FutureBuilder<List<TownDO>>(
              future: TownController().towns(widget.province.name),
              builder: (context, snapshot) {
                if (snapshot.hasError) {
                  return ConnectionFailedWidget(
                      message: snapshot.error.toString()
                  );
                }
                if (snapshot.hasData) {
                  List<TownDO> towns = snapshot.data!;
                  return ListView(
                    padding: const EdgeInsets.all(20.0),
                    children: <Widget>[
                      StageCard(stage: appState.stageDO!.stage),
                      const SizedBox(height: 15.0),
                      const TitleCard(title: "Towns"),
                      const SizedBox(height: 5.0),
                      ListView.builder(
                        shrinkWrap: true,
                        itemCount: towns.length,
                        itemBuilder: (context, index) {
                          return TownTile(
                            town: towns[index],
                            stage: appState.stageDO!.stage
                          );
                        },
                      ),
                    ],
                  );
                }
                return const LoadingWidget();
              }
          );
        }
        return Scaffold(
          appBar: AppBar(
            leading: IconButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              icon: const Icon(Icons.arrow_back_ios),
            ),
            title: Text(widget.province.name),
          ),
          body: appBarBody,
        );
      }
    );
  }
}
