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
				
				if(new Rectangle(px, py, spec.getWidth()*32, spec.getHeight()*32)
						.intersects(centerX, centerY, 1, 1)) {
					screen.setArea(spec.getAreaID());
				}
			}
		}

	}
	
	private int ticks;

	private void move() {
		if(ticks % 3 == 0) {
			movement();
		}
		ticks++;
		ticks %= 30;
	}
	
	private boolean[] ani = new boolean[4];
	private boolean left, useKeys;

	private void movement() {
		useKeys =  !ani[direction];
		
		if((input.isKeyDown(KeyEvent.VK_W) && useKeys) || ani[1]) {
			direction = 1;
			screen.addYOffset(16);
		}
		else if((input.isKeyDown(KeyEvent.VK_S) && useKeys) || ani[0]) {
			direction = 0;
			screen.addYOffset(-16);
		}
		else if((input.isKeyDown(KeyEvent.VK_A) && useKeys) || ani[2]) {
			direction = 2;
			screen.addXOffset(16);
		}
		else if((input.isKeyDown(KeyEvent.VK_D) && useKeys) || ani[3]) {
			direction = 3;
			screen.addXOffset(-16);
		}
		else {
			return;
		}

		if(ani[direction]) {
			mode = 1;
		}
		else {
			if(left) {
				mode = 2;
			}
			else {
				mode = 0;
			}
			left = !left;
		}
		
		ani[direction] = !ani[direction];
		System.out.println(mode);
		
		checkMove();
	}

	private void checkMove() {
		PokeArea area = screen.currentArea;
		if(area == null)
			return;
		
		int x = -(screen.xOffset / 32 + area.getMapX() - 7);
		int y = -(screen.yOffset / 32 + area.getMapY() - 6);
		
		Tile tile = screen.currentArea.getTilemap().get(new Point(x, y));
		if(tile != null) {
			if(tile.isGrass() && (rnd.nextInt(100)+1 < area.getEncounterChance(1))) {
				ArrayList<Encounter> encounters = area.getEncounters();
				if(encounters.size() > 0) {
					int i = rnd.nextInt(encounters.size());
					Encounter encounter = encounters.get(i);
					encounters.remove(i);
					this.pokemon = PokeTools.createPokemon(encounter);
					System.out.println(encounters.size());
				}
				else {
					//No more pokemon on this route
				}
			}
		}
	}

	public void render(Graphics g) {
		if(pokemon != null) {
			g.drawImage(pokemon.getFront(), 0, 0, null);
		}
		g.drawImage(animation[mode][direction], PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 20, 32, 40, null);
	}

}
