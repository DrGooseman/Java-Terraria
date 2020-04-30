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

    private int tileSize;

    public Tile(int tileType, int indexX, int indexY, int tileSize) {
        this.tileType = tileType;
        if (tileType == 1) maxHealth = 3;
        else maxHealth = 5;
        health = maxHealth;

        this.indexX = indexX;
        this.indexY = indexY;
        this.tileSize = tileSize;
        this.positionX = indexX * tileSize + (tileSize / 2);
        this.positionY = indexY * tileSize + (tileSize / 2);
    }

    public void paintComponent(Graphics g, int startX, int startY, int width, int height) {

        int alpha = (int) (health / (float) maxHealth * 255);

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

        g.fillRect(startX - 1, startY - 1, width - 2, height - 2);
    }

    public void setHighlighted(boolean isHighlighted) {
        if (isHighlighted)
            this.isHighlighted = isHighlighted;
    }

    public int getTileType() {
        return tileType;
    }

    public void setTileType(int tileType) {
        this.tileType = tileType;
    }

    public int getTopPosition() {
        return positionY - tileSize / 2;
    }
    public int getBottomPosition() {
        return positionY + tileSize / 2;
    }
    public int getRightPosition() {
        return positionX + tileSize / 2;
    }
    public int getLeftPosition() {
        return positionX - tileSize / 2;
    }

    public void hit(int power) {
        health -= power;
        if (health < 0) health = 0;
        if (health == 0)
            destroy();
    }

    private void destroy() {
        tileType = 0;
    }

    public int getIndexX() {
        return indexX;
    }

    public int getIndexY() {
        return indexY;
    }

}