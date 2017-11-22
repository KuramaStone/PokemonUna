package una.tiles;

import java.awt.image.BufferedImage;

import una.toolbox.Position;
import una.toolbox.Tiles;

public class Tile {
	
	public static int NORMAL_TILE_SIZE = 16;
	
	private Position pos;
	private int tileID;
	
	public Tile(Position pos, int tileID) {
		this.pos = pos;
		this.tileID = tileID;
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
		return Tiles.getImage(tileID);
	}

}
