package una.world;

import java.awt.Graphics;
import java.util.Map.Entry;

import una.battle.Battle;
import una.engine.PokeLoop;
import una.engine.TileRenderer;
import una.entity.Player;
import una.font.TextRenderer;
import una.toolbox.Tools;

public class Screen {

	public int xOffset = 0, yOffset = 0;

	// Renderers
	private TileRenderer tileR;
	private TextRenderer textR;

	// Current Area
	public PokeArea currentArea;
	// SpectatorArea
	public PokeArea[] specAreas = new PokeArea[4];

	// Player
	private Player player;

	// Current battle
	private Battle battle;

	public Screen(PokeLoop loop) {
		player = new Player(loop, this);
		tileR = new TileRenderer(this);
		textR = new TextRenderer();
		textR.addText(0, 10, 10, "Hello World");

		setArea(62);
	}

	public void setArea(int id) {
		if((currentArea == null) || (id != currentArea.getAreaID())) {
			currentArea = new PokeArea(id);
			specAreas = Tools.loadConnections(id);
		}
	}

	public void render(Graphics g) {
		if(battle == null) {
			for(PokeArea spec : specAreas) {
				if(spec != null) {
					tileR.render(g, spec, spec.getMapX(), spec.getMapY());
				}
			}
			tileR.render(g, currentArea, currentArea.getMapX(), currentArea.getMapY());

//			drawOverlay(g);

			player.render(g);
		}
		else {
			battle.render(g);
		}

		textR.render(g);
	}

	public void drawOverlay(Graphics g) {
		for(Entry<Integer, Integer> set : currentArea.getOverlay().entrySet()) {
			if(set.getKey() != null && set.getKey() != -1) {
				Integer i = set.getKey();
				int x = i % (currentArea.getWidth());
				int y = i / (currentArea.getWidth());
				int px = x * 32 + xOffset + (currentArea.getMapX()) * 32;
				int py = y * 32 + yOffset + (currentArea.getMapY()+1) * 32;

				g.setColor(Tools.getColor(set.getValue()));
				g.fillRect(px, py, 32, 32);
			}
		}
	}

	public void tick() {
		if(battle == null) {
			player.tick();
		}
	}

	public void addXOffset(int offset) {
		xOffset += offset;
	}

	public void addYOffset(int offset) {
		yOffset += offset;
	}

}
