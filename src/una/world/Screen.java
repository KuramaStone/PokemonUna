package una.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

import una.battle.Battle;
import una.engine.PokeLoop;
import una.engine.TileRenderer;
import una.entity.NPC;
import una.entity.Player;
import una.font.TextRenderer;
import una.toolbox.InputHandler;
import una.toolbox.Position;
import una.toolbox.Tools;

public class Screen {

	public int xOffset = 0, yOffset = 0;
	
	private PokeLoop loop;

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
	
	private InputHandler input;

	public Screen(PokeLoop loop) {
		this.loop = loop;
		this.input = loop.getInput();
		player = new Player(loop, this);
		tileR = new TileRenderer(this);
		textR = new TextRenderer();
//		textR.addText(0, 10, 10, "Hello World!?/.,&*");

		try {
			Tools.loadGame(0, player, this);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("No save data found...");
			setArea(62);
		}
	}

	public void setArea(int id) {
		if((currentArea == null) || (id != currentArea.getAreaID())) {
			currentArea = new PokeArea(id);
			specAreas = Tools.loadConnections(id);
		}
	}
	
	private NPC npc;

	public void render(Graphics g) {
		if(battle == null) {
			for(PokeArea spec : specAreas) {
				if(spec != null) {
					tileR.render(g, spec, spec.getMapX(), spec.getMapY());
				}
			}
			tileR.render(g, currentArea, currentArea.getMapX(), currentArea.getMapY());

			player.render(g);
		}
		else {
			battle.render(g);
		}

		textR.render(g);
		
		if(ssDelay > 0) {
			g.setColor(new Color(0, 0, 0, (int) (128 * (ssDelay / 10d))));
			g.fillRect(0, 0, PokeLoop.WIDTH, PokeLoop.HEIGHT);
		}
		npc.render(g);
	}
	
//	private void drawGrid(Graphics g) {
//		g.setColor(Color.RED);
//		for(int x = 0; x <= currentArea.getWidth(); x++) {
//			int lx = x * 32 + xOffset;
//			int ly = yOffset;
//			g.drawLine(lx, ly, lx, ly + ((currentArea.getHeight()) * 32));
//		}
//		for(int y = 0; y <= currentArea.getHeight(); y++) {
//			int lx = xOffset;
//			int ly = y * 32 + yOffset;
//			g.drawLine(lx, ly, lx + ((currentArea.getWidth()) * 32), ly);
//		}
//	}
	
	private int ssDelay;

	public void tick() {
		if(npc == null) {
			npc = new NPC(loop, this, 8, new Position(8*32, 8*32), currentArea);
			npc.setMovement(32);
		}
		
		if(battle == null) {
			player.tick();
			npc.tick();
		}
		
		if(input.isKeyDown(KeyEvent.VK_SPACE)) {
			loop.setSpeed(0.25D);
		}
		else {
			loop.setSpeed(1.0D);
		}
		
		if(ssDelay == 0 && input.isKeyDown(KeyEvent.VK_P)) {
			loop.screenshot();
			input.setKey(KeyEvent.VK_P, false);
			
			ssDelay = 10;
		}
		
		if(ssDelay > 0) {
			ssDelay--;
		}
	}

	public void addXOffset(int offset) {
		xOffset += offset;
	}

	public void addYOffset(int offset) {
		yOffset += offset;
	}

	public void saveGame() {
		try {
			Tools.saveGame(0, player, this);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

}
