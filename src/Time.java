package src;

public class Time {
	private final int hour;
	private final int minute;

	public Time(String input) {
		String[] temp = input.split(":");
		this.hour = Integer.parseInt(temp[0]);
		this.minute = Integer.parseInt(temp[1]);
	}

	public int timeInMinutes() {
		return hour * 60 + minute;
	}

	public int getMinute() {
		return minute;
	}

	public int getHour() {
		return hour;
	}
}
