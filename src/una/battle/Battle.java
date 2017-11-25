package una.battle;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import una.engine.PokeLoop;
import una.pokemon.Pokemon;
import una.toolbox.Tools;

public class Battle {

	private BufferedImage background;

	private Pokemon pokeF, pokeE;

	public Battle(int background, Pokemon pokeF, Pokemon pokeE) {
		this.background = Tools.loadBackground(background);
		this.pokeF = pokeF;
		this.pokeE = pokeE;
	}

	public void render(Graphics g) {
		g.drawImage(background, 0, 0, PokeLoop.WIDTH, 336, null);
		g.drawImage(pokeE.getFront(), 275, 75, 150, 150, null);
		g.drawImage(pokeF.getBack(), 25, 175, 200, 200, null);
	}

	public BufferedImage getBackground() {
		return background;
	}

	public Pokemon getPokeF() {
		return pokeF;
	}

	public Pokemon getPokeE() {
		return pokeE;
	}

}
