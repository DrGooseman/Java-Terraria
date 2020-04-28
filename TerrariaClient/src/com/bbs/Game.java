package com.bbs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

public class Game extends JPanel implements KeyListener, MouseListener, MouseMotionListener  {

    private JFrame mainFrame;
    public static int screenWidth;
    public static int screenHeight;
    public static int screenWidthCenter;
    public static int screenHeightCenter;

    private boolean gameRunning = false;

    private double lastFpsTime;
    private double fps;

    World world;
    Player player;

    public Game(int screenWidth, int screenHeight, JFrame mainFrame) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        screenWidthCenter = screenWidth / 2;
        screenHeightCenter = screenHeight / 2;
        this.mainFrame = mainFrame;
        setLayout(new OverlayLayout(this));

        int worldWidth = 200;
        int worldHeight = 200;
        int tileSize = 32;

         world = new World(worldWidth, worldHeight, tileSize);
         player = new Player(worldWidth * tileSize / 2, worldHeight * tileSize / 2, world);
         world.focusOn(player);
        this.add(world);
        this.add(player);

        setComponentZOrder(world, 1);
        setComponentZOrder(player, 0);

        gameRunning = true;
        Thread loop = new Thread() {
            public void run() {
                gameLoop();
            }
        };
        loop.start();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        setDoubleBuffered(true);
//        new Timer(16, new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//              //  gameLoop();
//                render();
//            }
//        }).start();
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
    }


    public void gameLoop() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

        // keep looping round til the game ends
        while (gameRunning) {
            // work out how long its been since the last update, this
            // will be used to calculate how far the entities should
            // move this loop
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            // update the frame counter
            lastFpsTime += updateLength;
            fps++;

            // update our FPS counter if a second has passed since
            // we last recorded
            if (lastFpsTime >= 1000000000) {
              //  System.out.println("(FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            // update the game logic
            doGameUpdates(delta);

            // draw everyting
            render();

            // we want each frame to take 10 milliseconds, to do this
            // we've recorded when we started the frame. We add 10 milliseconds
            // to this and then factor in the current time to give
            // us our final value to wait for
            // remember this is in ms, whereas our lastLoopTime etc. vars are in ns.
            try {
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
           } ;
        }
    }

    private void render() {
       // Graphics g = this.getBu
     //   BufferStrategy buffer = mainFrame.getBufferStrategy();
        //
        //        // Get context from the JPanel we are going to draw to
        //     //   Graphics g = buffer.getDrawGraphics();
        //
        //        // Start with clearing the drawing surface
        //     //   g.setColor(Color.MAGENTA);
        //       // g.fillRect(0, 0, screen.getWidth(), screen.getHeight());
        //      //  g.dispose();
        //
        //     //   world.paintComponent(g);
        //
        //        // After we draw everything to the back buffer, we show that buffer
        //      //  buffer.show();
        //      //  invalidate();
        revalidate();
        repaint();
      // world.repaint();
      // player.repaint();
    }

    private void doGameUpdates(double delta) {
        player.updateState(delta);
    }




//    public Point screenToWorldPosition(Point screenPosition)
//    {
//        Point worldPosition = new Point((int) (screenPosition.x - screenWidthCenter + player.getPosX()), (int)(screenPosition.y - screenHeightCenter + player.getPosY()));
//
//        return worldPosition;
//    }





    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        char ch = e.getKeyChar();
      //  System.out.println(e.getKeyChar());
        if (ch == 'a' || ch == 'w' || ch == 's'|| ch == 'd'|| ch == ' ')
            player.press(ch, true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char ch = e.getKeyChar();
        if (ch == 'a' || ch == 'w' || ch == 's'|| ch == 'd')
            player.press(ch, false);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        player.mouseClicked(true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        player.mouseClicked(false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
       // Point point = screenToWorldPosition(e.getPoint());
        player.mouseMoved(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      //  Point point = screenToWorldPosition(e.getPoint());
        player.mouseMoved(e.getPoint());
    }

}
