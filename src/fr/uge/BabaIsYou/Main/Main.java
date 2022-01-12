package fr.uge.BabaIsYou.Main;

import java.awt.*;
import fr.umlv.zen5.*;
import fr.uge.BabaIsYou.Read.*;
import fr.uge.BabaIsYou.Draw.*;
import fr.uge.BabaIsYou.Element.*;

/**
 * Main class of the program.
 */
public class Main {
	/**
	 * Main method.
	 * @param args
	 * 		array of String.
	 */
	public static void main(String[] args) {
		Application.run(Color.BLACK, context -> {
			ScreenInfo screenInfo = context.getScreenInfo();

			/* Get screen size. */
			Float width = screenInfo.getWidth();
			Float height = screenInfo.getHeight();

			File file = new File();
			Board board = new Board();
			Rule rule = new Rule();
			
			var lvl_path = new String("src/level/Level1");

			var num_lvl = 1;
			var ind = 0;
			var tmp = 0;

			board = Commands.allCommands(args, board, file);
			
			for(var arg: args) {
				if(arg.equals("--level")) {
					num_lvl = Integer.valueOf(String.valueOf((args[ind + 1].charAt(args[ind + 1].length() - 1))));
					if(num_lvl > 8 || num_lvl < 0)
						throw new IllegalArgumentException("0 < level_path <= 8");
				}
				ind++;
			}
			if(board.Map().isEmpty())
				board = file.readFile(board, lvl_path.substring(0, lvl_path.length() - 1) + num_lvl);

			Area area = new Area(width.intValue(), height.intValue());

			Player player = new Player();

			for(;;) {
				if(Rule.Win(board, player)) {
					if(num_lvl < 8) {
						player = new Player();
						board = new Board();
						board = file.readFile(board, lvl_path.substring(0, lvl_path.length() - 1) + ++num_lvl);
					}
					else { break; }
				}
				Input.inputHandle(context, player);
				rule.buildRules(board);
				board.resetStatus();
				rule.applyRules(board);
				player.buildPlayer(board, player, rule);
				board.setStatusLst();
				tmp = Rule.Push(board, player);
				rule.applyRules(board);
				player.Move(board, player, tmp);
				area.drawBoard(context, board);
				Timer.stop(60);
			}
		});
	}
}
