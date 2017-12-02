package una.pokemon;

import java.awt.image.BufferedImage;

import una.toolbox.PokeTools;
import una.toolbox.Tools;

public class Pokemon {

	private final int id;

	private String trueName, nickname;
	private int level;
	private boolean shiny = false;
	private PokeStats pokeStats;
	
	//0:back,1:front,2:backShiny,3:frontShiny
	private BufferedImage[] images;
	
	private int currentHealth = 50, maxHealth = 100;
	
	public Pokemon(int id, int level) {
		this(id, level, null, PokeTools.isShiny(), new PokeStats(PokeTools.loadBaseStats(id)));
	}

	public Pokemon(int id, int level, String nickname, boolean shiny, PokeStats pokeStats) {
		this.id = id;
		this.level = level;
		this.nickname = nickname;
		this.shiny = shiny;
		this.pokeStats = pokeStats;

		trueName = PokeTools.loadTrueName(id);
		images = PokeTools.loadSprites(id);
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

	public PokeStats getPokeStats() {
		return pokeStats;
	}
	
	@Override
	public String toString() {
		String string = "";
		string += id; // 0
		string += " " + level; // 1
		string += " " + (shiny ? 1 : 0); // 2
		for(int i = 0; i < 6; i++) {
			string += " " + pokeStats.getEV(i); // 3 4 5
			string += " " + pokeStats.getIV(i); // 6 7 8
		}
		string += " " + currentHealth; // 9
		string += " " + nickname == "" ? nickname : trueName; // 10
		return string;
	}
	
	public static Pokemon fromString(String string) {
		String[] str = string.split(" ");

		int[] ivs = new int[6];
		int[] evs = new int[6];
		
		for(int i = 0; i < 6; i++) {
			evs[i] = Tools.parse(str[i+3]);
			ivs[i] = Tools.parse(str[i+6]);
		}
		
		PokeStats stats = new PokeStats(ivs, evs, PokeTools.loadBaseStats(parse(str[0])));
		
		Pokemon poke = new Pokemon(parse(str[0]), parse(str[1]), str[10], parse(str[2]) == 1, stats);
		
		return poke;
	}
	
	private static int parse(String string) {
		return Tools.parse(string);
	}

}
