import 'package:app/controllers/schedule.dart';
import 'package:app/models/day.dart';
import 'package:flutter/material.dart';

import '../models/schedule.dart';
import '../models/town.dart';
import '../src/widgets.dart';

class SchedulePage extends StatefulWidget {
  const SchedulePage({Key? key, required this.town, required this.stage}) 
      : super(key: key);
  final TownDO town;
  final int stage;

  @override
  State<SchedulePage> createState() => _SchedulePageState();
}

class _SchedulePageState extends State<SchedulePage> {

  DateTime getStartDate(List<int> startDate) {
    return DateTime(startDate[0], startDate[1], startDate[2]);
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Schedule'),
        leading: IconButton(
          icon: const Icon(Icons.arrow_back_ios),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ),
      body: FutureBuilder<ScheduleDO>(
        future: ScheduleController().schedule(
            widget.town.province, widget.town.name, widget.stage
        ),
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            return ConnectionFailedWidget(
                message: snapshot.error.toString()
            );
          }
          if (snapshot.hasData) {
            List<DayDO> days = snapshot.data!.days;
            var date = getStartDate(snapshot.data!.startDate);
            
            return Center(
              child: SizedBox(
                width: 400.0,
                child: ScrollConfiguration(
                  behavior: ScrollConfiguration.of(context)
                      .copyWith(scrollbars: false),
                  child: ListView(
                      children: <Widget>[
                        const SizedBox(height: 20.0),
                        TitleCard(title: widget.town.name),
                        const SizedBox(height: 5.0),
                        ListView.builder(
                            shrinkWrap: true,
                            itemCount: days.length,
                            itemBuilder: (context, index) {
                              return CalendarCard(
                                  day: days[index],
                                  date: date.add(Duration(days: index)),
                                  stage: widget.stage
                              );
                            })
                      ]
                  ),
                ),
              ),
            );
          }
          return const LoadingWidget();
        },
      ),
    );
  }
}
