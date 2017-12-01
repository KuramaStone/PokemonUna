package una.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import una.engine.PokeLoop;
import una.pokemon.Encounter;
import una.pokemon.Pokemon;
import una.tiles.Tile;
import una.toolbox.InputHandler;
import una.toolbox.PokeTools;
import una.toolbox.Position;
import una.toolbox.Sprites;
import una.world.PokeArea;
import una.world.Screen;

public class Player extends Entity {

	private PokeArea area;

	private BufferedImage[][] animation;
	private int direction = 0, mode = 1;

	private InputHandler input;

	private Pokemon pokemon;

	// Animation movement
	private static int ticks;
	private boolean isRunning;

	private boolean left;
	private int movement, moveDelay;

	private GrassAnimation[] grass = new GrassAnimation[6];

	public Player(PokeLoop loop, Screen screen) {
		super(loop, screen);
		animation = Sprites.playerWalk;
		input = loop.getInput();
	}

	public void tick() {
		area = screen.currentArea;
		move();
		checkArea();
		mode %= 3;
	}

	private void checkArea() {
		int centerX = PokeLoop.WIDTH / 2;
		int centerY = PokeLoop.HEIGHT / 2;

		for(PokeArea spec : screen.specAreas) {
			if(spec != null) {
				int px = screen.xOffset + spec.getMapX() * 32;
				int py = screen.yOffset + (spec.getMapY() + 1) * 32;

				// For showing the borders
				//
				// g.setColor(Color.PINK);
				// g.fillRect(px, py, spec.getWidth() * 32, (spec.getHeight()-1) * 32);
				// g.setColor(Color.BLUE);
				// g.fillRect(centerX - 1, centerY - 1, 1, 1);

				if(new Rectangle(px, py, spec.getWidth() * 32, (spec.getHeight() - 1) * 32)
						.intersects(centerX - 1, centerY - 1, 1, 1)) {
					screen.setArea(spec.getAreaID());
				}
			}
		}

	}

	private boolean doneMoving;

	private void move() {
		if(moveDelay > 0) {
			moveDelay--;
			return;
		}

		if(movement > 0) {
			moveScreen(isRunning ? 8 : 4);
			movement--;

			if(movement == 0) {
				doneMoving = true;
			}
		}
		else {
			if(doneMoving) {

				doneMoving = false;
				checkPokemon();

			}
			checkRunning();
			mode = 1;
			movement();
		}

		ticks++;
		ticks %= 30;
	}

	public void moveScreen(int i) {
		if(direction == 0) {
			screen.addYOffset(-i);
		}
		else if(direction == 1) {
			screen.addYOffset(i);
		}
		else if(direction == 2) {
			screen.addXOffset(i);
		}
		else if(direction == 3) {
			screen.addXOffset(-i);
		}

		if(movement == (isRunning ? 2 : 5)) {
			if(left) {
				mode = 0;
			}
			else {
				mode = 2;
			}
			left = !left;
		}
	}

	private boolean canPlayerMove() {
		Position pos = adjacentTile(direction);

		return area.canMove(pos.getX(), pos.getY());
	}

	private Position adjacentTile(int i) {
		int x = getMapX();
		int y = getMapY() - 1;

		if(i == 0)
			y++;
		else if(i == 1)
			y--;
		else if(i == 2)
			x--;
		else if(i == 3)
			x++;

		return new Position(x, y);
	}

	private void movement() {
		int d;
		// down
		if(input.isKeyDown(KeyEvent.VK_S)) {
			d = 0;
		}
		// up
		else if(input.isKeyDown(KeyEvent.VK_W)) {
			d = 1;
		}
		// left
		else if(input.isKeyDown(KeyEvent.VK_A)) {
			d = 2;
		}
		// right
		else if(input.isKeyDown(KeyEvent.VK_D)) {
			d = 3;
		}
		else {
			return;
		}

		if(d != direction) {
			direction = d;
			moveDelay = isRunning ? 0 : 2;
			doneMoving = true;
			return;
		}
		direction = d;

		if(!canPlayerMove()) {
			return;
		}
		movement = isRunning ? 4 : 8;
	}

	private void checkRunning() {
		if(input.isKeyDown(KeyEvent.VK_DOWN)) {
			isRunning = true;
			if(animation != Sprites.playerRun) {
				animation = Sprites.playerRun;
			}
		}
		else {
			isRunning = false;
			if(animation != Sprites.playerWalk) {
				animation = Sprites.playerWalk;
			}
		}
	}

	private boolean checkPokemon() {
		if(area == null)
			return false;

		Tile tile = area.getTile(getMapX(), getMapY());
		if((tile != null) && (tile.isGrass()) &&
				(rnd.nextInt(101) < area.getEncounterChance(1))) {
			ArrayList<Encounter> encounters = area.getEncounters();
			if(encounters.size() > 0) {
				int i = rnd.nextInt(encounters.size());
				Encounter encounter = encounters.get(i);
				encounters.remove(i);
				this.pokemon = PokeTools.createPokemon(encounter);
				return true;
			}
			else {
				// No more pokemon on this route
			}
		}

		return false;
	}

	public int getMapX() {
		return -(((int) screen.xOffset / 32) + area.getMapX() - 7);
	}

	public int getMapY() {
		return -(((int) screen.yOffset / 32) + area.getMapY() - 6) + 1;
	}

	public void render(Graphics g) {
		if(pokemon != null) {
			g.drawImage(pokemon.getFront(), 0, 0, null);
		}
		Tile tile = area.getTile(getMapX(), getMapY());
		renderPlayer(g);

		if(tile != null) {
			renderGrass(tile, g);
			renderPlayerHead(g);

			if(tile.isGrass() && movement == 0) {
				int x = tile.getPos().getX() * 32 + area.getMapX() * 32;
				int y = tile.getPos().getY() * 32 + area.getMapY() * 32;
				addGrassAni(x, y);
			}
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
					ga.playGrassAnimation(g, screen);
				}
			}
		}
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
		BufferedImage image = animation[mode][direction];
		g.drawImage(image.getSubimage(0, 0, image.getWidth(), 15), PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 25, 32, 30, null);
	}

	private void renderPlayer(Graphics g) {
		g.drawImage(animation[mode][direction], PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 25, 32, 40, null);
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

		public void playGrassAnimation(Graphics g, Screen screen) {
			if(ticks % 5 == 0 && grassTick < 3) {
				grassTick++;
			}

			g.drawImage(Sprites.tiles[4][grassTick], x + screen.xOffset, y + screen.yOffset, 32, 32, null);
		}
	}

}