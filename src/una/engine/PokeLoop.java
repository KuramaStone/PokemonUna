package una.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import una.toolbox.InputHandler;
import una.world.Screen;

public class PokeLoop {
	
	public static final int WIDTH = 480, HEIGHT = 480;
	
	private final JFrame frame;

	public static double interpolation;

	private static final int FPS = 30;

	private BufferedImage backBuffer;
	private Insets insets;

	private boolean running = false;

	private Screen screen;
	
	private InputHandler inputHandler;

	public PokeLoop() {
		frame = new JFrame();
		init();
	}

	public void init() {
		frame.setTitle("Pokemon Una");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		backBuffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		insets = frame.getInsets();
		inputHandler = new InputHandler(frame);

		screen = new Screen(this);
	}

	private void run() {
		frame.setVisible(true);
		int SKIP_TICKS = 1000 / FPS;
		
		while(running) {
			long time = System.currentTimeMillis();

			update();
			render();

			time = SKIP_TICKS - (System.currentTimeMillis() - time);

			if(time > 0) {
				try {
					Thread.sleep(time);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}

			interpolation = (time + SKIP_TICKS - (time += SKIP_TICKS) / SKIP_TICKS) / 100d;
		}

		frame.setVisible(false);
		System.exit(0);
	}

	private void render() {
		Graphics g = frame.getGraphics();

		Graphics bbg = backBuffer.getGraphics();

		bbg.setColor(Color.WHITE);
		bbg.setColor(Color.BLACK);
		bbg.fillRect(0, 0, PokeLoop.WIDTH, PokeLoop.HEIGHT);

		screen.render(bbg);

		g.drawImage(backBuffer, insets.left, insets.top, frame);
	}

	private void update() {
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
//		System.out.println("A".matches("[a-z]"));
	}
	
	public InputHandler getInput() {
		return inputHandler;
	}

}
