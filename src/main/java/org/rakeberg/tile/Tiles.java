package org.rakeberg.tile;

import java.util.Arrays;

public enum Tiles {
//  FLOOR(new Tile("/tiles/floor01.png", Edge.FLOOR, Edge.FLOOR, Edge.FLOOR, Edge.FLOOR, false)),
//  EARTH(new Tile("/tiles/earth.png", Edge.EARTH, Edge.EARTH, Edge.EARTH, Edge.EARTH, false)),
//  WALL(new Tile("/tiles/wall.png", Edge.WALL, Edge.WALL, Edge.WALL, Edge.WALL, false)),
  TREE(new Tile("/tiles/tree.png", Edge.TREE, Edge.TREE, Edge.TREE, Edge.TREE, false)),
  WATER_00(new Tile("/tiles/water00.png", Edge.WATER, Edge.WATER, Edge.WATER, Edge.WATER, false)),
  WATER_01(new Tile("/tiles/water01.png", Edge.WATER, Edge.WATER, Edge.WATER, Edge.WATER, false)),
  WATER_02(new Tile("/tiles/water02.png", Edge.SHORE, Edge.SHORE_UP, Edge.SHORE_LEFT, Edge.SHORE, false)),
  WATER_03(new Tile("/tiles/water03.png", Edge.SHORE, Edge.SHORE_UP, Edge.WATER, Edge.SHORE_UP, false)),
  WATER_04(new Tile("/tiles/water04.png", Edge.SHORE, Edge.SHORE, Edge.SHORE_RIGHT, Edge.SHORE_UP, false)),
  WATER_05(new Tile("/tiles/water05.png", Edge.SHORE_LEFT, Edge.WATER, Edge.SHORE_LEFT, Edge.SHORE, false)),
  WATER_06(new Tile("/tiles/water06.png", Edge.SHORE_RIGHT, Edge.SHORE, Edge.SHORE_RIGHT, Edge.WATER, false)),
  WATER_07(new Tile("/tiles/water07.png", Edge.SHORE_LEFT, Edge.SHORE_DOWN, Edge.SHORE, Edge.SHORE, false)),
  WATER_08(new Tile("/tiles/water08.png", Edge.WATER, Edge.SHORE_DOWN, Edge.SHORE, Edge.SHORE_DOWN, false)),
  WATER_09(new Tile("/tiles/water09.png", Edge.SHORE_RIGHT, Edge.SHORE, Edge.SHORE, Edge.SHORE_DOWN, false)),
  WATER_10(new Tile("/tiles/water10.png", Edge.WATER, Edge.SHORE_DOWN, Edge.SHORE_RIGHT, Edge.WATER, false)),
  WATER_11(new Tile("/tiles/water11.png", Edge.WATER, Edge.WATER, Edge.SHORE_LEFT, Edge.SHORE_DOWN, false)),
  WATER_12(new Tile("/tiles/water12.png", Edge.SHORE_RIGHT, Edge.SHORE_UP, Edge.WATER, Edge.WATER, false)),
  WATER_13(new Tile("/tiles/water13.png", Edge.SHORE_LEFT, Edge.WATER, Edge.WATER, Edge.SHORE_UP, false)),
  ROAD_00(new Tile("/tiles/road00.png", Edge.ROAD, Edge.ROAD, Edge.ROAD, Edge.ROAD, false)),
  ROAD_01(new Tile("/tiles/road01.png", Edge.DITCH, Edge.DITCH_UP, Edge.DITCH_LEFT, Edge.DITCH, false)),
  ROAD_02(new Tile("/tiles/road02.png", Edge.DITCH, Edge.DITCH_UP, Edge.GRASS, Edge.DITCH_UP, false)),
  ROAD_03(new Tile("/tiles/road03.png", Edge.DITCH, Edge.DITCH, Edge.DITCH_RIGHT, Edge.DITCH_UP, false)),
  ROAD_04(new Tile("/tiles/road04.png", Edge.DITCH_LEFT, Edge.GRASS, Edge.DITCH_LEFT, Edge.DITCH, false)),
  ROAD_05(new Tile("/tiles/road05.png", Edge.DITCH_RIGHT, Edge.DITCH, Edge.DITCH_RIGHT, Edge.GRASS, false)),
  ROAD_06(new Tile("/tiles/road06.png", Edge.DITCH_LEFT, Edge.DITCH_DOWN, Edge.DITCH, Edge.DITCH, false)),
  ROAD_07(new Tile("/tiles/road07.png", Edge.GRASS, Edge.DITCH_DOWN, Edge.DITCH, Edge.DITCH_DOWN, false)),
  ROAD_08(new Tile("/tiles/road08.png", Edge.DITCH_RIGHT, Edge.DITCH, Edge.DITCH, Edge.DITCH_DOWN, false)),
  ROAD_09(new Tile("/tiles/road09.png", Edge.GRASS, Edge.DITCH_DOWN, Edge.DITCH_RIGHT, Edge.GRASS, false)),
  ROAD_10(new Tile("/tiles/road10.png", Edge.GRASS, Edge.GRASS, Edge.DITCH_LEFT, Edge.DITCH_DOWN, false)),
  ROAD_11(new Tile("/tiles/road11.png", Edge.DITCH_RIGHT, Edge.DITCH_UP, Edge.GRASS, Edge.GRASS, false)),
  ROAD_12(new Tile("/tiles/road12.png", Edge.DITCH_LEFT, Edge.GRASS, Edge.GRASS, Edge.DITCH_UP, false)),
  GRASS_00(new Tile("/tiles/grass00.png", Edge.GRASS, Edge.GRASS, Edge.GRASS, Edge.GRASS, false)),
  GRASS_01(new Tile("/tiles/grass01.png", Edge.GRASS, Edge.GRASS, Edge.GRASS, Edge.GRASS, false));

  private final Tile tile;

  Tiles(Tile tile) {
    this.tile = tile;
  }

  public Tile getTile() {
    return tile;
  }

  public static Tile[] getTiles() {
    return Arrays.stream(Tiles.values())
        .map(Tiles::getTile)
        .toArray(Tile[]::new);
  }
}
