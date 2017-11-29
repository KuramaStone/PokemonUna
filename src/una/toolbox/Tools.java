package una.toolbox;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import una.pokemon.Encounter;
import una.tiles.Tile;
import una.world.PokeArea;
import una.world.PokeArea.AreaData;

public class Tools {

	// private static Random rnd = new Random();

	static {
		loadCharacters();
	}

	public static String getAreaSrc(int areaID) {
		FileReader fileReader;
		try {
			fileReader = new FileReader("res\\maps\\AreaIDs.txt");
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = "";

			while((line = bufferedReader.readLine()) != null) {
				int id = Integer.parseInt(line.split(":")[0]);
				if(id == areaID) {
					return line.split(":")[1];
				}
			}

			bufferedReader.close();
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static AreaData loadArea(String fileName) {
		Map<Point, Tile> tilemap = new HashMap<>();
		int width = 0, height = 0;
		int mapX = 0, mapY = 0;

		try {
			String line = null;

			FileReader fileReader = new FileReader(fileName + ".txt");

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int lineX = 0;
			int lineY = 0;
			while((line = bufferedReader.readLine()) != null) {
				lineX = 0;

				if(lineY == 0) {
					String[] s = line.split("/");
					mapX = Integer.valueOf(s[0]);
					mapY = Integer.valueOf(s[1]);
				}
				else {
					for(String s : line.split("/")) {
						Integer i = Integer.valueOf(s);
						tilemap.put(new Point(lineX, lineY), new Tile(new Position(lineX, lineY), i));
						lineX++;
					}
				}

				lineY++;
			}

			width = lineX;
			height = lineY;

			bufferedReader.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			e.printStackTrace();
		}

		return new AreaData(fileName, mapX, mapY, width, height, tilemap);
	}

	public static PokeArea[] loadConnections(int i) {
		PokeArea[] conn = new PokeArea[4];
		int c = 0;
		try {
			FileReader fr = new FileReader("res\\maps\\Connections.txt");
			BufferedReader br = new BufferedReader(fr);

			String line;
			while((line = br.readLine()) != null) {
				if(!line.startsWith("##")) {
					Integer id = Integer.parseInt(line.split(",")[0]);
					if(id == i) {
						for(int j = 1; j < 5; j++) {
							Integer k = Integer.parseInt(line.split(",")[j]);
							if(k != -1) {
								conn[c] = new PokeArea(Integer.parseInt(line.split(",")[j]));
								c++;
							}
						}
					}
				}
			}

			br.close();
		}
		catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static int[] loadStats(int id) {
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

		return stats;
	}

	public static String loadTrueName(int id) {
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

	private static BufferedImage[] images = { getImage("res\\sprites\\pokemon_back_1.png"),
			getImage("res\\sprites\\pokemon_front_1.png"), getImage("res\\sprites\\shiny_pokemon_back_1.png"),
			getImage("res\\sprites\\shiny_pokemon_front_1.png") };

	public static BufferedImage[] loadSprites(int id) {
		id--;
		BufferedImage[] sprites = new BufferedImage[4];

		int x = id % 31;
		int y = id / 31;

		sprites[0] = images[0].getSubimage(x * 96, y * 96, 96, 96);
		sprites[1] = images[1].getSubimage(x * 80, y * 80, 80, 80);
		sprites[2] = images[2].getSubimage(x * 96, y * 96, 96, 96);
		sprites[3] = images[3].getSubimage(x * 80, y * 80, 80, 80);

		return sprites;
	}

	public static ArrayList<Encounter> loadEncounters(int areaID) {
		ArrayList<Encounter> encounters = new ArrayList<>();
		try {
			FileReader fr = new FileReader("res\\data\\encounters.txt");
			BufferedReader br = new BufferedReader(fr);

			String line;
			while((line = br.readLine()) != null) {
				if(!line.startsWith("##")) {
					String data[] = line.split(",");
					int route = Integer.parseInt(data[0]);
					if(route == areaID) {
						int area = Integer.parseInt(data[1]);
						int pokemon = Integer.parseInt(data[2]);
						int minLvl = Integer.parseInt(data[3]);
						int maxLvl = Integer.parseInt(data[4]);
						int amount = Integer.parseInt(data[5]);

						for(int i = 0; i < amount; i++) {
							encounters.add(new Encounter(pokemon, minLvl, maxLvl, area));
						}
					}
				}
			}

			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return encounters;
	}

	private static BufferedImage battleBackgrounds = getImage("res\\sprites\\battle\\backgrounds.png");

	public static BufferedImage loadBackground(int background) {
		if(background > 9 || background < 0) {
			background = 1;
		}

		int x = (background * 240) % 720;
		int y = (background * 240) / 720;

		return battleBackgrounds.getSubimage(x, y, 240, 112);
	}

	private static Map<Integer, BufferedImage> characters;

	public static BufferedImage getCharacter(Integer i) {
		return characters.get(i);
	}

	private static void loadCharacters() {
		characters = new HashMap<>();
		BufferedImage image = getImage("res\\sprites\\characters.png");

		try {
			FileReader fr = new FileReader("res\\data\\character_map.txt");
			BufferedReader br = new BufferedReader(fr);

			String line;
			while((line = br.readLine()) != null) {
				if(!line.startsWith("##")) {
					String[] str = line.split(":");
					String[] data = str[1].split(",");
					int id = parse(str[0]);
					int x = parse(data[0]);
					int y = parse(data[1]);
					int w = parse(data[2]);
					int h = parse(data[3]);

					characters.put(id, image.getSubimage(x, y, w, h));
				}
			}

			br.close();

		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}

	public static int parse(String string) {
		if(!isNumeric(string)) {
			return -1;
		}
		return Integer.parseInt(string);
	}

	public static int getCharID(char c) {
		String s = c + "";

		int id = 0;
		if(isNumeric(s)) {

		}
		else if(isLetter(s)) {
			id = (int) s.toLowerCase().charAt(0) - 97;
			if(s.equals(s.toUpperCase())) { // If 's' is uppercase
				id += 26;
			}
		}
		else {
			switch(s) {
				case "." :
					id = 52; break;
				case "," :
					id = 53; break;
				case "!" :
					id = 54; break;
				case "?" :
					id = 55; break;
				case "/" :
					id = 56; break;
				case "~" :
					id = 57; break;
				case "`" :
					id = 58; break;
				case "&" :
					id = 59; break;
				case "*" :
					id = 60; break;
			}
		}

		return id;
	}

	public static Map<Integer, Integer> loadOverlay(int i) {
		Map<Integer, Integer> overlay = new HashMap<>();

		String loadfile = "res\\data\\mapdata\\" + i + ".txt";
		File file = new File(loadfile);
		if(!file.exists())
			return overlay;

		try {
			FileReader fr = new FileReader(loadfile);
			BufferedReader br = new BufferedReader(fr);

			String line;
			int count = 0;
			while((line = br.readLine()) != null) {
				for(String str : line.split("/")) {
					int id = Integer.parseInt(str);
					if(id != -1) {
						overlay.put(count, id);
					}
					count++;
				}
			}

			br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return overlay;
	}

	public static Color getColor(Integer value) {
		switch(value) {
			case 0 :
				return new Color(255, 255, 255, 128);
			case 1 :
				return new Color(255, 0, 0, 128);
			case 2 :
				return new Color(255, 255, 0, 128);
			case 3 :
				return new Color(255, 100, 100, 128);
			case 4 :
				return new Color(255, 79, 175, 128);
			case 5 :
				return new Color(130, 45, 24, 128);
			case 6 :
				return new Color(98, 112, 130, 128);
			case 7 :
				return new Color(8, 130, 41, 128);
			case 8 :
				return new Color(0, 0, 0, 128);
			case 9 :
				return new Color(0, 255, 0, 128);
		}
		return null;
	}

	private static boolean isLetter(String s) {
		return s != null && s.toLowerCase().matches("[a-z]");
	}

	private static boolean isNumeric(String s) {
		return s != null && s.matches("[-+]?\\d*\\.?\\d+");
	}

}
