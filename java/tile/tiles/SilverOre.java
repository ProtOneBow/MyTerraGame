package tile.tiles;

import main.GamePanel;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SilverOre extends TileInstance {


	public SilverOre(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/silver_ore.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
