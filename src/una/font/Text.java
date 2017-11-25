package una.font;

import java.awt.image.BufferedImage;

import una.toolbox.Tools;

public class Text {
	
	private BufferedImage[] images;
	
	private String text;
	
	private int id, x, y;

	public Text(String text, int x, int y, int id_) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.id = id_;
		
		images = new BufferedImage[text.length()];
		int i = 0;
		for(char c : text.toCharArray()) {
			if(c != ' ') {
				int id = Tools.getCharID(c);
				images[i++] = Tools.getCharacter(id);
			}
			else {
				images[i++] = null;
			}
		}
	}

	public String getCharacters() {
		return text;
	}

	public int getId() {
		return id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public BufferedImage[] getImages() {
		return images;
	}

}
