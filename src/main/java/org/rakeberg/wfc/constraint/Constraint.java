package org.rakeberg.wfc.constraint;

import org.rakeberg.tile.Direction;
import org.rakeberg.tile.Tile;

public interface Constraint {

  /**
   * Applies the constraint on the neighboring tile.
   * @param source The current tile with entropy of 1
   * @param neighbor The neighbor with entropy > 1
   * @param direction the direction from source where neighbor is located
   * @return The neighbor with the new entropy after constraint has been applied
   */
  Tile[] apply(Tile source, Tile[] neighbor, Direction direction);

}
