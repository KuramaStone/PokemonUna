package una.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import una.pokemon.Encounter;
import una.tiles.Tile;
import una.toolbox.Tools;

public class PokeArea {

	private ArrayList<Encounter> encounters = new ArrayList<>();

	private int areaID = -1;

	private AreaData data;

	private Map<Integer, Integer> overlay;

	public PokeArea(int areaID) {
		this.areaID = areaID;

		data = Tools.loadArea(Tools.getAreaSrc(areaID));
		encounters = Tools.loadEncounters(areaID);
		overlay = Tools.loadOverlay(areaID);
	}

	public boolean canMove(int x, int y) {
		int i = x + y * data.getWidth();
		if(!overlay.containsKey(i)) {
			return true;
		}

		return overlay.get(i) == (-1 & 9);
	}

	public Map<Point, Tile> getTilemap() {
		return data.getTileMap();
	}

	public int getAreaID() {
		return areaID;
	}

	public int getWidth() {
		return data.getWidth();
	}

	public int getHeight() {
		return data.getHeight();
	}

	public int getMapX() {
		return data.getMapOffsetX();
	}

	public int getMapY() {
		return data.getMapOffsetY();
	}

	public ArrayList<Encounter> getEncounters() {
		return encounters;
	}

	public Map<Integer, Integer> getOverlay() {
		return overlay;
	}

	public static class AreaData {
		private int width, height;
		private Map<Point, Tile> tilemap = new HashMap<>();

		private String name;

		private int mapX, mapY;

		public AreaData(String name, int mapX, int mapY, int width, int height, Map<Point, Tile> tilemap) {
			this.name = name;
			this.mapX = mapX;
			this.mapY = mapY;
			this.width = width;
			this.height = height;
			this.tilemap = tilemap;
		}

		public int getMapOffsetX() {
			return mapX;
		}

		public int getMapOffsetY() {
			return mapY;
		}

		public String getName() {
			return name;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public Map<Point, Tile> getTileMap() {
			return tilemap;
		}
	}

	public int getEncounterChance(int i) {
		return 33;
	}

	public Tile getTile(int x, int y) {
		return data.tilemap.get(new Point(x, y));
	}

}
