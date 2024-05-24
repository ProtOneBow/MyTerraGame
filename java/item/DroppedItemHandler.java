package item;

import java.awt.*;

public class DroppedItemHandler {
	DroppedItem [] droppedItems = new DroppedItem[1000];
	int firstFree;

	public void cleanItemList() {
		for (int i = 0; i < droppedItems.length; i++) {
			if (droppedItems[i] == null) {
				DroppedItem temp = droppedItems[i+1];
				droppedItems[i+1] = null;
				droppedItems[i] = temp;
				firstFree = i+1;
			}
		}
		if (firstFree > 1000) {
			droppedItems[0] = null;
			droppedItems[1] = null;
			firstFree = 0;
		}
	}
	public void addDroppedItem(DroppedItem item) {
		if (droppedItems[firstFree] == null) {
			droppedItems[firstFree] = item;
		} else {
			cleanItemList();
			addDroppedItem(item);
		}
	}


	public void draw(Graphics2D g2) {
		for (int i = 0; i < droppedItems.length; i++) {
			if (droppedItems[i] != null && droppedItems[i].dropped) {
				droppedItems[i].draw(g2);
			}
		}
	}

}
