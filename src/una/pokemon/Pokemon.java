package una.pokemon;

import java.awt.image.BufferedImage;

import una.toolbox.PokeTools;
import una.toolbox.Tools;

public class Pokemon {

	private final int id;

	// hp,atk,def,spa,spd,spe
	private int[] baseStats;

	private String trueName, nickname;
	private int level;
	private boolean shiny = false;
	
	//0:back,1:front,2:backShiny,3:frontShiny
	private BufferedImage[] images;
	
	private int currentHealth = 50, maxHealth = 100;
	
	public Pokemon(int id, int level) {
		this(id, level, null, PokeTools.isShiny());
	}

	public Pokemon(int id, int level, String nickname, boolean shiny) {
		this.id = id;
		this.level = level;
		this.nickname = nickname;
		this.shiny = shiny;

		trueName = Tools.loadTrueName(id);
		baseStats = Tools.loadStats(id);
		images = Tools.loadSprites(id);
	}
	
	public BufferedImage getFront() {
		return images[shiny ? 3 : 1];
	}
	
	public BufferedImage getBack() {
		return images[shiny ? 2 : 0];
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getID() {
		return id;
	}

	public int[] getStats() {
		return baseStats;
	}

	public void setNickname(String nick) {
		this.nickname = nick;
	}

	public String getNickname() {
		return nickname;
	}

	public String getTrueName() {
		return trueName;
	}

	public boolean isShiny() {
		return shiny;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

}
