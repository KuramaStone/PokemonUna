package una.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import una.engine.PokeLoop;
import una.tiles.Tile;
import una.toolbox.Position;
import una.toolbox.Sprites;
import una.world.PokeArea;
import una.world.Screen;

public class NPC extends Entity {

	private Position position;

	private final int NPC_SPRITE_ID;
	private int npcOffset;

	// Animation movement
	private boolean isRunning;
	private int direction = 0, mode = 1;

	private boolean leftAnimation;
	private int jumping, dust, movement, moveDelay;
	
	private PokeArea area;

	private GrassAnimation[] grass = new GrassAnimation[10];

	public NPC(PokeLoop loop, Screen screen, int spriteID, Position position, PokeArea area) {
		super(loop, screen);
		this.NPC_SPRITE_ID = spriteID;
		this.position = position;
		this.area = area;
	}

	@Override
	public void tick() {
		direction = 5;
		move();

		super.tick();
	}

	private void move() {
		if(moveDelay > 0) {
			moveDelay--;
			return;
		}

		if(jumping > 0) { // For jumping over cliffs
			moveScreen(4);
			if(jumping % 4 == 0) {
				mode = leftAnimation ? 0 : 2;
				leftAnimation = !leftAnimation;
			}
			jumping--;
		}
		else if(movement > 0) { // Automatically moving gradually
			moveScreen(isRunning ? 8 : 4);
			movement--;
		}
		else {
			mode = 1;
		}
	}

	public void moveScreen(int i) {
		if(direction == 0) {
			position.addY(i);
		}
		else if(direction == 1) {
			position.addY(-i);
		}
		else if(direction == 2) {
			position.addX(-i);
		}
		else if(direction == 3) {
			position.addX(i);
		}

		if(movement == (isRunning ? 2 : 5)) {
			mode = leftAnimation ? 0 : 2;
			leftAnimation = !leftAnimation;
		}
	}

	@Override
	public void render(Graphics g) {
		renderPlayer(g);
		
//		Tile tile = area.getTile(getMapX(), getMapY());
//		if(tile != null) {
//			renderGrass(tile, g);
//
//			if(tile.isGrass() && movement == 0) {
//				int x = tile.getPos().getX() * 32 + area.getMapX() * 32;
//				int y = tile.getPos().getY() * 32 + area.getMapY() * 32;
//				addGrassAni(x, y);
//			}
//		}
		
		renderPlayerHead(g);
	}

	private void addGrassAni(int x, int y) {
		for(int i = 0; i < grass.length; i++) {
			if(grass[i] == null) {
				grass[i] = new GrassAnimation(x, y);
				break;
			}
		}
	}
	
	private void renderPlayerHead(Graphics g) {
		int yOffset = (int) (jumping > 0 ? 24 * (1 - ((double) Math.abs(jumping - 8) / 8)) : 0);
		int x = position.getX() * 32 + screen.xOffset;
		int y = position.getY() * 32 + screen.yOffset + yOffset;
		BufferedImage image = Sprites.NPCS[NPC_SPRITE_ID + direction];
		if(image != null) {
			g.drawImage(image.getSubimage(0, 0, image.getWidth(), 15), x, y, 32, 30, null);
		}
	}

	private void renderGrass(Tile tile, Graphics g) {
		for(int i = 0; i < grass.length; i++) {
			GrassAnimation ga = grass[i];
			if(ga != null) {
				int x = tile.getPos().getX() * 32 + area.getMapX() * 32;
				int y = tile.getPos().getY() * 32 + area.getMapY() * 32;

				if(ga.grassTick == 3 && !ga.compare(x, y)) {
					grass[i] = null;
				}
				else {
					ga.playGrassAnimation(g, screen, ticks);
				}
			}
		}
	}
	
	private void renderPlayer(Graphics g) {
		int yOffset = (int) (jumping > 0 ? 24 * (1 - ((double) Math.abs(jumping - 8) / 8)) : 0);
		int x = position.getX() + screen.xOffset;
		int y = position.getY() + screen.yOffset + yOffset;

		BufferedImage sprite = Sprites.NPCS[NPC_SPRITE_ID + direction];
		System.out.println(NPC_SPRITE_ID + direction);
		if(sprite != null) {
			g.drawImage(sprite, x + 2, y - 11, sprite.getWidth() * 2, sprite.getHeight() * 2, null);
		}

		if(jumping >= 0) {
			if(jumping == 0) {
				if(dust == -1)
					dust = 12;
			}
			else {
				g.drawImage(Sprites.shadow, PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 16, 32, 40, null);
				dust = -1;
			}
		}

		if(dust > 0) {
			g.drawImage(Sprites.tiles[11][(12 - dust) / 4], x - 16, y - 8, 32, 32, null);
			dust--;
		}
	}

	private static class GrassAnimation {

		private int grassTick = 0;
		private int x, y;

		public GrassAnimation(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public boolean compare(int x, int y) {
			return this.x == x && this.y == y;
		}

		public void playGrassAnimation(Graphics g, Screen screen, int ticks) {
			if(ticks % 6 == 0 && grassTick < 3) {
				grassTick++;
			}
			
			int ax = x + screen.xOffset;
			int ay = y + screen.yOffset;

			BufferedImage image = (224 == ax && 224 == ay) ? Sprites.tiles[4][grassTick] : Sprites.tiles[12][Math.min(grassTick, 2)];
			g.drawImage(image, ax, ay, 32, 32, null);
		}
	}
	
	public int getMapX() {
		return -(((int) screen.xOffset / 32) + area.getMapX());
	}

	public int getMapY() {
		return -(((int) screen.yOffset / 32) + area.getMapY()) + 1;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}
	
	public boolean isRunning() {
		return isRunning;
	}
	
	public void setRunning(boolean running) {
		this.isRunning = running;
	}
	
	public void setMovement(int i) {
		this.movement = i;
	}

}
