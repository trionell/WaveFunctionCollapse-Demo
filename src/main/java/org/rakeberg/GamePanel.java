package org.rakeberg;

import org.rakeberg.entities.Player;
import org.rakeberg.tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;

import static org.rakeberg.Constants.FPS;
import static org.rakeberg.Constants.SCREEN_HEIGHT;
import static org.rakeberg.Constants.SCREEN_WIDTH;

public class GamePanel extends JPanel implements Runnable {

  // SCREEN SETTINGS


  TileManager tileManager = new TileManager();
  KeyHandler keyHandler = new KeyHandler();
  Thread gameThread;
  Player player = new Player(this, keyHandler);

  public GamePanel() {
    this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    this.setBackground(Color.BLACK);
    this.setDoubleBuffered(true);
    this.addKeyListener(keyHandler);
    this.setFocusable(true);
  }

  public void startGameThread() {
    gameThread = new Thread((this));
    gameThread.start();
  }

  @Override
  public void run() {

    double drawInterval = 1000000000f / FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int drawCount = 0;

    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        update();
        repaint();
        delta--;
        drawCount++;
      }

      if (timer >= 1000000000) {
        System.out.println("FPS: " + drawCount);
        drawCount = 0;
        timer = 0;
      }
    }
  }

  public void update() {
    player.update();
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;
    tileManager.draw(g2);
    player.draw(g2);
    g2.dispose();
  }
}
