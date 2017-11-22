package una.toolbox;

import java.awt.image.BufferedImage;

public class Sprites {

	/* 
	 * [3]: 0:leftF, 1:normal, 2:rightF
	 * [4]: 0:down, 1:up, 2:left, 3:right
	 */
	public static BufferedImage[][] playerWalk = new BufferedImage[3][4];

	public static BufferedImage[][] playerRun = new BufferedImage[3][4];
	
	static {
		BufferedImage image = Tools.getImage("res\\sprites\\player\\PlayerM.png");
		for(int mode = 0; mode < 3; mode++) {
			for(int direction = 0; direction < 4; direction++) {
				playerWalk[mode][direction] = image.getSubimage(mode*16, direction*20, 16, 20);
			}
		}
		
		for(int mode = 3; mode < 6; mode++) {
			for(int direction = 0; direction < 4; direction++) {
				playerRun[mode-3][direction] = image.getSubimage(mode*16, direction*20, 16, 20);
			}
		}
	}
	
}