package org.rakeberg.entities;

import org.rakeberg.Constants;
import org.rakeberg.GamePanel;
import org.rakeberg.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static org.rakeberg.Constants.TILE_SIZE;

public class Player  extends Entity {

  private final GamePanel gamePanel;
  private final KeyHandler keyHandler;

  public Player(GamePanel gamePanel, KeyHandler keyHandler) {
    this.gamePanel = gamePanel;
    this.keyHandler = keyHandler;

    setDefaultValues();
    getPlayerImage();
  }

  private void setDefaultValues() {
    x = 100;
    y = 100;
    speed = 4;
    direction = "down";
  }

  public void getPlayerImage() {
    try {
      up1 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_up_1.png")));
      up2 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_up_2.png")));
      down1 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_down_1.png")));
      down2 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_down_2.png")));
      left1 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_left_1.png")));
      left2 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_left_2.png")));
      right1 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_right_1.png")));
      right2 = ImageIO.read(requireNonNull(getClass().getResourceAsStream("/player/boy_right_2.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void update() {
    if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
      if(keyHandler.upPressed) {
        direction = "up";
        y -= speed * (keyHandler.sprinting ? 2 : 1);
      }
      if(keyHandler.downPressed) {
        direction = "down";
        y += speed * (keyHandler.sprinting ? 2 : 1);
      }
      if(keyHandler.leftPressed) {
        direction = "left";
        x -= speed * (keyHandler.sprinting ? 2 : 1);
      }
      if(keyHandler.rightPressed) {
        direction = "right";
        x += speed * (keyHandler.sprinting ? 2 : 1);
      }

      spriteCounter++;
      if (spriteCounter > 12) {
        if (spriteNum == 1) {
          spriteNum = 2;
        } else if (spriteNum == 2) {
          spriteNum = 1;
        }
        spriteCounter = 0;
      }
    }
  }

  public void draw(Graphics2D g2) {
    BufferedImage image = null;
    switch (direction) {
      case "up":
        if (spriteNum == 1) {
          image = up1;
        } else if (spriteNum == 2) {
          image = up2;
        }
        break;
      case "down":
        if (spriteNum == 1) {
          image = down1;
        } else if (spriteNum == 2) {
          image = down2;
        }
        break;
      case "left":
        if (spriteNum == 1) {
          image = left1;
        } else if (spriteNum == 2) {
          image = left2;
        }
        break;
      case "right":
        if (spriteNum == 1) {
          image = right1;
        } else if (spriteNum == 2) {
          image = right2;
        }
    }

    g2.drawImage(image, x, y, TILE_SIZE, TILE_SIZE, null);
  }
}
