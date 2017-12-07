package una.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import una.engine.PokeLoop;
import una.toolbox.Position;
import una.toolbox.Sprites;
import una.world.Screen;

public class NPC extends Entity {

	private Position position;

	private final int NPC_SPRITE_ID;
	private int npcOffset;

	public NPC(PokeLoop loop, Screen screen, int spriteID, Position position) {
		super(loop, screen);
		this.NPC_SPRITE_ID = spriteID;
		this.position = position;
	}

	@Override
	public void render(Graphics g) {
		int x = position.getX() * 32 + screen.xOffset;
		int y = position.getY() * 32 + screen.yOffset;

		BufferedImage sprite = Sprites.NPCS[NPC_SPRITE_ID + npcOffset];
		if(sprite != null) {
			g.drawImage(sprite, x + 2, y - 11, sprite.getWidth() * 2, sprite.getHeight() * 2, null);
		}

	}

}
