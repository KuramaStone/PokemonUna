package una.entity;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import una.engine.PokeLoop;
import una.input.InputHandler;
import una.pokemon.Pokemon;
import una.tiles.Tile;
import una.toolbox.Sprites;
import una.world.PokeArea;
import una.world.Screen;

public class Player extends Entity {

	private BufferedImage[][] animation;
	private int direction = 0, mode = 1;

	private InputHandler input;

	public Player(PokeLoop loop, Screen screen) {
		super(loop, screen);
		animation = Sprites.playerWalk;
		input = loop.getInput();
	}

	public void tick() {
		move();
		checkArea();
	}

	private void checkArea() {
		int centerX = PokeLoop.WIDTH / 2 - 16;
		int centerY = PokeLoop.HEIGHT / 2 - 20;
		
		for(PokeArea spec : screen.specAreas) {
			if(spec != null) {
				int px = screen.xOffset + spec.getMapX() * 32;
				int py = screen.yOffset + spec.getMapY() * 32;
				
				if(new Rectangle(px, py, spec.getWidth()*32, spec.getHeight()*32)
						.intersects(centerX, centerY, 1, 1)) {
					screen.setArea(spec.getAreaID());
				}
			}
		}

	}

	private void move() {
		movement();
		checkMove();
	}
	
	private Pokemon pokemon;

	private void checkMove() {
		PokeArea area = screen.currentArea;
		if(area == null)
			return;
		
		int x = -(screen.xOffset / 32 + area.getMapX() - 7);
		int y = -(screen.yOffset / 32 + area.getMapY() - 6);
		
		Tile tile = screen.currentArea.getTilemap().get(new Point(x, y));
		if(tile != null) {
			if(tile.isGrass()) {
				ArrayList<Integer> encounters = area.getEncounters();
				encounters.add(6);
				int i = rnd.nextInt(encounters.size());
				int pokeID = encounters.get(i);
				encounters.remove(i);
				Pokemon pokemon = new Pokemon(pokeID);
				this.pokemon = pokemon;
			}
		}
		else {
			System.out.println(x + " " + y);
		}
	}

	private void movement() {
		if(input.isKeyDown(KeyEvent.VK_W)) {
			direction = 1;
			screen.addYOffset(32);
		}
		else if(input.isKeyDown(KeyEvent.VK_S)) {
			direction = 0;
			screen.addYOffset(-32);
		}
		else if(input.isKeyDown(KeyEvent.VK_A)) {
			direction = 2;
			screen.addXOffset(32);
		}
		else if(input.isKeyDown(KeyEvent.VK_D)) {
			direction = 3;
			screen.addXOffset(-32);
		}
	}

	public void render(Graphics g) {
		if(pokemon != null) {
			g.drawImage(pokemon.getFront(), 0, 0, null);
		}
		g.drawImage(animation[mode][direction], PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 20, 32, 40, null);
	}

}
