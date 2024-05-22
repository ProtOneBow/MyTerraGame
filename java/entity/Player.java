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
		speed = 4;
		terminalVelocity = 20;
		direction = "right";



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
		if (keyHandler.leftPressed) {
			direction = "left";
			spriteCounter++;
		}
		else if (keyHandler.rightPressed) {
			direction = "right";
			spriteCounter++;
		}
		collisionOn = false;
		//gamePanel.collisionHandler.checkCollision(this);
		//System.out.println("Collision: "+collisionOn);
		//System.out.println("Falling: "+isFalling);
		// Player can move if collision is off

		/*
		if(collisionOn == false) {

			if (direction.equals("left") && keyHandler.leftPressed) {
				worldX -= speed;
			} else if (direction.equals("right") && keyHandler.rightPressed) {
				worldX += speed;
			}
		}
		*/

		//if (keyHandler.leftPressed && direction.equals("left") && gamePanel.collisionHandler.canMoveHere(worldX-speed,worldY, hitBox.width, hitBox.height)) {
		//	worldX -= speed;
		//} else if (keyHandler.rightPressed && direction.equals("right") && gamePanel.collisionHandler.canMoveHere(worldX+speed,worldY,hitBox.width,hitBox.height)) {
		//	worldX += speed;
		//}
		updatePos();



		if (onFloor && keyHandler.spacePressed) {
			onFloor = false;
			System.out.println("Jump!");
			jump();
		}
		//if (isFalling) {
		//	worldY++;
		//}

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

		//if (!keyHandler.leftPressed && !keyHandler.rightPressed) {
		//	return;
		//}
		//float xSpeed = 0, ySpeed = 0;
		if (jumpCounter == 0 && keyHandler.spacePressed && !isFalling) {
			jumping = true;
			accelY = 10;
		}
		if (jumping) jumpCounter++;
		if (jumpCounter > 10) {
			jumping = false;
			jumpCounter = 0;
		}
		if (keyHandler.leftPressed && !keyHandler.rightPressed)
			speedX = -speed;
		else if (keyHandler.rightPressed && !keyHandler.leftPressed)
			speedX = speed;
		else {
			if (-speedX == 4)
				spriteNum = 1;
			else if (speedX == 4)
				spriteNum = 1;
		}
		isFalling = true;
		if (!gamePanel.collisionHandler.canMoveHere(worldX, worldY+(hitBox.height), hitBox.width, hitBox.height)) {
			isFalling = false;
		}

		if (isFalling)
			speedY += 9.81/ gamePanel.tileSize;
		else
			speedY = 0;

		speedY -= accelY;
		accelY/=10;
		if (!keyHandler.spacePressed) {
			accelY /= 10;
		}

		if (speedY > terminalVelocity) {
			speedY = terminalVelocity;
		}

		//System.out.println(speedX);

		boolean topCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY, hitBox.width, hitBox.height);
		boolean middleCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY+ gamePanel.tileSize, hitBox.width, hitBox.height);
		boolean bottomCollision = !gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY+ (gamePanel.tileSize*2), hitBox.width, hitBox.height);
		worldY += speedY;
		if ((!topCollision && !bottomCollision) && !middleCollision) {
			if (!keyHandler.leftPressed && !keyHandler.rightPressed) {
				return;
			}
			worldX += speedX;

			moving = true;
		} else {
			speedX = 0;
			if (bottomCollision && !middleCollision && !topCollision && gamePanel.collisionHandler.canMoveHere(worldX+speedX, worldY-speedY, hitBox.width, hitBox.height)) {
				System.out.println("Attempt block stepup");

				worldY-= gamePanel.tileSize/4f;
			}
		}
		//System.out.println(topCollision+" "+middleCollision+" "+bottomCollision);



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



}
