package fr.uge.BabaIsYou.Draw;

import fr.umlv.zen5.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Class for Area.
 */
public class Area {
	private Image img;
	private final int width;
	private final int height;
	private final int case_x;
	private final int case_y;

	/**
	 * Constructor for the Area.
	 * @param width
	 * 		width of the window.
	 * @param height
	 * 		height of the window.
	 */
	public Area(int width, int height) {
		this.width = width;
		this.height = height;
		this.case_x = width / 40;
		this.case_y = height / 27;
	}

	/**
     * This function try to read a file which matches as an image.
     * @param image 
	 * 		the name of the image to be read.
     */
	private void loadImage(String image) {
		try {
			img = ImageIO.read(new File("src/img/" + image + ".gif"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This functions draw every elements in the Board b.
	 * @param context
	 * 		context to draw on the window.
	 * @param b 
	 * 		the board of the game.
	 */
	public void drawBoard(ApplicationContext context, Board b) {
		context.renderFrame(graphics -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new  Rectangle2D.Float(0, 0, width, height));

			for(var pos : b.Map().keySet()) {
				if(b.Map().get(pos) != null) {
					for(int i = 0; i < b.Map().get(pos).size(); i++) {
						loadImage(b.Map().get(pos).get(i).name());
						graphics.drawImage(img, pos.x() * case_x, pos.y() * case_y, case_x, case_y, null);
					}
				}
			}
		});
	}
}
