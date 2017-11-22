package una.engine;

import java.awt.Graphics;

import una.tiles.Tile;
import una.toolbox.Position;
import una.world.PokeArea;
import una.world.Screen;

public class TileRenderer {

	private Screen screen;

	public TileRenderer(Screen screen) {
		this.screen = screen;
	}

	public void render(Graphics g, PokeArea area, int mapX, int pokeY) {
		for(Tile tile : area.getTilemap().values()) {
			Position pos = tile.getPos();

			int px = pos.getX() * 32 + screen.xOffset;
			px += mapX * 32;
			int py = pos.getY() * 32 + screen.yOffset;
			py += pokeY * 32;
			
			g.drawImage(tile.getImage(), px, py, 32, 32, null);
		}
	}

}
