package fr.uge.BabaIsYou.Read;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import fr.uge.BabaIsYou.Draw.Board;
import fr.uge.BabaIsYou.Element.*;

/**
 * Class for file management.
 */
public class File {
	/**
	 * Reads the file corresponding to the given argument and creates the board of the game.
	 * @param b
	 * 		the board of the game.
	 * @param arg
	 * 		the argument which represents the file of the level name.
	 * @return 
	 * 		the board of the game after loading all element.
	 */
	public Board readFile(Board b, String arg) {
		try {
			String line;
			Reader file = new FileReader(arg);
		
			BufferedReader buffer = new BufferedReader(file);
			
			while((line = buffer.readLine()) != null) {
				String elem[] = line.split(" ");
				b.add(new Position(Integer.parseInt(elem[0]), Integer.parseInt(elem[1])), new Element(elem[2], Type.valueOf(elem[3]), Status.None));
			}
			buffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return b;
	}
}