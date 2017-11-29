package una.tiles;

import java.awt.image.BufferedImage;

import una.toolbox.Position;
import una.toolbox.Sprites;

public class Tile {
	
	public static int NORMAL_TILE_SIZE = 16;
	
	private Position pos;
	private int tileID;
	
	private boolean animated = false;
	private BufferedImage[] tiles;
	
	private int ani = 0, tick = 0;
	
	public Tile(Position pos, int tileID) {
		this.pos = pos;
		this.tileID = tileID;
		
		if(tileID == 885) {
			tiles = Sprites.tiles[2];
		}
		else if(tileID == 3540) {
			tiles = Sprites.tiles[0];
		}
		else if(tileID == 2471) {
			tiles = Sprites.tiles[6];
		}
		else if(tileID == 3599) {
			tiles = Sprites.tiles[7];
		}
		else if(tileID == 3481) {
			tiles = Sprites.tiles[8];
		}
		else if(tileID == 2412) {
			tiles = Sprites.tiles[10];
		}
		else if(tileID == 3304) {
			tiles = Sprites.tiles[9];
		}
		else {
			return;
		}
		
		animated = true;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public int getTileID() {
		return tileID;
	}

	public BufferedImage getImage() {
		BufferedImage image = animated ? tiles[ani] : Tiles.getImage(tileID);
		
		if(animated && tick % (isWater() ? 10 : 5) == 0) {
			ani++;
			ani %= tiles.length;
		}

		tick++;
		tick %= 30;
		
		return image;
	}
	
	public boolean isGrass() {
		return tileID == 1947;
	}
	
	public boolean isWater() {
		return tileID == (2471 & 3599 & 3481 & 2412 & 3304);
	}

}
