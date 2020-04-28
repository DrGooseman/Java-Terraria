package com.bbs;

import javax.swing.*;
import java.awt.*;

public class Tile extends JPanel {

    private int tileType;
    private boolean isHighlighted = false;

    private int indexX;
    private int indexY;
    private int positionX;
    private int positionY;

    private int health;
    private int maxHealth;

    public Tile(int tileType, int indexX, int indexY) {
        this.tileType = tileType;
        if (tileType == 1) maxHealth = 3;
        else maxHealth = 5;
        health = maxHealth;

        this.indexX = indexX;
        this.indexY = indexY;
        this.positionX = indexX * (World.getTileSize()) + (World.getTileSize()/2);
        this.positionY = indexY * (World.getTileSize()) + (World.getTileSize()/2);
    }

    public void paintComponent(Graphics g, int startX, int startY, int width, int height) {
       // super.paintComponent(g);
        int alpha = (int) (health/(float)maxHealth * 255);

        if (isHighlighted)
            g.setColor(Color.YELLOW);
        else if (tileType == 0)
            g.setColor(Color.BLUE);
        else if (tileType == 1)
            g.setColor(new Color(120, 60, 0, alpha));
        else if (tileType == 2)
            g.setColor(new Color(85, 85, 85, alpha));
        else if (tileType == 3)
            g.setColor(new Color(194, 115, 51, alpha));
        else if (tileType == 4)
            g.setColor(new Color(165, 165, 165, alpha));
        else if (tileType == 5)
            g.setColor(new Color(255, 215, 0, alpha));

        g.fillRect(startX-1, startY-1, width-2, height-2);







    }

    public void setHighlighted(boolean isHighlighted){
        if (isHighlighted)
        this.isHighlighted = isHighlighted;
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public int getTopPosition(){
        return positionY - World.getTileSize()/2;
}

}