package una.font;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextRenderer {
	
	private Map<Integer, Text> texts = new HashMap<>();
	
	public void addText(int id, int x, int y, String text) {
		texts.put(id, new Text(text, x, y, id));
	}
	
	public void removeText(int id) {
		texts.remove(id);
	}
	
	public void render(Graphics g) {
		for(Text text : texts.values()) {
			BufferedImage[] images = text.getImages();
			int x = text.getX();
			int y = text.getY();
			for(BufferedImage image : images) {
				if(image != null) {
					g.drawImage(image, x, y, image.getWidth()*2, image.getHeight()*2, null);
					x += image.getWidth()*2;
				}
				else {
					x += 7;
				}
			}
		}
	}

}
