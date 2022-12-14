import 'dart:ffi';

import 'package:app/models/schedule.dart';
import 'package:app/pages/towns.dart';
import 'package:app/src/common.dart';
import 'package:flutter/material.dart';

import '../models/day.dart';
import '../models/slot.dart';
import '../models/stage.dart';
import '../models/town.dart';
import '../pages/schedule.dart';

class TitleCard extends StatelessWidget {
  const TitleCard({Key? key, required this.title}) : super(key: key);
  final String title;

  @override
  Widget build(BuildContext context) {
    return Card(
        elevation: 3.0,
        color: Colors.blueGrey,
        shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10.0)
        ),
        child: Padding(
          padding: const EdgeInsets.all(10.0),
          child: Center(
            child: Text(
              title,
              style: const TextStyle(
                fontSize: 36.0,
                fontWeight: FontWeight.bold,
                color: Colors.white
              ),
            ),
          ),
        )
    );
  }
}

class ProvinceTile extends StatelessWidget {
  const ProvinceTile({Key? key, required this.province}) : super(key: key);
  final Province province;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3.0,
      shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0)
      ),
      child: ListTile(
        onTap: () {
          Navigator.of(context).push(
            MaterialPageRoute<void>(
                builder: (context) {
                  return TownsPage(province: province);
                }
            )
          );
        },
        horizontalTitleGap: 15.0,
        minVerticalPadding: 20.0,
        key: Key(province.name),
        title: Text(
          province.name,
          style: const TextStyle(
            fontWeight: FontWeight.bold,
            fontSize: 18
          )
        ),
        trailing: const Icon(Icons.arrow_forward_ios),
        leading: CircleAvatar(
          foregroundColor: Colors.blue,
          backgroundColor: Colors.transparent,
          child: Text(province.convention),
        )
      ),
    );
  }
}

class StageCard extends StatefulWidget {
  const StageCard({Key? key, required this.stage}) : super(key: key);
  final int stage;

  @override
  State<StageCard> createState() => _StageCardState();
}

class _StageCardState extends State<StageCard> {
  Color _fontColor = Colors.black45;
  IconData _icon = Icons.power_off;
  
  Color _getFontColor() {
    _fontColor = Colors.black45;
    _icon = Icons.power_off;

    if (widget.stage == 0) _icon = Icons.emoji_objects_outlined;
    if (widget.stage < 2) return Colors.green;
    if (widget.stage < 5) return Colors.orange;
    if (widget.stage < 7) return Colors.redAccent;

    _fontColor = Colors.white54;
    return Colors.black;
  }
  
  @override
  Widget build(BuildContext context) {
    return Card(
        elevation: 3.0,
        color: _getFontColor(),
        shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(10.0)
        ),
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Center(
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  Icon(_icon, size: 42, color: _fontColor,),
                  const SizedBox(width: 10.0,),
                  Text(
                      "Stage ${widget.stage}",
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                        fontSize: 36,
                        color: _fontColor
                      )
                  ),
                ],
              )
          ),
        )
    );
  }
}

class TownTile extends StatelessWidget {
  const TownTile({Key? key, required this.town, required this.stage})
      : super(key: key);
  final TownDO town;
  final int stage;

  @override
  Widget build(BuildContext context) {
    return Card(
      elevation: 3.0,
      shape: RoundedRectangleBorder(
          borderRadius: BorderRadius.circular(10.0)
      ),
      child: ListTile(
          onTap: () {
            Navigator.of(context).push(
                MaterialPageRoute<void>(
                    builder: (context) {
                      return SchedulePage(town: town, stage: stage);
                    }
                )
            );
          },
          horizontalTitleGap: 15.0,
          minVerticalPadding: 20.0,
          key: Key(town.name),
          title: Text(
              town.name,
              style: const TextStyle(
                  fontWeight: FontWeight.bold,
                  fontSize: 18
              )
          ),
          trailing: const Icon(Icons.arrow_forward_ios),
          leading: const Icon(Icons.location_city_outlined),
      ),
    );
  }
}

class LoadingWidget extends StatelessWidget {
  const LoadingWidget({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return const Center(child: CircularProgressIndicator());
  }
}

class ConnectionFailedWidget extends StatelessWidget {
  const ConnectionFailedWidget({Key? key, required this.message}) 
      : super(key: key);
  final String message;
  
  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.all(20.0),
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            const Icon(
              Icons.error_outline,
              size: 150,
            ),
            const SizedBox(
                height: 10.0
            ),
            Text(
                message,
                textAlign: TextAlign.center,
                style: Theme.of(context).textTheme.headline6
            ),
          ],
        ),
      ),
    );
  }
}

class SlotCard extends StatelessWidget {
  const SlotCard({Key? key, required this.slot}) : super(key: key);
  final SlotDO slot;

  int _formatHour(int hour) {
    if (hour > 12) return hour - 12;
    return hour;
  }

  String _formatMinute(int minutes) {
    return minutes == 0 ? "" : ":$minutes";
  }

  String _getMeridiem(int hours) {
    if (hours < 12) return "am";
    return "pm";
  }

  String _getSlotTime() {
    List<int> start = slot.start;
    List<int> end = slot.end;

    int startH = _formatHour(start[0]);
    String startM = _formatMinute(start[1]);
    String startMeridiem = _getMeridiem(start[0]);

    int endH = _formatHour(end[0]);
    String endM = _formatMinute(end[1]);
    String endMeridiem = _getMeridiem(end[0]);

    if (startMeridiem == endMeridiem) startMeridiem = "";

    return "$startH$startM$startMeridiem - $endH$endM$endMeridiem";
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 7.0),
      child: Text(
        _getSlotTime(),
        style: const TextStyle(
          fontWeight: FontWeight.bold,
          color: Colors.blue,
          fontSize: 18.0
        )
      ),
    );
  }
}

class DayCardBanner extends StatelessWidget {
  const DayCardBanner({Key? key, required this.date}) : super(key: key);
  final DateTime date;

  String formatDate() {
    int year = date.year;
    int month = date.month - 1;
    int day = date.day;
    int weekDay = date.weekday - 1;
    return "${DaysOfTheWeek.values[weekDay].name}, "
        "$day ${Months.values[month].name} $year";
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        borderRadius: BorderRadius.only(
            topLeft: Radius.circular(10.0),
            topRight: Radius.circular(10.0)
        ),
        color: Colors.blue,
      ),
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 15.0),
        child: Center(
            child: Text(
                formatDate(),
                style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                    fontSize: 24
                )
            )
        ),
      ),
    );
  }
}

class StageText extends StatelessWidget {
  const StageText({Key? key, required this.stage}) : super(key: key);
  final int stage;

  IconData _getIcon() {
    if (stage == 0) return Icons.emoji_objects_outlined;
    return Icons.power_off;
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 125,
      child: Padding(
        padding: const EdgeInsets.symmetric(horizontal: 20.0),
        child: Center(
          child: Column(
            children: <Widget>[
              Text(
                  "Stage $stage",
                  style: const TextStyle(
                    fontSize: 22,
                    fontWeight: FontWeight.bold,
                    color: Colors.white,
                  )
              ),
              const SizedBox(height: 10.0),
              Icon(
                _getIcon(),
                color: Colors.white
              ),
            ]
          ),
        ),
      ),
    );
  }
}

class CalendarCard extends StatelessWidget {
  const CalendarCard({
    Key? key,
    required this.day,
    required this.date,
    required this.stage
  })
      : super(key: key);
  final DayDO day;
  final DateTime date;
  final int stage;

  Color _getCardColor() {
    if (stage < 2) return Colors.green;
    if (stage < 5) return Colors.orange;
    if (stage < 7) return Colors.redAccent;
    return Colors.black;
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: <Widget>[
        const SizedBox(height: 10.0),
        Card(
            color: _getCardColor(),
            elevation: 1.0,
            shape: RoundedRectangleBorder(
                borderRadius: BorderRadius.circular(10.0)
            ),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              children: <Widget>[
                DayCardBanner(date: date),
                Row(
                  children: <Widget>[
                    StageText(stage: stage),
                    Container(
                        decoration: const BoxDecoration(
                          color: Colors.white,
                          borderRadius: BorderRadius.only(
                            bottomRight: Radius.circular(10.0),
                          ),
                        ),
                        width: 267,
                        child: ListView(
                            shrinkWrap: true,
                            children: <Widget>[
                              ListView.builder(
                                  shrinkWrap: true,
                                  itemCount: day.slots.length * 2,
                                  itemBuilder: (context, index) {
                                    var i = index ~/ 2;
                                    if (index == day.slots.length * 2 - 1) {
                                      return const Divider(
                                        color: Colors.transparent,
                                        height: 0.0,
                                      );
                                    }
                                    if (index.isOdd) {
                                      return const Divider(
                                        // color: Colors.transparent,
                                        height: 7.0,
                                      );
                                    }
                                    return SlotCard(slot: day.slots[i]);
                                  }
                              ),
                            ]
                        )
                    )
                  ],
                ),
              ],
            )
        ),
        const SizedBox(height: 10.0),
      ],
    );
  }
}



