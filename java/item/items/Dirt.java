package item.items;

import item.ItemInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Dirt extends ItemInstance {


	public Dirt() {
		width = 8;
		height = 8;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/items/dirt.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
