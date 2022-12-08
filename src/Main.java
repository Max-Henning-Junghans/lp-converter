package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		/**
		 * Idee: Jedes Abspielen eines Films: Variablen x_1 bis x_x, x = 1 bedeutet Film wird gezeigt, 0 Film wird nicht gezeigt,
		 * Für jeden Start eines Films: Einschränkungen für die Filme, die parallel laufen.
		 * x_1 + x_x <= 1
		 * Für gleichen Film mehrmals: x_1 + x_1' <= 1
		 * Eventuell Zeit als Minuten seit Montag morgen
		 */
		MovieData[] movieData = readData(args[0]);
		Arrays.sort(movieData);

		List<MovieData> equalObjects = new ArrayList<>();

		// Find the equal objects in the list
		for (int i = 0; i < movieData.length; i++) {
			for (int j = i + 1; j < movieData.length; j++) {
				if (movieData[i].equals(movieData[j])) {
					equalObjects.add(movieData[i]);
				}
			}
		}
		StringBuilder output = new StringBuilder("max: " + movieData[0].getScore() + "x1");
		for (int i = 1; i < movieData.length; i++) {
			output.append(" + ").append(movieData[i].getScore()).append("x").append((i + 1));
		}
		output.append(";").append(System.lineSeparator());


		HashSet<Integer> set = new HashSet<>();
		List listOfItemsToRemove = null;
		for (int i = 0; i < movieData.length; i++) {
			listOfItemsToRemove = new ArrayList();
			for (int item: set) {
				if (movieData[item].getEndTimeInMinutes() <= movieData[i].getStartTimeInMinutes()) {
					listOfItemsToRemove.add(item);
				}
			}
			for (int j = 0; j < listOfItemsToRemove.size(); j++) {
				set.remove(listOfItemsToRemove.get(j));
			}
			output.append("x").append(i + 1);
			for (Integer item: set) {
				output.append(" + x").append(item + 1);
			}
			set.add(i);
			try { // Not a good solution
				while (movieData[i].getStartTimeInMinutes() == movieData[i + 1].getStartTimeInMinutes()) {
					set.add(i + 1);
					i++;
					output.append(" + x").append(i + 1);
				}
			} catch (Exception ignored) {
			}

			output.append(" <= 1;").append(System.lineSeparator());
		}

		boolean first;
		for (MovieData equalObject : equalObjects) {
			first = true;
			for (int j = 0; j < movieData.length; j++) {
				if (movieData[j].equals(equalObject)) {
					if (first) {
						output.append("x").append(j + 1);
						first = false;
					} else {
						output.append(" + x").append(j + 1);
					}
				}
			}
			output.append("<= 1;").append(System.lineSeparator());
		}


		output.append("bin x1");
		for (int i = 1; i < movieData.length; i++) {
			output.append(", x").append(i + 1);
		}
		output.append(";");
		try (PrintWriter out = new PrintWriter("output.txt")) {
			out.println(output);
		} catch (FileNotFoundException e) {System.out.println(e);}
	}

	private static MovieData[] readData(String fileName) {
		Class<Main> classVar = Main.class;
		InputStream inputStream = classVar.getResourceAsStream("/datainput/" + fileName);
		String data = null;
		try {
			data = readFromInputStream(inputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		System.out.println(data);
		String[] lines = data.split("\n");
		MovieData[] movieData = new MovieData[lines.length];
		String[] temp;
		for (int i = 0; i < lines.length; i++) {
			temp = lines[i].split(";");
			movieData[i] = new MovieData(weekdayFinder(temp[0]), new Time(temp[1]), Integer.parseInt(temp[2]), temp[3], Double.parseDouble(temp[4]));
		}
		return movieData;
	}

	private static Weekday weekdayFinder(String input) {
		switch (input) {
			case "Mo" -> {
				return Weekday.MONDAY;
			}
			case "Di" -> {
				return Weekday.TUESDAY;
			}
			case "Mi" -> {
				return Weekday.WEDNESDAY;
			}
			case "Do" -> {
				return Weekday.THURSDAY;
			}
			case "Fr" -> {
				return Weekday.FRIDAY;
			}
			case "Sa" -> {
				return Weekday.SATURDAY;
			}
			case "So" -> {
				return Weekday.SUNDAY;
			}
		}
		return null;
	}

	private static String readFromInputStream(InputStream inputStream)
			throws IOException {
		StringBuilder resultStringBuilder = new StringBuilder();
		try (BufferedReader br
					 = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				resultStringBuilder.append(line).append("\n");
			}
		}
		return resultStringBuilder.toString();
	}
}
