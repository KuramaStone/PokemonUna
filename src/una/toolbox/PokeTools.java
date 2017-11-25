package una.toolbox;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import una.pokemon.Encounter;
import una.pokemon.Pokemon;

public class PokeTools {
	
	private static Random rnd = new Random();
	
	public static int getLevel(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static Pokemon createPokemon(Encounter encounter) {
		int level = getLevel(encounter.getMinLvl(), encounter.getMaxLvl());
		return new Pokemon(encounter.getPokemonID(), level);
	}

	public static boolean isShiny() {
		return rnd.nextInt(8192) == 0;
	}

}
