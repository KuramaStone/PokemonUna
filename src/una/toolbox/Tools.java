package una.toolbox;

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
import java.util.Random;

import javax.imageio.ImageIO;

import una.tiles.Tile;
import una.world.PokeArea;
import una.world.PokeArea.AreaData;

public class Tools {
	
	private static Random rnd = new Random();

	public static String getAreaSrc(int areaID) {
		FileReader fileReader;
		try {
			fileReader = new FileReader("res\\maps\\AreaIDs.txt");
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			String line = "";

			while ((line = bufferedReader.readLine()) != null) {
				int id = Integer.parseInt(line.split(":")[0]);
				if (id == areaID) {
					return line.split(":")[1];
				}
			}

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static BufferedImage getImage(String path) {
		try {
			return ImageIO.read(new File(path));
		} catch (Exception e) {
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
			while ((line = bufferedReader.readLine()) != null) {
				lineX = 0;

				if (lineY == 0) {
					String[] s = line.split("/");
					mapX = Integer.valueOf(s[0]);
					mapY = Integer.valueOf(s[1]);
				} else {
					for (String s : line.split("/")) {
						Integer i = Integer.valueOf(s);
						tilemap.put(new Point(lineX, lineY), new Tile(new Position(lineX, lineY), i));
						lineX++;
					}
				}

				lineY++;
			}

			width = lineX - 1;
			height = lineY - 2;

			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
			e.printStackTrace();
		} catch (IOException e) {
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
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("##")) {
					Integer id = Integer.parseInt(line.split(",")[0]);
					if (id == i) {
						for (int j = 1; j < 5; j++) {
							Integer k = Integer.parseInt(line.split(",")[j]);
							if (k != -1) {
								conn[c] = new PokeArea(Integer.parseInt(line.split(",")[j]));
								c++;
							}
						}
					}
				}
			}

			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
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
			while ((line = br.readLine()) != null) {
				if (line.startsWith("##"))
					continue;
				String[] data = line.split(",");
				int dataID = Integer.parseInt(data[0]);

				if (id == dataID) {
					stats[i++] = Integer.parseInt(data[2]);
				}
			}
			br.close();
		} catch (Exception e) {
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
			while ((line = br.readLine()) != null) {
				if (line.startsWith("##"))
					continue;
				String[] data = line.split(",");
				int dataID = Integer.parseInt(data[0]);

				if (id == dataID) {
					int lang = Integer.parseInt(data[1]);
					if (lang == 9) {
						return data[2];
					}
				}
			}

			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private static BufferedImage[] images = {
			getImage("res\\sprites\\pokemon_back_1.png"),
			getImage("res\\sprites\\pokemon_front_1.png"),
			getImage("res\\sprites\\shiny_pokemon_back_1.png"),
			getImage("res\\sprites\\shiny_pokemon_front_1.png")
	};

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

	public static boolean isShiny() {
		return rnd.nextInt(8192) == 0;
	}

	public static ArrayList<Integer> loadEncounters(int areaID) {
		return null;
	}

}
