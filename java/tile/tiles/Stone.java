package tile.tiles;

import main.GamePanel;
import tile.Tile;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Stone extends TileInstance {


	public Stone(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = true;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
