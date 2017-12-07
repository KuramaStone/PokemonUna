package una.toolbox;

import java.awt.image.BufferedImage;

public class ImgSorter {

	public BufferedImage[] loadImages(String path) {
		BufferedImage[] images = new BufferedImage[605];
		BufferedImage image = Tools.getImage(path);

		int count = 0;
		int yOffset = 0;

		int lastX = 0;
		while(yOffset != 11) {
			for(int x = 0; x < image.getWidth(); x++) {
				boolean foundPixel = check(image, x);

				if(!foundPixel) {

					if(!check(image, x+1))
						break;
					
					System.out.println(count);
					images[count++] = image.getSubimage(lastX, yOffset * 23, x - lastX, 20);
					lastX = x + 1;
				}
			}
			yOffset++;
			lastX = 0;
		}

		return images;
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
