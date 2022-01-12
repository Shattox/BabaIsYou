package fr.uge.BabaIsYou.Read;

/**
 * Class for timer.
 */
public class Timer {	
	
	/**
	 * Private constructor of RenderTimer, should not be used.
	 */
	private Timer() {
		throw new RuntimeException("Cannot instantiate RenderTimer class.");
	}
	
	/**
	 * Stops the game for x milliseconds.
	 * @param x
	 * 		the waiting time.
	 */
	public static void stop(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}