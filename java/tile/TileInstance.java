package tile;

import main.GamePanel;

import java.awt.*;

public class TileInstance extends Tile{
	//private int worldX;
	//private int worldY;
	GamePanel gamePanel;
	private boolean debug;
	public TileInstance(GamePanel gp) {
		gamePanel = gp;
		//worldX = (col-1)*gp.tileSize;
		//worldY = row*gp.tileSize;
	}
	public void toggleDebug() {
		this.debug = true;
	}
	public void toggleDebug(boolean debug) {
		this.debug = debug;
	}


	//public int getWorldX() {
	//	return worldX;
	//}
	//public int getWorldY() {
	//	return worldY;
	//}
	//public int getCol() {
	//	return worldX/gamePanel.tileSize;
	//}
	//public int getRow() {
	//	return worldY/gamePanel.tileSize;
	//}

	//public boolean isOnScreen(){
	//	int screenX = getCol()*gamePanel.tileSize - (int) gamePanel.player.worldX + gamePanel.player.screenX;
	//	int screenY = getRow()*gamePanel.tileSize - (int) gamePanel.player.worldY + gamePanel.player.screenY;
	//	if (screenX > gamePanel.screenWidth || screenX < gamePanel.tileSize*-1) {
	//	//	System.out.println(screenX+" > "+gamePanel.screenWidth);
	//		return false;
	//	}
	//	if (screenY > gamePanel.screenHeight || screenY < gamePanel.tileSize*-1) {
	//		//	System.out.println(screenX+" > "+gamePanel.screenWidth);
	//		return false;
	//	}
	//	return true;
	//}
	//public void draw(Graphics2D g2){
	//	int screenX = worldX - (int) gamePanel.player.worldX + gamePanel.player.screenX;
	//	int screenY = worldY - (int) gamePanel.player.worldY + gamePanel.player.screenY;
	//	if (!isOnScreen()) {
	//		return;
	//	}
	//	if (image == null) {
	//		return;
	//	}
	//	g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
	//	if (debug) drawHitbox(g2);
//
//
	//}

	public void drawHitbox(Graphics2D g) {
		//int screenX = worldX - (int) gamePanel.player.worldX + gamePanel.player.screenX;
		//int screenY = worldY - (int) gamePanel.player.worldY + gamePanel.player.screenY;

		g.setColor(Color.RED);
		//g.drawRect(screenX, screenY, gamePanel.tileSize, gamePanel.tileSize);

	}


	//public String toString(){
	//	return this.getClass().toString().substring(17)+" at x"+worldX+", y"+worldY+" | at col "+getCol()+", at row "+getRow();
	//}

}
