package una.pokemon;

public class PokeStats {
	
	public static final int
	HEALTH = 0,
	ATTACK = 1,
	DEFENSE = 2,
	SP_ATTACK = 3,
	SP_DEFENSE = 4,
	SPEED = 5;
	
	private int[] IVS, EVS, base;

	public PokeStats(int[] ivs, int[] evs, int[] base) {
		IVS = ivs;
		EVS = evs;
		this.base = base;
	}
	
	public PokeStats(int[] base) {
		this(new int[6], new int[6], base);
	}
	
	/**
	 * 
	 * @param stat Stat to add amount to | HP:0, ATK:1, DEF:2, SPA:3, SPD:4, SPE:5
	 * @param amount Amount to add to stat
	 * @param legal Check if amount is legal. True if yes, false if no.
	 * 
	 */
	public void addEVs(int stat, int amount, boolean legal) {
		int evs = EVS[stat];
		
		int sum = 0;
		for(int i = 0; i < EVS.length; i++)
			sum += EVS[i];
		
		if(sum <= 510 || !legal) {
			EVS[stat] = legal ? Math.min(evs + amount, 255) : evs + amount;
		}
	}

	public int getIV(int stat) {
		return IVS[stat];
	}
	
	public int getEV(int stat) {
		return EVS[stat];
	}
	
	public int getBase(int stat) {
		return base[stat];
	}

	public int[] getIVS() {
		return IVS;
	}

	public void setIVS(int[] ivs) {
		IVS = ivs;
	}

	public int[] getEVS() {
		return EVS;
	}

	public void setEVS(int[] evs) {
		EVS = evs;
	}

	public int[] getBase() {
		return base;
	}

	public void setBase(int[] base) {
		this.base = base;
	}

}
