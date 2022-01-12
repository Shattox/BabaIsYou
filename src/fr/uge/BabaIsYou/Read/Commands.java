package fr.uge.BabaIsYou.Read;

import fr.uge.BabaIsYou.Draw.Board;
import fr.uge.BabaIsYou.Element.Element;
import fr.uge.BabaIsYou.Element.Status;
import fr.uge.BabaIsYou.Element.Type;

/**
 * Class for lines commands.
 */
public class Commands {
	/**
	 * Private constructor of Commands, should not be used.
	 */
	private Commands() {
		throw new RuntimeException("Cannot instantiate the Input class.");
	}

	/**
	 * makes the command "--Level (number of the level)"
	 * @param args
	 *         list of arguments.
	 * @param arg
	 *         the argument.
	 * @param b
	 *         the board of game.
	 * @param ind
	 *         the index of the argument in list of argument.
	 * @param file
	 *         the file where are the levels.
	 * @return 
	 *         return the board.
	 */
	private static Board level(String arg, Board b, File file) {
		b = file.readFile(b, arg);
		return b;
	}

	/**
	 * makes the command "--execute word1 word2 word3"
	 * @param args
	 * 		list of arguments.
	 * @param arg
	 * 		the argument.
	 * @param b
	 * 		the board of game.
	 * @param ind
	 * 		the index of the argument in list of argument.
	 * @param file
	 * 		the file where are the levels.
	 * @return 
	 * 		return the board.
	 */
	private static Board execute(String[] args, String arg, Board b, int ind, File file) {
		b = file.readFile(b, "src/level/Level1");

		b.add(new Position(90, 90),new Element(args[ind + 1]+"T", Type.Name, Status.None));
		b.add(new Position(90, 91),new Element(args[ind + 2]+"T", Type.Op,Status.None));

		if((args[ind + 3] + "T").equals("FlagT") || (args[ind + 3] + "T").equals("RockT") || (args[ind + 3] + "T").equals("WallT") || (args[ind + 3] + "T").equals("LavaT") || (args[ind + 3] + "T").equals("BabaT") || (args[ind + 3] + "T").equals("WaterT") || (args[ind + 3] + "T").equals("SkullT") || (args[ind + 3] + "T").equals("PortalT"))
			b.add(new Position(90, 92),new Element(args[ind +3] + "T", Type.Name, Status.None));
		else 
			b.add(new Position(90, 92),new Element(args[ind + 3] + "T", Type.Prop, Status.None));
		return b;
	}

	/**
	 * check and apply input command between "--levels", "--level (name of level)" and "--execute word1 word2 word3".
	 * @param args
	 *         list of arguments.
	 * @param b
	 *         the board of game.
	 * @param file
	 *         the file where are the levels.
	 * @return 
	 *         return the board.
	 */
	public static Board allCommands(String[] args, Board b, File file) {
		var ind = 0;

		for(var arg: args) {
			if(arg.equals("--levels")) {
				if(!args[ind + 1].equals("level")) {
					throw new IllegalArgumentException("level folder name is wrong");
				}
				b = file.readFile(b, "src/level/Level1");
			}
			if(arg.equals("--level")) {
				b = level("src/level/" + args[ind + 1], b, file);
			}
			if(arg.equals("--execute")) {
				b = execute(args, arg, b, ind, file);
			}
			ind++;
		}
		return b;
	}

}