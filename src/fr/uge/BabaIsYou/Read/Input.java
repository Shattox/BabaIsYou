package fr.uge.BabaIsYou.Read;

import fr.umlv.zen5.*;
import fr.umlv.zen5.Event.Action;
import fr.uge.BabaIsYou.Element.Player;

/**
 * Class for input.
 */
public class Input {
	/**
	 * Private constructor of Input, should not be used.
	 */
	private Input() {
		throw new RuntimeException("Cannot instantiate the Input class.");
	}
	
	/**
	 * Detects if the left button has been used and has left the context.
	 * @param context
	 * 		ApplicationContext of the game.
	 * @param player
	 * 		the player.of the game.
	 */
	public static void inputHandle(ApplicationContext context, Player player) {
		if(Input.detectInput(context, player) == 1) {
	          context.exit(0);
		}
	}
	
	/**
	 * Applies actions according to the given key pressed/released.
	 * @param k
	 * 		the key pressed or released.
	 * @param player
	 * 		player affected by key press.
	 * @param mode
	 * 		if is key press is true else if is key release is false.
	 * @return
	 * 		return 1 if exit key was pressed, else 0.
	 */
	private static int keypressesToAction(KeyboardKey k, Player player, boolean mode) {
		KeyboardKey[] keys = player.getKeys();
		
		if(k == KeyboardKey.Q) {
			return 1;
		}
		for(int i = 0; i < keys.length; i++) {
			if(keys[i].equals(k)) {
				player.setKeypress(i, mode);
			}
		}
		return 0;
	}
	
	/**
	 * Detects key presses and releases by the user.
	 * @param context
	 * 		ApplicationContext of the game.
	 * @param player
	 * 		the player of the game.
	 * @return
	 * 		return 1 if exit key was detected, else 0.
	 */
	public static int detectInput(ApplicationContext context,Player player) {
		Event event = context.pollEvent();
		int keyboardActionType = 0;

		if(event != null) {
			Action action = event.getAction();
			KeyboardKey key = event.getKey();
			if(action == Action.KEY_PRESSED) {
				keyboardActionType = keypressesToAction(key, player, true);
			}
			else if(action == Action.KEY_RELEASED) {
				keyboardActionType = keypressesToAction(key, player, false);
			}
		}
		return keyboardActionType;
	}
}