package una.toolbox;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import una.pokemon.Encounter;
import una.pokemon.Pokemon;

public class PokeTools {
	
	private static Random rnd = new Random();

	private static BufferedImage[] images;

	private static Map<Integer, BufferedImage[]> pokemonSprites = new HashMap<>();
	private static Map<Integer, int[]> pokemonStats = new HashMap<>();
	private static Map<Integer, String> pokemonNames = new HashMap<>();
	
	public static void init() {
		images = new BufferedImage[4];
		images[0] = Tools.getImage("res\\sprites\\pokemon_back_1.png");
		images[1] = Tools.getImage("res\\sprites\\pokemon_front_1.png");
		images[2] = Tools.getImage("res\\sprites\\shiny_pokemon_back_1.png");
		images[3] = Tools.getImage("res\\sprites\\shiny_pokemon_front_1.png");
	}

	public static BufferedImage[] loadSprites(int id) {
		if(pokemonSprites.containsKey(id))
			return pokemonSprites.get(id);
		
		id--;
		BufferedImage[] sprites = new BufferedImage[4];

		int x = id % 31;
		int y = id / 31;

		sprites[0] = images[0].getSubimage(x * 96, y * 96, 96, 96);
		sprites[1] = images[1].getSubimage(x * 80, y * 80, 80, 80);
		sprites[2] = images[2].getSubimage(x * 96, y * 96, 96, 96);
		sprites[3] = images[3].getSubimage(x * 80, y * 80, 80, 80);

		pokemonSprites.put(id + 1, sprites);

		return sprites;
	}

	public static int[] loadBaseStats(int id) {
		if(pokemonStats.containsKey(id))
			return pokemonStats.get(id);
		
		int[] stats = new int[6];
		int i = 0;
		try {
			FileReader fr = new FileReader("res\\data\\pokemon_stats.txt");
			BufferedReader br = new BufferedReader(fr);

			String line;
			while((line = br.readLine()) != null) {
				if(line.startsWith("##"))
					continue;
				String[] data = line.split(",");
				int dataID = Integer.parseInt(data[0]);

				if(id == dataID) {
					stats[i++] = Integer.parseInt(data[2]);
				}
			}
			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		pokemonStats.put(id, stats);
		
		return stats;
	}

	public static String loadTrueName(int id) {
		if(pokemonNames.containsKey(id))
			return pokemonNames.get(id);
		try {
			FileReader fr = new FileReader("res\\data\\pokemon_names.txt");
			@SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(fr);

			String line;
			while((line = br.readLine()) != null) {
				if(line.startsWith("##"))
					continue;
				String[] data = line.split(",");
				int dataID = Integer.parseInt(data[0]);

				if(id == dataID) {
					int lang = Integer.parseInt(data[1]);
					if(lang == 9) {
						return data[2];
					}
				}
			}

			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static int getLevel(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static Pokemon createPokemon(Encounter encounter) {
		int level = getLevel(encounter.getMinLvl(), encounter.getMaxLvl());
		return new Pokemon(encounter.getPokemonID(), level);
	}

	public static boolean isShiny() {
		return rnd.nextInt(8192) == 6;
	}

}
