package tile.tiles.walls;

import main.GamePanel;
import tile.TileInstance;

import javax.imageio.ImageIO;
import java.io.IOException;

public class DirtWall extends TileInstance {


	public DirtWall(GamePanel gp) {
		super(gp);
		setDefaults();
	}

	public void setDefaults() {
		collision = false;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/dirt_wall.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
