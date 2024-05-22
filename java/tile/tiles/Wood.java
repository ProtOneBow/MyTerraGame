package tile.tiles;

import main.GamePanel;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Wood extends TileInstance {


	public Wood(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/wood.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
