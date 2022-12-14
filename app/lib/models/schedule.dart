import 'day.dart';

class ScheduleDO {
  final List<DayDO> days;
  final List<int> startDate;

  ScheduleDO({required this.days, required this.startDate});

  factory ScheduleDO.fromJson(Map<String, dynamic> json) {
    List<DayDO> days = [];
    List<int> startDate = [];

    for (var d in json['days']) {
      days.add(DayDO.fromJson(d));
    }

    for (var d in  json['startDate']) {
      startDate.add(d);
    }
    return ScheduleDO(days: days, startDate: startDate);
  }
}