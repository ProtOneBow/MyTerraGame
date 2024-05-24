package main;

import entity.Entity;
import tile.TileInstance;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class CollisionHandler {
	GamePanel gamePanel;
	public CollisionHandler(GamePanel gp) {
		gamePanel = gp;
	}



	public boolean canMoveHere(float x, float y, float width, float height) {
		// x = worldX of target location
		// y = worldY of target location

		//width and height = hitbox of collision entity

		//float centerX = x+entity.width/2;
		//float centerY = y+height/2;



		int leftCol = (int)((x)/gamePanel.tileSize)+2;
		int rightCol = (int)((x+width)/gamePanel.tileSize)+2;
		int row = (int)(y+(height/2))/gamePanel.tileSize;

		//leftCol = fixCol(leftCol);
		//rightCol = fixCol(rightCol);
		//row = fixRow(row);


		TileInstance [][] tileGrid = gamePanel.tileManager.tileGrid;
		if (isOutOfBounds(leftCol, row) || isOutOfBounds(rightCol, row)) {
			return false;
		}
		if (tileGrid[row][rightCol] == null || !tileGrid[row][rightCol].collision) {
			if (tileGrid[row][leftCol] == null || !tileGrid[row][leftCol].collision) {
				return true;
			}
		}
		return false;
	}

	private boolean isOutOfBounds(int col, int row){
		if (col > gamePanel.maxWorldCol-1 || col < 0) {
			return true;
		}
		if (row > gamePanel.maxWorldRow-1 || row < 0) {
			return true;
		}

		return false;
	}
	private int fixCol(int col){
		if (col > gamePanel.maxWorldCol-1) col = gamePanel.maxWorldCol-1;
		if (col < 0) col = 0;
		return col;
	}
	private int fixRow(int row){
		if (row > gamePanel.maxWorldRow-1) row = gamePanel.maxWorldRow-1;
		if (row < 0) row = 0;
		return row;
	}



}

















/*package main;

import entity.Entity;
import tile.TileInstance;

import java.awt.*;

public class CollisionHandler {
	GamePanel gamePanel;
	int entityLeftCol;
	int entityRightCol;
	int entityTopRow;
	int entityBottomRow;

	public CollisionHandler(GamePanel gp) {
		gamePanel = gp;

	}

	//can take in Player as Player IS A Entity
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
		int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
		int entityTopWorldY = entity.worldY + entity.collisionBox.y;
		int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

		entityLeftCol = entityLeftWorldX/gamePanel.tileSize; //gets tile on LEFT of player's boundary box
		entityRightCol = entityRightWorldX/gamePanel.tileSize; //gets tile on RIGHT of player's boundary box
		entityTopRow = entityTopWorldY/gamePanel.tileSize; //gets tile on TOP of player's boundary box
		entityBottomRow = entityBottomWorldY/gamePanel.tileSize; //gets tile on TOP of player's boundary box

		TileInstance [][] tileGrid = gamePanel.tileManager.tileGrid;

		System.out.println();

	//	System.out.println("topLeft: "+tileGrid[entityTopRow][entityLeftCol]);
	//	System.out.println("topRight: "+tileGrid[entityTopRow][entityRightCol]);
	//	System.out.println("bottomLeft: "+tileGrid[entityBottomRow][entityLeftCol]);
	//	System.out.println("bottomRight: "+tileGrid[entityBottomRow][entityRightCol]);



		maximumBounds();


		TileInstance tileNum1, tileNum2;

		tileNum1 = gamePanel.tileManager.tileGrid[entityLeftCol][entityBottomRow];
		tileNum2 = gamePanel.tileManager.tileGrid[entityRightCol][entityBottomRow];



		if (entity.direction.equals("left")) {
			entityLeftCol = (int) (entityLeftWorldX - entity.speed)/gamePanel.tileSize;
			maximumBounds();
			tileNum1 = gamePanel.tileManager.tileGrid[entityLeftCol][entityTopRow];
			tileNum2 = gamePanel.tileManager.tileGrid[entityLeftCol][entityBottomRow];

		}
		else if (entity.direction.equals("right")) {
			entityRightCol = (int) (entityRightWorldX + entity.speed)/gamePanel.tileSize;
			maximumBounds();
			tileNum1 = gamePanel.tileManager.tileGrid[entityRightCol][entityTopRow];
			tileNum2 = gamePanel.tileManager.tileGrid[entityRightCol][entityBottomRow];

		}


		if (tileNum1 != null && tileNum2 != null && (tileNum1.collision || tileNum2.collision)) {
			entity.collisionOn = true;
		}


	}





	private void maximumBounds(){
		if (entityLeftCol < 0) entityLeftCol = 0;
		if (entityRightCol < 0) entityRightCol = 0;

		if (entityLeftCol >= gamePanel.maxWorldCol) entityLeftCol = gamePanel.maxWorldCol-1;
		if (entityRightCol >= gamePanel.maxWorldCol) entityRightCol = gamePanel.maxWorldCol-1;

		if (entityTopRow < 0) entityTopRow = 0;
		if (entityBottomRow < 0) entityBottomRow = 0;

		if (entityTopRow >= gamePanel.maxWorldRow) entityTopRow = gamePanel.maxWorldRow-1;
		if (entityBottomRow >= gamePanel.maxWorldRow) entityBottomRow = gamePanel.maxWorldRow-1;
	}
}
*/