package main;

import entity.Player;
import tile.TileManager;
import tile.WallManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GamePanel extends JPanel implements Runnable {

	//Screen Settings
	final int originalTileSize = 16; // 16x16 tile
	public int scale = 3;

	public final int tileSize =originalTileSize*scale;
	public final int maxScreenCol = 28;
	public final int maxScreenRow = 20;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;

	//WORLD SETTINGS
	public final int maxWorldCol = 75;
	public final int maxWorldRow = 75;
	public final int worldWidth = tileSize*maxWorldCol;
	public final int worldHeight =  tileSize*maxWorldRow;


	final int TARGET_FPS = 60;
	public int fps;

	KeyHandler keyHandler = new KeyHandler();
	MouseHandler mouseHandler = new MouseHandler();
	Thread gameThread;
	public Player player = new Player(this, keyHandler, mouseHandler);
	public TileManager tileManager = new TileManager(this);
	public WallManager wallManager = new WallManager(this);
	public CollisionHandler collisionHandler = new CollisionHandler(this);
	private int frames = 0;



	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.getHSBColor(195,57,100));
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.addMouseListener(mouseHandler);
		this.setFocusable(true);
	}

	public void startGameThread() {

		gameThread = new Thread(this);
		gameThread.start(); //.start() makes the run() method below start running


	}

	@Override
	public void run() {

		double drawInterval = 1000000000 / TARGET_FPS; //1000000000 nanoseconds = 1 second // 0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				repaint(); //for some reason, paintComponent(), which is built into JPanel, is called by using repaint()
				delta--;
				drawCount++;
			}
			if (timer >= 1000000000) { //everySecond
				//System.out.println("FPS: "+drawCount);
				//System.out.println("Player Coords: x "+this.player.worldX+", y "+this.player.worldY);
				fps = drawCount;
				drawCount = 0;
				timer = 0;
			}


		}
	}
	public void update() {
		player.update();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);//uses JPanel's paintComponent

		Graphics2D g2 = (Graphics2D) g;
		wallManager.draw(g2);
		tileManager.draw(g2);
		player.draw(g2);
		g2.dispose();

	}



}
