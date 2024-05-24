package item.items;

import item.ItemInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Wood extends ItemInstance {


	public Wood() {
		width = 12;
		height = 11;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/items/wood.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
