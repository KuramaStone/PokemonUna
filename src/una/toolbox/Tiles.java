package una.toolbox;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Tiles {

	private static final String TILE_MAP_LOC = 
			"res\\data\\tileset.png";
	private static final int size = 16;
	
	private static Map<Integer, BufferedImage> tileIDsInt = new HashMap<>();
	private static Map<BufferedImage, Integer> tileIDsBuf = new HashMap<>();
	
	public static BufferedImage getImage(int id) {
		return tileIDsInt.get(id);
	}
	
	static {
		System.out.println("Loading tileset...");
		setIDs();
		System.out.println("Tileset loaded.");
	}
	
	private static void setIDs() {
		try {
			BufferedImage tileset = ImageIO.read(new File(TILE_MAP_LOC));

			for(int x = 0; x * size < tileset.getWidth(); x++) {
				for(int y = 0; y * size < tileset.getHeight(); y++) {
					BufferedImage image = tileset.getSubimage(x * size, y * size, size, size);
					if(isUnique(image)) {
						tileIDsInt.put(tileIDsInt.size(), image);
						tileIDsBuf.put(image, tileIDsBuf.size());
					}
				}
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isUnique(BufferedImage i1) {
		for(BufferedImage i2 : tileIDsInt.values()) {
			if(compareImage(i1, i2)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * returns true if images are the same
	 */
	private static boolean compareImage(BufferedImage i1, BufferedImage i2) {
		try {
			for(int x = 0; x < i1.getWidth(); x++) {
				for(int y = 0; y < i1.getHeight(); y++) {
					if(i1.getRGB(x, y) != i2.getRGB(x, y)) {
						return false;
					}
				}
			}
			return true;
		}
		catch(Exception e) {
			System.out.println("Failed to compare image files ...");
			return false;
		}
	}

}
