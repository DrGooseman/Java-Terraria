package com.bbs;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static int screenWidth = 1200;
    public static int screenHeight = 1000;

    public static void main(String[] args) {

        JFrame obj = new JFrame();
        Game game = new Game(screenWidth, screenHeight, obj);
        obj.add(game);
      //  World world = new World(200, 200, 16);
     //   obj.add(world);

        obj.setBounds(0, 0, screenWidth, screenHeight);
        obj.setBackground(Color.GRAY);
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
