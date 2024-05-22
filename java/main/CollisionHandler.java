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
		float centerX = x+width/2;
		float centerY = y+height/2;

		int leftCol = (int)((x)/gamePanel.tileSize)+2;
		int rightCol = (int)((x+width)/gamePanel.tileSize)+2;
		//int topRow = (int)(y)/gamePanel.tileSize;
		//int bottomRow = (int)(y+height)/gamePanel.tileSize;
		int row = (int)(y+(height/2))/gamePanel.tileSize;
		TileInstance [][] tileGrid = gamePanel.tileManager.tileGrid;
		if (tileGrid[row][rightCol] == null || !tileGrid[row][rightCol].collision) {
			if (tileGrid[row][leftCol] == null || !tileGrid[row][leftCol].collision) {
				return true;
			}
		}
		return false;
	}


	public void checkCollision(Entity entity) {
		TileInstance[][] tileGrid = gamePanel.tileManager.tileGrid;
		int tileSize = gamePanel.tileSize;
		double x = entity.worldX;
		double y = entity.worldY;
		Rectangle2D.Float collisionBox = entity.hitBox;
		boolean isGrounded = false;

		int leftCol = (int) ((x + collisionBox.x)/tileSize)-5;
		int rightCol = (int) ((x + collisionBox.x + collisionBox.width)/tileSize)-5;
		int bottomRow = (int) ((y + collisionBox.y)/tileSize)-1;
		int middleRow = (int) ((y + collisionBox.y - collisionBox.height/2)/tileSize)-1;
		int topRow = (int) ((y + collisionBox.y - collisionBox.height)/tileSize)-1;
		entity.collisionOn = false;
		entity.collisionWall = false;
		entity.onFloor = false;
		if (tileGrid[bottomRow][leftCol] != null && tileGrid[bottomRow][leftCol].collision && entity.direction.equals("left")) {
			entity.onFloor = true;
		}
		if (tileGrid[bottomRow][rightCol] != null && tileGrid[bottomRow][rightCol].collision && entity.direction.equals("right")) {
			entity.onFloor = true;
		}
		if (tileGrid[topRow][leftCol] != null && tileGrid[topRow][leftCol].collision && entity.direction.equals("left")) {
			entity.collisionWall = true;
		}
		if (tileGrid[middleRow][leftCol] != null && tileGrid[middleRow][leftCol].collision && entity.direction.equals("left")) {
			entity.collisionOn = true;
			entity.onFloor = false;
			entity.collisionWall = true;
		}

		if (tileGrid[topRow][rightCol] != null && !tileGrid[topRow][rightCol].collision && entity.direction.equals("right")) {
			entity.collisionOn = true;
			entity.collisionWall = true;
		}
		if (tileGrid[middleRow][rightCol] != null && tileGrid[middleRow][rightCol].collision && entity.direction.equals("right")) {
			entity.collisionOn = true;
			entity.onFloor = false;
			entity.collisionWall = true;
		}





		entity.isFalling = false;
		if ((tileGrid[bottomRow][leftCol] == null || !tileGrid[bottomRow][leftCol].collision)) entity.isFalling = true;
		else if (tileGrid[bottomRow][rightCol] == null || !tileGrid[bottomRow][rightCol].collision) entity.isFalling = true;
		if (entity.onFloor && !entity.collisionWall) entity.collisionOn = false;

		//for (int i = 0; i < 100; i++) {
		//	System.out.println();
		//}

	//	System.out.println("topRow: "+topRow+ " middleRow: "+middleRow+" bottomRow: "+bottomRow);
	//	System.out.println("leftCol: "+leftCol+ " rightCol: "+rightCol);
	//	System.out.println("topLeft: "+tileGrid[topRow][leftCol]);
	//	System.out.println("topRight: "+tileGrid[topRow][rightCol]);
	//	System.out.println("middleLeft: "+tileGrid[middleRow][leftCol]);
	//	System.out.println("middleRight: "+tileGrid[middleRow][rightCol]);
	//	System.out.println("bottomLeft: "+tileGrid[bottomRow][leftCol]);
	//	System.out.println("bottomRight: "+tileGrid[bottomRow][rightCol]);
		//entity.collisionOn = false;

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