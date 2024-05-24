package item.items;

import item.ItemInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Stone extends ItemInstance {


	public Stone() {
		width = 8;
		height = 8;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/items/stone.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
