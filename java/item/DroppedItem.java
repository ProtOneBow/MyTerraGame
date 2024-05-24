package item;

import java.awt.*;

public class DroppedItem extends ItemInstance {
	int worldX;
	int worldY;
	int stackSize;
	boolean dropped;
	int speedX;
	int speedY;

	public DroppedItem(int x, int y) {
		worldX = x;
		worldY = y;
		stackSize = 1;
		dropped = true;
	}

	public void addToStack(ItemInstance addedItem) {
		int itemAmount = 1;
		if (addedItem instanceof DroppedItem) {
			itemAmount = addedItem.stackSize;
			((DroppedItem) addedItem).deleteDroppedItemStack();
		}
		this.stackSize+=itemAmount;

	}

	public void deleteDroppedItemStack() {
		dropped = false;
		worldX = 0;
		worldY = 0;
		stackSize = 0;
	}


	public void draw(Graphics2D g2) {
		g2.drawImage(this.image, this.worldX,this.worldY, width, height,null);
	}
}
