class TownDO {
  final String name;
  final String province;

  TownDO({required this.name, required this.province});

  factory TownDO.fromJson(Map<String, dynamic> json) {
    return TownDO(name: json["name"], province: json["province"]);
  }
}