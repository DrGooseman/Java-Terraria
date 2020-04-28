package com.bbs;

import javax.swing.*;
import java.awt.*;



public class World extends JPanel {
    private ImageIcon playerIcon;


    private int worldWidth = 200;
    private int worldHeight = 200;

    private  int tileSize = 16;
    private int offSetX = worldWidth/2 * tileSize;
    private int offSetY = worldHeight/2 * tileSize;

    private Player trackedObject;


    private Tile[][] tiles = new Tile[worldHeight][worldWidth];

    public World(int worldWidth, int worldHeight, int tileSize) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;

        this.tileSize = tileSize;

        offSetX = worldWidth/2 * tileSize;
        offSetY = worldHeight/2 * tileSize;

        generateWorld();
       // System.out.println("world created");
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        offSetX = (int)trackedObject.getPosX();
        offSetY =(int) trackedObject.getPosY();

//        g.setColor(Color.LIGHT_GRAY);
//        g.fillRect(0, 0, worldWidth, worldHeight);


        int startX = (offSetX - Game.screenWidthCenter)/ tileSize;
        if (startX < 0)
            startX = 0;
        int endX = (offSetX + Game.screenWidthCenter)/ tileSize;
        if (endX > tiles.length)
            endX = tiles.length;
        startX +=1;
        endX -= 1;

        int startY = (offSetY - Game.screenHeightCenter)/ tileSize;
        if (startY < 0)
            startY = 0;
        int endY = (offSetY + Game.screenHeightCenter)/ tileSize;
        if (endY > tiles.length)
            endY = tiles.length;
        startY +=1;
        endY -= 1;

        for (int i = startY; i < endY; i++){
            for (int j = startX; j < endX; j++){
                tiles[i][j].paintComponent(g, j * tileSize - offSetX + Game.screenWidthCenter, i* tileSize  - offSetY + Game.screenHeightCenter,   tileSize, tileSize);
            }
        }
//        for (int i = 0; i < tiles.length; i++){
//            for (int j = 0; j < tiles[i].length; j++){
//                tiles[i][j].paintComponent(g, j * tileSize - offSetX + Game.screenWidthCenter, i* tileSize  - offSetY + Game.screenHeightCenter,   tileSize, tileSize);
//
//            }
//        }
    }

    private void generateWorld(){

        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++){
                int tileType = 0;

                if (i > worldHeight/2)
                    tileType = 1;

                if (tileType == 1){
                    //stone
                    float chance = i/(float)worldHeight * .34f -.1f;
                    if (i > 0 && tiles[i-1][j].getTileType() == 2)
                        chance += 1.2f;
                    if (j > 0 && tiles[i][j-1].getTileType() == 2)
                        chance += 1.2f;
                    if (j > 0 && i > 0 && tiles[i-1][j-1].getTileType() == 2)
                        chance += 0.2f;
                    if (j < tiles[i].length -1 && i > 0 && tiles[i-1][j+1].getTileType() == 2)
                        chance += 0.8f;
                    if (Math.random()*4 < chance)
                        tileType = 2;
                    // bronze
                    chance = i/(float)worldHeight * .2f -.1f;
                    if (i > 0 && tiles[i-1][j].getTileType() == 3)
                        chance += 1.0f;
                    if (j > 0 && tiles[i][j-1].getTileType() == 3)
                        chance += 1.0f;
                    if (j > 0 && i > 0 && tiles[i-1][j-1].getTileType() == 3)
                        chance += 0.1f;
                    if (j < tiles[i].length -1 && i > 0 && tiles[i-1][j+1].getTileType() == 3)
                        chance += 0.6f;
                    if (Math.random()*4 < chance)
                        tileType = 3;
                    // silver
                    chance = i/(float)worldHeight * .16f -.1f;
                    if (i > 0 && tiles[i-1][j].getTileType() == 4)
                        chance += 1.0f;
                    if (j > 0 && tiles[i][j-1].getTileType() == 4)
                        chance += 1.0f;
                    if (j > 0 && i > 0 && tiles[i-1][j-1].getTileType() == 4)
                        chance += 0.1f;
                    if (j < tiles[i].length -1 && i > 0 && tiles[i-1][j+1].getTileType() == 4)
                        chance += 0.6f;
                    if (Math.random()*4 < chance)
                        tileType = 4;
                    // gold
                    chance = i/(float)worldHeight * .14f -.1f;
                    if (i > 0 && tiles[i-1][j].getTileType() == 5)
                        chance += 1.0f;
                    if (j > 0 && tiles[i][j-1].getTileType() == 5)
                        chance += 1.0f;
                    if (j > 0 && i > 0 && tiles[i-1][j-1].getTileType() == 5)
                        chance += 0.1f;
                    if (j < tiles[i].length -1 && i > 0 && tiles[i-1][j+1].getTileType() == 5)
                        chance += 0.6f;
                    if (Math.random()*4 < chance)
                        tileType = 5;
            }

                tiles[i][j] = new Tile(tileType, j, i, tileSize);
            }
        }
    }

    public void focusOn(Player player) {
        trackedObject = player;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTileAtIndex(int x, int y){
        return tiles[y / tileSize][x / tileSize];
    }

    public int getTileSize(){
        return tileSize;
    }


}