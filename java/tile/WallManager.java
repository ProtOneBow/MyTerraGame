package tile;


import main.GamePanel;
import tile.tiles.*;
import tile.tiles.walls.DirtWall;
import tile.tiles.walls.WoodWall;

import java.awt.*;

public class WallManager {
	GamePanel gamePanel;
	public TileInstance[] [] tileGrid;
	int loadedWalls = 0;

	public WallManager(GamePanel gp) {
		gamePanel = gp;

		tileGrid = new TileInstance[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		this.initializeWorld();
	}


	public void initializeWorld() {
		int worldCol = 0; // x-level
		int worldRow = 0; // y-level
		int crashTimer = 0;
		while (worldRow < gamePanel.maxWorldRow-1) {
			while (worldCol < gamePanel.maxWorldCol-1) {
				crashTimer++;
				System.out.println(crashTimer);
				if (crashTimer > 15000) {
					System.out.println("Infinite Loop!");
					return;
				}
			//	int worldX = worldCol * gamePanel.tileSize;
			//	int worldY = worldRow * gamePanel.tileSize;

				TileInstance wallInstance;
				TileInstance tileInstance = gamePanel.tileManager.tileGrid[worldRow][worldCol];
				if (tileInstance != null && worldRow > 20) {
					//System.out.println("Wall Placed! "+gamePanel.tileManager.tileGrid[worldRow][worldCol]);
					wallInstance = new DirtWall(gamePanel);
				} else {
					wallInstance = null;
				}



				tileGrid[worldRow][worldCol] = wallInstance;
				worldCol++;


				//System.out.println("worldCol: "+worldCol+" worldRow: "+worldRow +"  |  maxWorldCol "+ gamePanel.maxWorldCol+" maxWorldRow "+ gamePanel.maxWorldRow);
			}
			worldCol = 0;
			worldRow++;


		}


	}

	//public void getTileImage() {
	//	try {
	//		tile[0] = new Tile();
	//		tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
	//		tile[1] = new Tile();
	//		tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));
	//		tile[2] = new Tile();
	//		tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
	//	}catch (IOException e) {
	//		e.printStackTrace();
	//	}
	//}
	public void draw(Graphics2D g2){
		loadedWalls = 0;

		for (int row = 0; row < tileGrid.length; row++) {
			for (int col = 0; col < tileGrid[row].length; col++) {
				if (tileGrid[row][col] != null && tileGrid[row][col].image != null && gamePanel.tileManager.tileGrid[row][col] == null) {
					tileGrid[row][col].draw(g2, gamePanel,col,row);
					if (tileGrid[row][col].isOnScreen(gamePanel,col,row)) {
						loadedWalls++;
					}
				}
			}
		}


		//for (TileInstance [] column : tileGrid) {
		//	for (TileInstance tileInstance : column) {
		//		if (tileInstance != null && tileInstance.image != null && gamePanel.tileManager.tileGrid[tileInstance.getRow()][tileInstance.getCol()+1] == null) {
		//			tileInstance.draw(g2);
		//			if (tileInstance.isOnScreen()){
		//				loadedWalls++;
		//			}
//
		//			//String tileName = tileInstance.getClass().toString().substring(17);
		//			//System.out.println(tileName+" worldX: "+tileInstance.getWorldX()+", worldY: "+tileInstance.getWorldY());
		//			//System.out.println("Player worldX:"+gamePanel.player.worldX+" worldY:"+gamePanel.player.worldY);
		//		}
//
		//	}
		//}
		System.out.println(loadedWalls+" loaded walls");

	}

}
