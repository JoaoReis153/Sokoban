package filereader;

import java.util.Scanner;

import abstratas_e_interfaces.GameElement;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import utils.Point2D;

public class MapReader {

	File file;

	public MapReader(File file) {
		this.file = file;
	}

	public List<GameElement> read() throws FileNotFoundException {

		Scanner scanner = new Scanner(file);

		String result = "";

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			result += line + "\n";
		}

		System.out.println("");
		System.out.println("^^^^^^^^^^^^^");
		System.out.println(result);
		System.out.println("^^^^^^^^^^^^^");
		List<GameElement> obj = createObject(result);
		scanner.close();

		return obj;
	}

	public List<GameElement> createObject(String line) {
		String[] linesArray = line.split("\n");
		List<GameElement> list = new ArrayList<GameElement>();

		// Adiciona criar e adiciona os GameElements à lista9
		for (int i = 0; i < linesArray.length; i++) {
			String[] objects = linesArray[i].split("");
			for (int j = 0; j < objects.length; j++) {
				list.add(GameElement.criar(objects[j], new Point2D(j, i)));
			}
		}

		return list;
	};
}
