package fr.uge.BabaIsYou.Element;

/**
 * Enum for status of element.
 */
public enum Status {
	/**
	 * You -> you play the element.
	 */
	You,
	/**
	 * Stop -> the element can't be moved.
	 */
	Stop,
	/**
	 * Push -> the element can be pushed.
	 */
	Push,
	/**
	 * Hot -> the element hot destroy element with melt status.
	 */
	Hot,
	/**
	 * Melt -> the element is melt.
	 */
	Melt, 
	/**
	 * Win -> the element make you win the game.
	 */
	Win, 
	/**
	 * Sink -> Destroy element on sink element and itself.
	 */
	Sink, 
	/**
	 * Defeat -> destroy element.
	 */
	Defeat, 
	/**
	 * Tp -> teleport to the next closest element with this status.
	 */
	Tp, 
	/**
	 * None -> default value.
	 */
	None
}