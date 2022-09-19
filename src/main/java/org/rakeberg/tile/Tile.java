package org.rakeberg.tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.util.Objects.requireNonNull;

public class Tile {

  private final BufferedImage image;
  private final boolean collision;
  private final Edge edgeUp;
  private final Edge edgeRight;
  private final Edge edgeDown;

  private final Edge edgeLeft;

  public Tile(String imagePath, Edge edgeUp, Edge edgeRight, Edge edgeDown, Edge edgeLeft, boolean collision) {
    this.edgeUp = edgeUp;
    this.edgeRight = edgeRight;
    this.edgeDown = edgeDown;
    this.edgeLeft = edgeLeft;
    this.collision = collision;

    try {
      this.image = ImageIO.read(requireNonNull(getClass().getResourceAsStream(imagePath)));
    } catch (IOException e) {
      throw new RuntimeException("Failed to load tile image (" + imagePath + ")", e);
    }
  }

  public BufferedImage getImage() {
    return image;
  }

  public Edge getEdgeUp() {
    return edgeUp;
  }

  public Edge getEdgeRight() {
    return edgeRight;
  }

  public Edge getEdgeDown() {
    return edgeDown;
  }

  public Edge getEdgeLeft() {
    return edgeLeft;
  }

  public boolean isCollision() {
    return collision;
  }
}
