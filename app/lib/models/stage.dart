class StageDO {
  final int stage;

  StageDO({required this.stage});

  factory StageDO.fromJson(Map<String, dynamic> json) {
    return StageDO(stage: json["stage"]);
  }
}