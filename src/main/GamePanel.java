package src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.JWindow;

public class GamePanel extends JPanel implements Runnable{
    final int originalTileSize = 16;        // Is a standard size of game characters 16x16 (Vintage)
    final int scale = 3;                    // Pretty common

    final int tileSize = originalTileSize * scale;  // 48x48 actual scale
    final int maxScreenCol = 16;        /* */
    final int maxScreenRow = 12;        /* */
    final int screenWidth = tileSize * maxScreenCol;    // 768 pixels
    final int screenHeight = tileSize * maxScreenRow;   // 576 pixels

    /* FPS */
    private final int FPS = 60;
    private final int NANO_TO_MILLIS = 1000000;


    private final KeyHandler keyH = new KeyHandler();
    private Thread gameThread;

    // Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    /* Constructors */
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

public void startGameThread(){
    gameThread = new Thread(this);          // Creates a separated Trhread for this class
    gameThread.start();
}

    @Override
    public void run() {
        final long drawInterval = 1000 /FPS;        // refreshing every 0.016666... second
        long nextDrawTime = System.nanoTime() /NANO_TO_MILLIS + drawInterval;

        while(gameThread != null){


            System.out.println("GameLoop is running");

            // 1 UPDATES: update informations such as characters positions
            this.update();

            // 2 DRAW: draw the screen with the updated information
            this.repaint();

            try {
                long remainingTime = nextDrawTime - System.nanoTime() /NANO_TO_MILLIS;

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep(remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        if(keyH.upPressed == true){
            playerY -= playerSpeed;
        }else if(keyH.downPressed == true){
            playerY += playerSpeed;
        }else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
        }else if(keyH.rightPressed == true){
            playerX += playerSpeed;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
