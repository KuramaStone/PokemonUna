package una.entity;

import una.engine.PokeLoop;
import una.world.Screen;

public abstract class Entity {
	
	protected final PokeLoop loop;
	protected final Screen screen;

	public Entity(PokeLoop loop, Screen screen) {
		this.loop = loop;
		this.screen = screen;
	}

}
