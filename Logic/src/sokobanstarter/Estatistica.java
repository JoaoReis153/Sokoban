package sokobanstarter;

import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Estatistica {

	private static Estatistica INSTANCE;
	private static String NOTHING = "-";
	private static final int numberOfWinners = GameEngine.NUMBEROFWINNERS;
	private static final String FILENAME = "src/top" + numberOfWinners + ".txt";
	private Line[][] fileContent = new Line[GameEngine.getNumberOfLevels()][numberOfWinners];

	private Estatistica() {
		readFile();
	}

	private void write() {
		try {
			FileWriter myWriter = new FileWriter(FILENAME);
			String result = toString();

			myWriter.write(result);
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public void loadGame(String player, int points, int level) {
		addPlayerToTopContenders(level, new Line(points, player));
		write();
	}

	private void addPlayerToTopContenders(int level, Line newPlayer) {

		for (int i = 0; i < fileContent[level].length; i++) {
			if (fileContent[level][i] == null) {
				newPlayer.setPosition(1);
				fileContent[level][i] = newPlayer;
				return;
			}
		}

		Line[] cp = Arrays.copyOf(fileContent[level], numberOfWinners + 1);
		cp[cp.length - 1] = newPlayer;
		Arrays.sort(cp);

		for (int i = 0; i < cp.length; i++) {
			cp[i].position = i + 1;
		}

		fileContent[level] = Arrays.copyOf(cp, Math.min(cp.length, numberOfWinners));

	}

	private void readFile() {
		Scanner sc = getScanner();
		if (!sc.hasNextLine())
			write();

		int level = -1;
		int contender = 0;
		while (sc.hasNextLine()) {
			String data = sc.nextLine();

			if (data.equals(levelTitle(level + 1))) {
				level++;
				contender = 0;

			} else if (level >= 0) {
				if (!data.equals(NOTHING)) {

					Line newLine = new Line(data);
					contender = newLine.getPosition() - 1;
					fileContent[level][contender] = newLine;

				} else if (data.equals(NOTHING)) {
					fileContent[level][contender] = Line.DOESNTEXIST;
				}
				contender++;
			}
		}

		sc.close();
	}

	private String levelTitle(int n) {
		return "----------" + n + "----------";
	}

	private Scanner getScanner() {
		Scanner scanner = null;
		try {
			File myObj = new File(FILENAME);
			try {
				myObj.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			scanner = new Scanner(myObj);

		} catch (FileNotFoundException e) {
			System.err.println(e);
		}
		return scanner;
	}

	public static Estatistica getInstance() {
		if (INSTANCE == null)
			INSTANCE = new Estatistica();
		return INSTANCE;
	}

	@Override
	public String toString() {
		String result = "";
		for (int i = 0; i < fileContent.length; i++) {
			result += levelTitle(i) + "\n";
			for (int j = 0; j < fileContent[i].length; j++) {
				if (fileContent[i][j] == null)
					result += NOTHING + "\n";
				else
					result += fileContent[i][j] + "\n";
			}
		}

		return result;

	}

	private static class Line implements Comparable<Line> {

		public static Line DOESNTEXIST = new Line(-1, "");
		private int position;
		private int points = -1;
		private String name;

		public Line(int points, String name) {
			this.points = points;
			this.name = name;
		}

		public Line(String line) {
			if (!line.equals(NOTHING)) {
				String[] arr = line.split(" ");
				position = Integer.valueOf(arr[0].replace(".", ""));
				points = Integer.valueOf(arr[3]);
				name = arr[1];
			}
		}

		@Override
		public String toString() {
			String result;
			if (points == -1)
				result = NOTHING;
			else
				result = position + ". " + name + " with " + points + " points";
			return result;
		}

		public void setPosition(int pos) {
			this.position = pos;
		}

		public int getPosition() {
			return position;
		}

		@Override
		public int compareTo(Line o) {
			return o.points - points;
		}
	}

}
