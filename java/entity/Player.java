package entity;

import main.GamePanel;
import main.KeyHandler;
import main.MouseHandler;
import tile.TileInstance;
import tile.TileManager;
import tile.tiles.Dirt;
import tile.tiles.Wood;

import javax.imageio.ImageIO;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class Player extends Entity{
	GamePanel gamePanel;
	KeyHandler keyHandler;
	MouseHandler mouseHandler;

	public final int screenX;
	public final int screenY;

	private int clickedX;
	private int clickedY;


	public Player(GamePanel gp, KeyHandler keyH, MouseHandler mouseH) {
		gamePanel = gp;
		keyHandler = keyH;
		mouseHandler = mouseH;


		setDefaults();
		getPlayerImage();

		screenX = (gamePanel.screenWidth/2)-(width*2);
		screenY = (gamePanel.screenHeight/2)-(height*2);
		//initHitbox();
		hitBox = new Rectangle2D.Float(screenX + width, screenY+height, width + 10, height+45);
	}
	public void setDefaults() {
		worldX = gamePanel.worldWidth/2f;
		worldY = 1000;
		width = 40;
		height = 62;
		speed = 6.25f;
		terminalVelocity = 20;
		direction = "right";
		jumpHeight = 20;



	}
	public void getPlayerImage() {
		try {
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_2.png"));
			left3 = ImageIO.read(getClass().getResourceAsStream("/player/player_left_3.png"));
			leftJump = ImageIO.read(getClass().getResourceAsStream("/player/player_left_jump.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_2.png"));
			right3 = ImageIO.read(getClass().getResourceAsStream("/player/player_right_3.png"));
			rightJump = ImageIO.read(getClass().getResourceAsStream("/player/player_right_jump.png"));
		} catch(IOException e) {
			e.printStackTrace();
			//System.out.println("Failed to load Player Images");
		}
	}
	public void update() {
		if (keyHandler.leftPressed || keyHandler.rightPressed) {
			spriteCounter++;
		}

		collisionOn = false;
		updatePos();



		if (onFloor && keyHandler.spacePressed) {
			onFloor = false;
			//System.out.println("Jump!");
			jump();
		}

		if (spriteCounter > 10) {
			spriteCounter = 0;
			if (spriteNum == 1 || spriteNum == 2) spriteNum++;
			else spriteNum = 1;
		}
		if (mouseHandler.mouseClick) {
			mouseHandler.mouseClick = false;
			TileInstance[][] tileGrid = gamePanel.tileManager.tileGrid;
			int clickX = (int) ((mouseHandler.mouseX-gamePanel.screenWidth/2)+worldX - (gamePanel.tileSize/2));
			int clickY = (int) ((mouseHandler.mouseY-gamePanel.screenHeight/2) + worldY + (gamePanel.tileSize/2));

			int clickCol = ((clickX)/ gamePanel.tileSize)+3;
			int clickRow = ((clickY)/ gamePanel.tileSize)+2;

			clickCol = fixCol(clickCol);
			clickRow = fixCol(clickRow);

			System.out.println("x: "+clickCol+" y: "+clickRow);
			System.out.println("Clicked Block: "+tileGrid[clickRow][clickCol]);

			if (tileGrid[clickRow][clickCol] != null) {
				System.out.println("worldX: "+(clickCol*gamePanel.tileSize)+" worldY: "+(clickRow*gamePanel.tileSize));
				System.out.println("gridX: "+clickCol+" gridY: "+clickRow);
				tileGrid[clickRow][clickCol] = null;
			} else {

				tileGrid[clickRow][clickCol] = new Wood(gamePanel);
				tileGrid[clickRow][clickCol].toggleDebug(true);
				System.out.println("worldX: "+(clickCol*gamePanel.tileSize)+" worldY: "+(clickRow*gamePanel.tileSize));
				System.out.println("gridX: "+clickCol+" gridY: "+clickRow);
			}


		}
	}

	private void updatePos() {
		moving = false;
		jump();
		if (keyHandler.leftPressed && !keyHandler.rightPressed) {
			speedX = -speed;
			direction = "left";
		}
		else if (keyHandler.rightPressed && !keyHandler.leftPressed) {
			speedX = speed;
			direction = "right";
		}
		else {
			speedX = 0;
			spriteNum = 1;
		}
		isFalling = true;
		if (!gamePanel.collisionHandler.canMoveHere(worldX, worldY+ hitBox.height, hitBox.width, hitBox.height+2)) {
			isFalling = false;
		}
		if (isFalling)
			speedY += 9.81/gamePanel.tileSize*4;
		else
			speedY = 0;
		speedY -= accelY;
		accelY=0;
		jump();
		if (speedY > terminalVelocity) {
			speedY = terminalVelocity;
		}
		worldY += speedY;
		boolean topCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY, hitBox.width, hitBox.height);
		boolean middleCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY+gamePanel.tileSize, hitBox.width, hitBox.height);
		boolean bottomCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY+gamePanel.tileSize*2, hitBox.width, hitBox.height);

		if ((!topCollision && !bottomCollision) && !middleCollision) {
			if (!keyHandler.leftPressed && !keyHandler.rightPressed) {
				return;
			}
			worldX += speedX;
			moving = true;
		} else {
			speedX = 0;
			if (bottomCollision && !middleCollision && !topCollision && gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY+speedY- gamePanel.tileSize, hitBox.width, hitBox.height)) {
				worldY-= gamePanel.tileSize/4f;
			}
		}
		if (topCollision && !middleCollision) {
			speedY = 5;
		}
		//System.out.println(topCollision+" "+middleCollision+" "+bottomCollision);



	}

	public void jump() {

		if (!jumping) {
			if (keyHandler.spacePressed && !isFalling) {
				jumping = true;
				jumpCounter = jumpHeight;
				accelY = jumpHeight;
			}

		} else {
			System.out.println("jumpCounter "+jumpCounter+" accelY "+accelY);
			jumpCounter--;
			if (jumpCounter < 0) {
				jumpCounter = 0;
				jumping = false;

			} else if (!keyHandler.spacePressed) {
				accelY=-1;
			}

		}

	}

	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		if (direction.equals("left")) {
			if (spriteNum == 1) image = left1;
			else if (spriteNum == 2) image = left2;
			else image = left3;
			if (isFalling) image = leftJump;

		} else {
			if (spriteNum == 1) image = right1;
			else if (spriteNum == 2) image = right2;
			else image = right3;
			if (isFalling) image = rightJump;
		}

		//if (0 > worldX-)
		g2.drawImage(image, screenX, screenY, width * gamePanel.scale, height * gamePanel.scale, null);

		g2.drawString("worldX: " + worldX + ", worldY: " + worldY, 0, 10);
		g2.drawString("gridX: " + ((int)worldX + width / 2) / gamePanel.tileSize + ", gridY: " + (int)worldY / gamePanel.tileSize, 0, 20);
		g2.drawString("fps: " + gamePanel.fps, 0, 30);
		g2.setColor(Color.red);
		drawHitbox(g2);
		g2.setColor(Color.orange);

		g2.drawRoundRect((int)hitBox.x, (int)hitBox.y, 3, 3, 1, 1);
		g2.drawRoundRect((int)hitBox.x + (int)hitBox.width - 3, (int)hitBox.y, 3, 3, 1, 1);

		g2.drawRoundRect((int)hitBox.x, (int)hitBox.y + (int)hitBox.height / 2, 3, 3, 1, 1);
		g2.drawRoundRect((int)hitBox.x + (int)hitBox.width - 3, (int)hitBox.y + (int)hitBox.height / 2, 3, 3, 1, 1);


		g2.drawRoundRect((int)hitBox.x, (int)hitBox.y + (int)hitBox.height - 3, 3, 3, 1, 1);
		g2.drawRoundRect((int)hitBox.x + (int)hitBox.width - 3, (int)hitBox.y + (int)hitBox.height - 3, 3, 3, 1, 1);



		g2.setColor(Color.CYAN);
		g2.drawRoundRect((int)hitBox.x+width/2, (int)hitBox.y+height, 5, 5, 4, 4);

		g2.setColor(Color.YELLOW);
		g2.drawRect((int)hitBox.x, (int)(hitBox.y+height/2), gamePanel.tileSize, gamePanel.tileSize);

	}

	private int fixCol(int col){
		if (col > gamePanel.maxWorldCol-1) col = gamePanel.maxWorldCol-1;
		if (col < 0) col = 0;
		return col;
	}
	private int fixRow(int row){
		if (row > gamePanel.maxWorldRow) row = gamePanel.maxWorldRow;
		if (row < 0) row = 0;
		return row;
	}



}
