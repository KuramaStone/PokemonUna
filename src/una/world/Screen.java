package una.world;

import java.awt.Color;
import java.awt.Graphics;

import una.engine.PokeLoop;
import una.engine.TileRenderer;
import una.entity.Player;
import una.pokemon.Pokemon;
import una.toolbox.Tools;

public class Screen {

	public int xOffset = 0, yOffset = 0;

	// Renderers
	private TileRenderer tileR;

	// Current Area
	public PokeArea currentArea;
	// SpectatorArea
	public PokeArea[] specAreas = new PokeArea[4];

	// Player
	private Player player;

	public Screen(PokeLoop loop) {
		player = new Player(loop, this);
		tileR = new TileRenderer(this);
		setArea(62);
	}

	public void setArea(int id) {
		if((currentArea == null) || (id != currentArea.getAreaID())) {
			currentArea = new PokeArea(id);
			specAreas = Tools.loadConnections(id);
		}
	}
	Pokemon poke = new Pokemon(7, null);

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, PokeLoop.WIDTH, PokeLoop.HEIGHT);
		for(PokeArea spec : specAreas) {
			if(spec != null) {
				tileR.render(g, spec, spec.getMapX(), spec.getMapY());
			}
		}
		tileR.render(g, currentArea, currentArea.getMapX(), currentArea.getMapY());

		player.render(g);
		g.drawImage(poke.getBack(), 0, 0, null);
	}

	public void tick() {
		player.tick();
	}

	public void addXOffset(int offset) {
		// if((xOffset + offset <= 0) &&
		// ((xOffset + offset - PokeLoop.WIDTH) >= (-currentArea.getWidth() * 32))) {
		// }
		xOffset += offset;
	}

	public void addYOffset(int offset) {
		// if((yOffset + offset <= 0) &&
		// ((yOffset + offset - PokeLoop.HEIGHT) >= (-currentArea.getHeight() * 32))) {
		// }
		yOffset += offset;
	}

}
