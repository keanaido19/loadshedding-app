class SlotDO {
  final List<int> start;
  final List<int> end;

  SlotDO({required this.start, required this.end});

  factory SlotDO.fromJson(Map<String, dynamic> json) {
    List<int> start = [];
    List<int> end = [];

    for (var d in json['start']) {
      start.add(d);
    }

    for (var d in json['end']) {
      end.add(d);
    }

    return SlotDO(start: start, end: end);
  }
}