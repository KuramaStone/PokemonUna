package una.entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import una.engine.PokeLoop;
import una.input.InputHandler;
import una.pokemon.Encounter;
import una.pokemon.Pokemon;
import una.tiles.Tile;
import una.toolbox.PokeTools;
import una.toolbox.Sprites;
import una.world.PokeArea;
import una.world.Screen;

public class Player extends Entity {

	private BufferedImage[][] animation;
	private int direction = 0, mode = 1;

	private InputHandler input;

	private Pokemon pokemon;

	// Animation movement
	private int ticks;
	private boolean running;

	public Player(PokeLoop loop, Screen screen) {
		super(loop, screen);
		animation = Sprites.playerWalk;
		input = loop.getInput();
	}

	public void tick() {
		move();
		checkArea();
		mode %= 3;
	}

	private void checkArea() {
		int centerX = PokeLoop.WIDTH / 2 - 16;
		int centerY = PokeLoop.HEIGHT / 2 - 20;

		for(PokeArea spec : screen.specAreas) {
			if(spec != null) {
				int px = screen.xOffset + spec.getMapX() * 32;
				int py = screen.yOffset + spec.getMapY() * 32;

				if(new Rectangle(px, py, spec.getWidth() * 32, spec.getHeight() * 32)
						.intersects(centerX, centerY, 1, 1)) {
					screen.setArea(spec.getAreaID());
				}
			}
		}

	}

	private void move() {
		if(movement > 0) {
			moveScreen(running ? 8 : 4);
			movement--;
		}
		else {
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
		
		if(movement == (running ? 2 : 5)) {
			if(left) {
				mode = 0;
			}
			else {
				mode = 2;
			}
			left = !left;
		}
	}
	
	private boolean left;
	private int movement;

	private void movement() {

		// down
		int i = running ? 8 : 4;
		if(input.isKeyDown(KeyEvent.VK_S)) {
			direction = 0;
			moveScreen(i);
		}
		// up
		else if(input.isKeyDown(KeyEvent.VK_W)) {
			direction = 1;
			moveScreen(i);
		}
		// left
		else if(input.isKeyDown(KeyEvent.VK_A)) {
			direction = 2;
			moveScreen(i);
		}
		// right
		else if(input.isKeyDown(KeyEvent.VK_D)) {
			direction = 3;
			moveScreen(i);
		}
		else {
			return;
		}

		movement = running ? 3 : 7;
		
		checkMove();
	}

	private void checkRunning() {
		if(input.isKeyDown(KeyEvent.VK_SPACE)) {
			running = true;
			if(animation != Sprites.playerRun) {
				animation = Sprites.playerRun;
			}
		}
		else {
			running = false;
			if(animation != Sprites.playerWalk) {
				animation = Sprites.playerWalk;
			}
		}
	}

	private void checkMove() {
		PokeArea area = screen.currentArea;
		if(area == null)
			return;

		int x = -(screen.xOffset / 32 + area.getMapX() - 7);
		int y = -(screen.yOffset / 32 + area.getMapY() - 6);

		Tile tile = screen.currentArea.getTilemap().get(new Point(x, y));
		if(tile != null) {
			if(tile.isGrass() && (rnd.nextInt(100) + 1 < area.getEncounterChance(1))) {
				ArrayList<Encounter> encounters = area.getEncounters();
				if(encounters.size() > 0) {
					int i = rnd.nextInt(encounters.size());
					Encounter encounter = encounters.get(i);
					encounters.remove(i);
					this.pokemon = PokeTools.createPokemon(encounter);
				}
				else {
					// No more pokemon on this route
				}
			}
		}
	}

	public void render(Graphics g) {
		if(pokemon != null) {
			g.drawImage(pokemon.getFront(), 0, 0, null);
		}
		g.drawImage(animation[mode][direction], PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 25, 32, 40, null);
	}

}
