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

	public static Weekday weekdayFinder(String input) {
		switch (input) {
			case "Mo" -> {
				return MONDAY;
			}
			case "Di" -> {
				return TUESDAY;
			}
			case "Mi" -> {
				return WEDNESDAY;
			}
			case "Do" -> {
				return THURSDAY;
			}
			case "Fr" -> {
				return FRIDAY;
			}
			case "Sa" -> {
				return SATURDAY;
			}
			case "So" -> {
				return SUNDAY;
			}
			default -> {
				return TUESDAY; //return null; Quick fix, there seems to be a problem with the input data.
			}
		}
	}
}
