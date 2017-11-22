package una.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import una.tiles.Tile;
import una.toolbox.Tools;

public class PokeArea {
	
	private ArrayList<Integer> encounters = new ArrayList<>();
	
	private int areaID = -1;
	
	private AreaData data;
	
	public PokeArea(int areaID) {
		data = Tools.loadArea(Tools.getAreaSrc(areaID));
		this.areaID = areaID;
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
	
	public ArrayList<Integer> getEncounters() {
		return encounters;
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

}
