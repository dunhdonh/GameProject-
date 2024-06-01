package game;

import javax.swing.JPanel;
import entity.Player;
import java.awt.*;

import Tile.TileManager;

public class DragonBall extends JPanel implements Runnable{

        // Screen Setting
        final int OriginalTileSize = 16; //16px
        final int scale = 3;
        public final int tileSize = OriginalTileSize * scale; //48x48px
        final int maxScreenCol = 16;
        final int maxScreenRow = 12;
        public final int screenWidth = tileSize * maxScreenCol; // 768px
        public final int screenHeight = tileSize * maxScreenRow;// 576px

        int FPS = 60;

        KeyHandle keyHandle = new KeyHandle();
        Thread gameThread;
        Player player = new Player(this, keyHandle);
        TileManager tileManager = new TileManager(this);
        public CollisionCheck collCheck = new CollisionCheck(this);

        // Set player default position
        int playerX = 100;
        int playerY = 100;
        int playerSpeed = 5;
        public DragonBall() {
            this.setPreferredSize(new Dimension(screenWidth, screenHeight));
            this.setBackground(Color.BLACK);
            this.setDoubleBuffered(true);
            this.addKeyListener(keyHandle);
            this.setFocusable(true);
        }

        public void startGameThread() {
            gameThread = new Thread(this);
            gameThread.start();
        }

        public void update() {
            player.update();
        } 

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            tileManager.drawTile(g2);
            player.draw(g2);
            
        }

        @Override
        public void run() {

            double drawInterval = 1000000000 / FPS;
            double nextDrawTime = System.nanoTime() + drawInterval;
            

            while (gameThread != null) {
                update();
                repaint();

                try{
                    double waitTime = nextDrawTime - System.nanoTime();
                    if (waitTime <0 )
                    waitTime = 0;
                    Thread.sleep((long) (waitTime/1000000));
                    nextDrawTime += drawInterval;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        }

}

