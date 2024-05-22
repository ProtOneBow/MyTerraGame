package tile.tiles;

import main.GamePanel;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Dirt extends TileInstance {


	public Dirt(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/dirt.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
