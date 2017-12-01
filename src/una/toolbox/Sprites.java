package una.toolbox;

import java.awt.image.BufferedImage;

public class Sprites {

	/*
	 * [3]: 0:leftF, 1:normal, 2:rightF [4]: 0:down, 1:up, 2:left, 3:right
	 */
	public static BufferedImage[][] playerWalk = new BufferedImage[3][4];

	public static BufferedImage[][] playerRun = new BufferedImage[3][4];

	// [tile][tiles]
	public static BufferedImage[][] tiles = new BufferedImage[99][8];

	static {
		player();
		tiles();
	}

	private static void tiles() {
		BufferedImage image = Tools.getImage("res\\sprites\\animations.png");

		
		loadSet(image, 0, 8); // Water 1
		loadSet(image, 2, 5); // Flowers
		loadSet(image, 4, 4); // grass
		loadSet(image, 6, 8); // Water 2
		loadSet(image, 7, 8); // Water 3
		loadSet(image, 8, 8); // Water 4
		loadSet(image, 9, 8); // Water 5
		loadSet(image, 10, 8); // Water 6
		loadSet(image, 11, 8); // Water 7

	}

	private static BufferedImage[] loadSet(BufferedImage image, int y, int length) {
		BufferedImage[] tiles = new BufferedImage[length];
		int x = 0;
		for(int i = 0; i < length; i++) {
			tiles[i] = image.getSubimage(x, y*16, 16, 16);
			x += 17;
		}
		
		Sprites.tiles[y] = tiles;
		
		return tiles;
	}

	private static void player() {
		BufferedImage image = Tools.getImage("res\\sprites\\player\\PlayerM.png");
		for(int mode = 0; mode < 3; mode++) {
			for(int direction = 0; direction < 4; direction++) {
				playerWalk[mode][direction] = image.getSubimage(mode * 16, direction * 20, 16, 20);
			}
		}

		for(int mode = 3; mode < 6; mode++) {
			for(int direction = 0; direction < 4; direction++) {
				playerRun[mode - 3][direction] = image.getSubimage(mode * 16, direction * 20, 16, 20);
			}
		}
	}

}