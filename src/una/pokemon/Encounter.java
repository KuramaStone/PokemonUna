package una.pokemon;

public class Encounter {
	
	private int pokemonID;
	private int minLvl, maxLvl;
	
	private int areaType;

	public Encounter(int pokemonID, int minLvl, int maxLvl, int areaType) {
		this.pokemonID = pokemonID;
		this.minLvl = minLvl;
		this.maxLvl = maxLvl;
		this.areaType = areaType;
	}

	public int getPokemonID() {
		return pokemonID;
	}

	public int getMinLvl() {
		return minLvl;
	}

	public int getMaxLvl() {
		return maxLvl;
	}

	public int getAreaType() {
		return areaType;
	}

}
