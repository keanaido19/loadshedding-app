import 'package:app/models/slot.dart';

class DayDO {
  final List<SlotDO> slots;

  DayDO({required this.slots});

  factory DayDO.fromJson(Map<String, dynamic> json) {
    List<SlotDO> slots = [];
    for (var d in json['slots']) {
      slots.add(SlotDO.fromJson(d));
    }
    return DayDO(slots: slots);
  }
}