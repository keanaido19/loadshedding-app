enum Province {
  easternCape("Eastern Cape", "EC"),
  freeState("Free State", "FS"),
  gauteng("Gauteng", "GP"),
  kwaZuluNatal("KwaZulu-Natal", "KZN"),
  limpopo("Limpopo", "LP"),
  mpumalanga("Mpumalanga", "MP"),
  northernCape("Northern Cape", "NC"),
  northWest("North West", "NW"),
  westernCape("Western Cape", "WC");

  const Province(this.name, this.convention);

  final String name;
  final String convention;
}

enum Months {
  january("January"),
  february("February"),
  march("March"),
  april("April"),
  may("May"),
  june("June"),
  july("July"),
  august("August"),
  september("September"),
  october("October"),
  november("November"),
  december("December");

  const Months(this.name);
  final String name;
}

enum DaysOfTheWeek {
  monday("Monday"),
  tuesday("Tuesday"),
  wednesday("Wednesday"),
  thursday("Thursday"),
  friday("Friday"),
  saturday("Saturday"),
  sunday("Sunday");

  const DaysOfTheWeek(this.name);
  final String name;
}

class ConnectionFailed implements Exception {
  String cause;
  ConnectionFailed(this.cause);

  @override
  String toString() {
    return 'Connection failed: $cause';
  }
}
