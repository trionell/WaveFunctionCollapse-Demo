package org.rakeberg.wfc;

import org.rakeberg.tile.Direction;
import org.rakeberg.tile.Tile;
import org.rakeberg.tile.Tiles;
import org.rakeberg.wfc.constraint.Constraint;
import org.rakeberg.wfc.constraint.NeighborConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import static org.rakeberg.tile.Edge.*;

public class WaveFunctionCollapse {
  private static final Random RANDOM = new Random();

  private Tile[][][] map;
  int finishedCells = 0;
  private final Stack<Coordinate> stack = new Stack<>();
  private final List<Constraint> constraints;
  private final int cols;
  private final int rows;

  public WaveFunctionCollapse(int cols, int rows) {
    this.cols = cols;
    this.rows = rows;
    this.constraints = loadConstraints();
  }

  public Tile[][] generateMap() {
    System.out.println("Generating map using WFC!");
    map = new Tile[cols][rows][Tiles.values().length];
    populateMapWithOptions();

    // Loop
    while (!isDone()) {
      //printMap();
      Coordinate currentCellCoords = getNextCoordinate();
      Coordinate neighborUp = new Coordinate(currentCellCoords.col, currentCellCoords.row - 1);
      Coordinate neighborDown = new Coordinate(currentCellCoords.col, currentCellCoords.row + 1);
      Coordinate neighborLeft = new Coordinate(currentCellCoords.col - 1, currentCellCoords.row);
      Coordinate neighborRight = new Coordinate(currentCellCoords.col + 1, currentCellCoords.row);

      applyConstraints(neighborUp, currentCellCoords, Direction.UP);
      applyConstraints(neighborDown, currentCellCoords, Direction.DOWN);
      applyConstraints(neighborLeft, currentCellCoords, Direction.LEFT);
      applyConstraints(neighborRight, currentCellCoords, Direction.RIGHT);

    }

    return convertToFinishedMap(map);
  }

  private void applyConstraints(Coordinate neighbor, Coordinate currentCellCoords, Direction direction) {
    if (inside(neighbor, cols, rows)) {
      if (map[neighbor.col][neighbor.row].length > 1) {
        for (Constraint constraint : constraints) {
          map[neighbor.col][neighbor.row] = constraint.apply(map[currentCellCoords.col()][currentCellCoords.row][0], map[neighbor.col][neighbor.row], direction);
        }
        if (map[neighbor.col][neighbor.row].length == 1) {
          stack.push(neighbor);
        }
      }
    }
  }

  private void printMap() {
    System.out.println("-------------------------------------------------------------");
    for (Tile[][] tiles : map) {
      for (Tile[] tile : tiles) {
        System.out.print(tile.length + " ");
      }
      System.out.print("\n");
    }
  }

  private Coordinate getNextCoordinate() {
    // Take coordinate from the stack
    System.out.println("Stack size: " + stack.size());
    if (stack.size() > 0) {
      finishedCells++;
      return stack.pop();
    }

    // If nothing in the stack, pick a random cell out of the cells with the lowest entropy and give it entropy of 1.
    int lowestEntropy = findLowestEntropyAboveOne();
    Coordinate next = getRandomCellWithEntropy(lowestEntropy);
    map[next.col][next.row] = new Tile[]{map[next.col][next.row][RANDOM.nextInt(map[next.col][next.row].length)]};
    finishedCells++;
    return next;
  }

  private Coordinate getRandomCellWithEntropy(int entropy) {
    List<Coordinate> candidates = new ArrayList<>();
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j].length == entropy) {
          candidates.add(new Coordinate(i, j));
        }
      }
    }
    return candidates.get(RANDOM.nextInt(candidates.size()));
  }

  private int findLowestEntropyAboveOne() {
    int entropy = Tiles.values().length; // Max possible entropy
    for (Tile[][] tiles : map) {
      for (Tile[] tile : tiles) {
        if (tile.length > 1 && tile.length < entropy) {
          entropy = tile.length;
        }
      }
    }
    //System.out.println("Lowest non-1 entropy: " + entropy);
    return entropy;
  }

  private boolean inside(Coordinate coordinate, int cols, int rows) {
    return coordinate.col >= 0 && coordinate.col < cols &&
        coordinate.row >= 0 && coordinate.row < rows;
  }

  private Tile[][] convertToFinishedMap(Tile[][][] map) {
    Tile[][] outMap = new Tile[map.length][];

    for (int i = 0; i < map.length; i++) {
      outMap[i] = new Tile[map[i].length];
      for (int j = 0; j < map[i].length; j++) {
        if (map[i][j].length != 1) {
          throw new RuntimeException("Expected entropy of 1 at " + i + ":" + j + ". Got " + map[i][j].length);
        }
        outMap[i][j] = map[i][j][0];
      }
    }

    return outMap;
  }

  private boolean isDone() {
    for (Tile[][] tiles : map) {
      for (Tile[] tile : tiles) {
        if (tile.length > 1) {
          return false;
        }
      }
    }
    return true;
  }

  private Tile[][] getMapWithRandomTiles() {
    Tile[][] out = new Tile[map.length][map[0].length];

    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        out[i][j] = map[i][j][RANDOM.nextInt(map[i][j].length)];
      }
    }

    return out;
  }

  private void populateMapWithOptions() {
    for (int i = 0; i < map.length; i++) {
      for (int j = 0; j < map[i].length; j++) {
        map[i][j] = Tiles.getTiles();
      }
    }
  }

  private List<Constraint> loadConstraints() {
    return List.of(
      new NeighborConstraint(FLOOR, Set.of(EARTH, TREE, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(EARTH, Set.of(FLOOR, TREE, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(TREE, Set.of(FLOOR, EARTH, WALL, WATER, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(WALL, Set.of(TREE, WATER, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(WATER, Set.of(FLOOR, EARTH, TREE, WALL, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(GRASS, Set.of(FLOOR, EARTH, WATER, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(ROAD, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(ROAD_UP, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(ROAD_RIGHT, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(ROAD_DOWN, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(ROAD_LEFT, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, SHORE_UP, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(SHORE_UP, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_RIGHT, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(SHORE_RIGHT, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_DOWN, SHORE_LEFT)),
      new NeighborConstraint(SHORE_DOWN, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_LEFT)),
      new NeighborConstraint(SHORE_LEFT, Set.of(FLOOR, EARTH, TREE, WALL, WATER, GRASS, ROAD, ROAD_UP, ROAD_RIGHT, ROAD_DOWN, ROAD_LEFT, SHORE_UP, SHORE_RIGHT, SHORE_DOWN))
    );
  }

  private record Coordinate(
    int col,
    int row
  ) {}
}
