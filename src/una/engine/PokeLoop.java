package una.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import una.toolbox.ClipboardImage;
import una.toolbox.InputHandler;
import una.world.Screen;

public class PokeLoop {

	public static final int WIDTH = 480, HEIGHT = 480;

	private final JFrame frame;

	public static double interpolation;

	private static final int LIMITER = 33;

	private BufferedImage backBuffer;
	private Insets insets;

	private boolean running = false;

	private Screen screen;

	private InputHandler inputHandler;
	private ClipboardImage clipboard;
	private DecimalFormat df = new DecimalFormat("#00.00");

	public PokeLoop() {
		frame = new JFrame();
		init();
	}

	public void init() {
		frame.setTitle("Pokemon Una");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				screen.saveGame();
				running = false;
			}
		});
		frame.setVisible(true);
		backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		insets = frame.getInsets();
		inputHandler = new InputHandler(frame);

		screen = new Screen(this);
		clipboard = new ClipboardImage();
	}
	
	public int framesPerSecond;
	
	private double speed = 1.0D;

	private void run() {
		frame.setVisible(true);


		int ticks = 0;
		long last = 0, lastFPS = 0;
		while(running) {
			int delay = (int) (1000 / LIMITER * speed);
			long now = System.currentTimeMillis();

			if(last + delay <= now) {
				update();
				render();
				
				if(lastFPS + 1000 <= now) {
					framesPerSecond = ticks;
					lastFPS = now;
					ticks = 0;
				}
				ticks++;
				
				last = now;
			}
			
		}

		frame.setVisible(false);
		System.exit(0);
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double d) {
		this.speed = d;
	}

	private void render() {
		Graphics g = frame.getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.clearRect(0, 0, PokeLoop.WIDTH, PokeLoop.HEIGHT);

		screen.render(bbg);

		g.drawImage(backBuffer, insets.left, insets.top, frame);
	}
	
	private void update() {
		frame.setTitle("Pokemon Una | " + df.format((double) framesPerSecond / LIMITER * 100) + "%");
		screen.tick();
	}

	public void start() {
		running = true;
		run();
	}

	public void stop() {
		running = false;
	}

	public static void main(String[] args) {
		PokeLoop loop = new PokeLoop();
		loop.start();
	}

	public InputHandler getInput() {
		return inputHandler;
	}
	
	public int getFPS() {
		return framesPerSecond;
	}

	public void screenshot() {
		int i = 0;
		File file;
		while((file = new File("res\\screenshots\\" + i + ".png")).exists()) {
			i++;
		}

		try {
			file.mkdirs();
			ImageIO.write(backBuffer, "png", file);
			clipboard.copyToClipboard(backBuffer);
			System.out.println("Saved screenshot '" + file.getName() + "'");
		}
		catch(IOException e) {
			System.out.println("Could not save screenshot '" + file.getName() + "'");
			e.printStackTrace();
		}

	}

}
