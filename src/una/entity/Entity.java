package una.entity;

import java.awt.Graphics;
import java.util.Random;

import una.engine.PokeLoop;
import una.world.Screen;

public abstract class Entity {

	protected final PokeLoop loop;
	protected final Screen screen;

	protected final Random rnd;

	public Entity(PokeLoop loop, Screen screen) {
		this.loop = loop;
		this.screen = screen;
		rnd = new Random();
	}

	public void tick() {
	}

	public void render(Graphics g) {
	}

}
