package tile;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
	public BufferedImage image;
	public boolean collision = false;


	public boolean isOnScreen(GamePanel gamePanel, int col, int row){
		int screenX = gamePanel.tileSize*col - (int) gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = gamePanel.tileSize*row - (int) gamePanel.player.worldY + gamePanel.player.screenY;
		if (screenX > gamePanel.screenWidth || screenX < gamePanel.tileSize*-1) {
			//	System.out.println(screenX+" > "+gamePanel.screenWidth);
			return false;
		}
		if (screenY > gamePanel.screenHeight || screenY < gamePanel.tileSize*-1) {
			//	System.out.println(screenX+" > "+gamePanel.screenWidth);
			return false;
		}
		return true;
	}

	public void draw(Graphics2D g2, GamePanel gamePanel, int col, int row){
		col -=1;
		int screenX = (col*gamePanel.tileSize) - (int) gamePanel.player.worldX + gamePanel.player.screenX;
		int screenY = (row*gamePanel.tileSize) - (int) gamePanel.player.worldY + gamePanel.player.screenY;
		if (!isOnScreen(gamePanel, col, row)) {
			return;
		}
		if (image == null) {
			return;
		}
		g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);



	}

}

