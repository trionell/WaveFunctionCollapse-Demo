package org.rakeberg.tile;

import org.rakeberg.wfc.WaveFunctionCollapse;

import java.awt.*;

import static org.rakeberg.Constants.MAX_SCREEN_COL;
import static org.rakeberg.Constants.MAX_SCREEN_ROW;
import static org.rakeberg.Constants.TILE_SIZE;

public class TileManager {
  private final Tile[][] map;

  public TileManager() {
    map = generateMap();
  }

  private Tile[][] generateMap() {
    try {
      return new WaveFunctionCollapse(MAX_SCREEN_COL, MAX_SCREEN_ROW).generateMap();
    } catch (RuntimeException e) {
      System.out.println("Failed to generate map. Attempting again");
      return generateMap();
    }
  }

  public void draw(Graphics2D g2) {
    for (int i = 0; i < MAX_SCREEN_COL; i++) {
      for (int j = 0; j < MAX_SCREEN_ROW; j++) {
        g2.drawImage(map[i][j].getImage(), i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
      }
    }
  }
}
