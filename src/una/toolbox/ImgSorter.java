package una.toolbox;

import java.awt.image.BufferedImage;

public class ImgSorter {

	public BufferedImage[] loadImages(String path) {
		BufferedImage[] images = new BufferedImage[1000];
		BufferedImage image = Tools.getImage(path);

		int count = 0;
		int yOffset = 0;

		int lastX = 0;
		int j = 0;
		while(yOffset != 11) {
			for(int x = 0; x < image.getWidth(); x++) {
				boolean foundPixel = check(image, x);

				if(!foundPixel) {

					if(!check(image, x+1))
						break;
					
					images[count++] = image.getSubimage(lastX, yOffset * 23, x - lastX, 20);
					
					if(j == 2) {
						images[count++] = flip(images[count-2]);
						j = 0;
					}
					
					lastX = x + 1;
					j++;
				}
			}
			yOffset++;
			lastX = 0;
		}

		return images;
	}

	private BufferedImage flip(BufferedImage bi) {
		BufferedImage image = bi.getSubimage(0, 0, bi.getWidth(), bi.getHeight());
		
		for(int x = image.getWidth()-1; x >= 0; x--) {
			for(int y = 0; y < image.getHeight(); y++) {
				image.setRGB(x, y, bi.getRGB(x, y));
			}
		}
		
		return null;
	}

	private boolean check(BufferedImage image, int x) {
		for(int y = 0; y < 20; y++) {
			int pixel = image.getRGB(x, y);
			int p = pixel >> 24;
			if(p != 0x00) {
				return true;
			}
		}
		
		return false;
	}

	public static class ImageSize {
		private int x, y, w, h;

		public ImageSize(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public int getW() {
			return w;
		}

		public int getH() {
			return h;
		}
	}

}
