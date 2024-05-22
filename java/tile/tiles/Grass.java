package tile.tiles;

import main.GamePanel;
import tile.Tile;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Grass extends TileInstance {


	public Grass(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
