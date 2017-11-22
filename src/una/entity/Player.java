package una.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import una.engine.PokeLoop;
import una.input.InputHandler;
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

	private int tick = 0;

	public void tick() {
		if(tick % 10 == 0) {
		}
		move();
	}

	private void checkArea(Graphics g) {
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
		tick++;
		movement();
		tick %= 30;
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
		checkArea(g);
		g.drawImage(animation[mode][direction], PokeLoop.WIDTH / 2 - 16, PokeLoop.HEIGHT / 2 - 20, 32, 40, null);
	}

}
