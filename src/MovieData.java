package src;

public class MovieData implements Comparable<MovieData>{
	private final Weekday weekday;
	private final Time startTime;
	private final int runTime;
	private final String name;
	private final double score;
	private final int startTimeInMinutes;
	private final int endTimeInMinutes;

	public MovieData(Weekday weekday, Time startTime, int runTime, String name, double score) {
		this.weekday = weekday;
		this.startTime = startTime;
		this.runTime = runTime;
		this.name = name;
		this.score = score;
		startTimeInMinutes = Weekday.timeOfDay(weekday) + startTime.timeInMinutes();
		endTimeInMinutes = startTimeInMinutes + runTime;
	}

	public String getName() {
		return name;
	}

	public double getScore() {
		return score;
	}

	public int getStartTimeInMinutes() {
		return startTimeInMinutes;
	}

	public int getEndTimeInMinutes() {
		return endTimeInMinutes;
	}

	@Override
	public int compareTo(MovieData movieData2) {
		return startTimeInMinutes - movieData2.getStartTimeInMinutes();
	}

	public String toString() {
		return "Movie: " + name + " Starttime: " + startTimeInMinutes + " Endtime: " + endTimeInMinutes + System.lineSeparator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (o == null || getClass() != o.getClass()) {return false;}

		MovieData movieData = (MovieData) o;

		return name.equals(movieData.name);
	}
}
