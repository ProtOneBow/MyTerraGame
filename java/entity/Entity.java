package entity;

import tile.TileInstance;
import tile.TileManager;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Entity {
	public float worldX, worldY; //positions in WORLD MAP
	int height;
	public int width;
	public float speed;
	public float speedX, speedY;
	public float accelX, accelY;
	public static float terminalVelocity;

	public BufferedImage left1, left2, left3, leftJump,
						right1, right2, right3, rightJump;
	public String direction;

	public int spriteCounter = 0;
	public int spriteNum = 1;

	public Rectangle2D.Float hitBox;
	public boolean collisionOn = false;
	public boolean isFalling = false;
	public boolean onFloor = false;
	public boolean collisionWall = false;
	public boolean moving;
	public int jumpCounter;
	public boolean jumping;
	public int jumpHeight;

	public void initHitbox() {
		hitBox = new Rectangle2D.Float(worldX, worldY, width, height);
	}

	//debug
	public void drawHitbox(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawRect((int)hitBox.x, (int)hitBox.y, (int)hitBox.width, (int)hitBox.height);

	}
	public void updateHitbox(){
		hitBox.x = (int) worldX;
		hitBox.y = (int) worldY;
	}
	public Rectangle2D.Float getHitBox() {
		return hitBox;
	}

	//Player class's jump can take in KeyHandler and determine height based on how long jump is held for
	public void jump(){
		if (!jumping) {
			if (!isFalling) {
				jumping = true;
				jumpCounter = jumpHeight;
				accelY = jumpHeight;
			}

		} else {
			jumpCounter--;
			if (jumpCounter < 0) {
				jumpCounter = 0;
				jumping = false;
			}

		}
	}

}
