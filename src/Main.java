package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<String> fileNames = readFileNames(args[0]);
		for (String fileName : fileNames) {
			printToFile(lpWriter(readData(args[0], fileName)), args[1], fileName.substring(0, fileName.length() - 4) + ".lp");
		}
	}

	private static List<String> readFileNames(String dirName) {
		File folder = new File(dirName);
		File[] listOfFiles = folder.listFiles();
		List<String> listOfFileNames = new ArrayList<>();
		assert listOfFiles != null;
		for (File listOfFile : listOfFiles) {
			if (listOfFile.isFile()) {
				listOfFileNames.add(listOfFile.getName());
			}
		}
		return listOfFileNames;
	}

	private static String lpWriter(MovieData[] movieData) {
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
		List<Integer> listOfItemsToRemove;
		for (int i = 0; i < movieData.length; i++) {
			listOfItemsToRemove = new ArrayList<>();
			for (int item: set) {
				if (movieData[item].getEndTimeInMinutes() <= movieData[i].getStartTimeInMinutes()) {
					listOfItemsToRemove.add(item);
				}
			}
			for (Integer integer : listOfItemsToRemove) {
				set.remove(integer);
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
		return output.toString();
	}

	private static void printToFile(String output, String dirname, String fileName) {
		try (PrintWriter out = new PrintWriter(dirname + fileName)) {
			out.println(output);
		} catch (FileNotFoundException e) {System.out.println(e);}
	}

	private static MovieData[] readData(String dirName, String fileName) {
		Class<Main> classVar = Main.class;
		InputStream inputStream = classVar.getResourceAsStream("/" + dirName + fileName);
		String data;
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
			movieData[i] = new MovieData(Weekday.weekdayFinder(temp[0]), new Time(temp[1]), Integer.parseInt(temp[2]), temp[3], Double.parseDouble(temp[4]));
		}
		return movieData;
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
