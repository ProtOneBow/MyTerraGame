package tile;


import main.GamePanel;
import tile.tiles.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class TileManager {
	GamePanel gamePanel;
	TileInstance[] tile;
	public TileInstance[] [] tileGrid;
	int loadedTiles = 0;

	public TileManager(GamePanel gp) {
		gamePanel = gp;

		//tile = new Tile[10];
		tileGrid = new TileInstance[gamePanel.maxWorldRow][gamePanel.maxWorldCol];
		initializeTiles();
		initializeWorld();

	}

	private void initializeTiles() {
		tile = new TileInstance[10];
		tile[0] = new Dirt(gamePanel);
		tile[1] = new Grass(gamePanel);
		tile[2] = new Stone(gamePanel);
		tile[3] = new Wood(gamePanel);
		tile[4] = new SilverOre(gamePanel);
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
				TileInstance tileInstance;
				if (worldRow == 25) {
					tileInstance = tile[1];
				} else if (worldRow > 25) {
					int stoneChance = (worldRow-25);
					if (Math.random()*100 < stoneChance) {
						tileInstance = tile[2];
					} else {
						tileInstance = tile[0];
					}

				} else {
					tileInstance = null;
				}
				tileGrid[worldRow][worldCol] = tileInstance;
				worldCol++;


				//System.out.println("worldCol: "+worldCol+" worldRow: "+worldRow +"  |  maxWorldCol "+ gamePanel.maxWorldCol+" maxWorldRow "+ gamePanel.maxWorldRow);
			}
			worldCol = 0;
			worldRow++;


		}
		tileGrid[25][25] = new Stone(gamePanel);
		tileGrid[25][26] = new SilverOre(gamePanel);
		tileGrid[25][27] = new Wood(gamePanel);

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
		loadedTiles = 0;
		for (int row = 0; row < gamePanel.maxWorldRow-1; row++) {
			for (int col = 0; col < gamePanel.maxWorldCol-1; col++) {
				if (tileGrid[row][col] != null && tileGrid[row][col].image != null) {
					tileGrid[row][col].draw(g2, gamePanel,col,row);
					if (tileGrid[row][col].isOnScreen(gamePanel,col,row)) {
						loadedTiles++;
					}
				}
			}
		}
		//for (TileInstance [] column : tileGrid) {
		//	for (TileInstance tileInstance : column) {
		//		if (tileInstance != null && tileInstance.image != null) {
		//			tileInstance.draw(g2);
		//			if (tileInstance.isOnScreen()){
		//				loadedTiles++;
		//			}
//
		//			//String tileName = tileInstance.getClass().toString().substring(17);
		//			//System.out.println(tileName+" worldX: "+tileInstance.getWorldX()+", worldY: "+tileInstance.getWorldY());
		//			//System.out.println("Player worldX:"+gamePanel.player.worldX+" worldY:"+gamePanel.player.worldY);
		//		}
//
		//	}
		//}
		//System.out.println(loadedTiles+" loaded tiles");

	}

}
