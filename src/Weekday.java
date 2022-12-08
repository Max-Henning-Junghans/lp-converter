package src;

public enum Weekday {
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY,
	SUNDAY;

	public static int timeOfDay(Weekday weekday) {
		switch (weekday){
			case MONDAY -> {return 0;}
			case TUESDAY -> {return 1440;}
			case WEDNESDAY -> {return 2880;}
			case THURSDAY -> {return 4320;}
			case FRIDAY -> {return 5760;}
			case SATURDAY -> {return 7200;}
			case SUNDAY -> {return 8640;}
		}
		return 0;  // This case won't happen and this tool won't be used enough to need error management.
	}
}
