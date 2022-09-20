package org.rakeberg.wfc.constraint;

import org.rakeberg.tile.Direction;
import org.rakeberg.tile.Edge;
import org.rakeberg.tile.Tile;

import java.util.Arrays;
import java.util.Set;

public class NeighborConstraint implements Constraint{

  private final Edge sourceEdge;
  private final Set<Edge> invalidNeighbors;

  public NeighborConstraint(Edge sourceEdge, Set<Edge> invalidNeighbors) {
    this.sourceEdge = sourceEdge;
    this.invalidNeighbors = invalidNeighbors;
  }

  @Override
  public Tile[] apply(Tile source, Tile[] neighbor, Direction direction) {
    switch (direction) {
      case UP -> {
        if (!sourceEdge.equals(source.getEdgeUp())) {
          return neighbor;
        }
        return Arrays.stream(neighbor)
            .filter(tile -> invalidNeighbors.contains(tile.getEdgeDown()))
            .toList()
            .toArray(new Tile[0]);
      }
      case RIGHT -> {
        if (!sourceEdge.equals(source.getEdgeRight())) {
          return neighbor;
        }
        return Arrays.stream(neighbor)
            .filter(tile -> invalidNeighbors.contains(tile.getEdgeLeft()))
            .toList()
            .toArray(new Tile[0]);
      }
      case DOWN -> {
        if (!sourceEdge.equals(source.getEdgeDown())) {
          return neighbor;
        }
        return Arrays.stream(neighbor)
            .filter(tile -> invalidNeighbors.contains(tile.getEdgeUp()))
            .toList()
            .toArray(new Tile[0]);
      }
      case LEFT -> {
        if (!sourceEdge.equals(source.getEdgeLeft())) {
          return neighbor;
        }
        return Arrays.stream(neighbor)
            .filter(tile -> invalidNeighbors.contains(tile.getEdgeRight()))
            .toList()
            .toArray(new Tile[0]);
      }
    }
    throw new RuntimeException("Did not match direction when applying constraint");
  }
}
