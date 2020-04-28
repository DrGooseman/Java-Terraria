package com.bbs;

import javax.swing.*;
import java.awt.*;

public class Player  extends JPanel {

    private float posX;
    private float posY;

    private int moveSpeed = 3;

    private int playerWidth = 40;//28;
    private int playerHeight = 72;//42;
    private int playerWidthHalf = playerWidth/2;
    private int playerHeightHalf = playerHeight/2;

    private boolean isLeftPressed = false;
    private boolean isUpPressed = false;
    private boolean isRightPressed = false;
    private boolean isDownPressed = false;
    private boolean isSpacePressed = false;

    private boolean falling = false;
    private float fallSpeed = 0;
    private float fallAcceleration = .24f;
    private float jumpPower = 10;

    private World world;
    private Tile[][] worldTiles;

    public Player (int posX, int posY, World world){
        this.posX = posX;
        this.posY = posY;
        this.world = world;
        System.out.println("player y " + posY);
    }

@Override
    public void paintComponent(Graphics g) {
       //super.paintComponent(g);
        //draw player imageicon
//        playerIcon = new ImageIcon("src/images/playerIcon.png");
//        playerIcon.paintIcon(this, g, worldWidth, worldHeight);
//System.out.println("paint player");
       g.setColor(Color.BLACK);
        g.fillRect(Game.screenWidthCenter - playerWidth/2, Game.screenHeightCenter - playerHeight/2, playerWidth, playerHeight);


    }

    public void press(char ch, boolean isPressed){
        if (ch == 'a')
            isLeftPressed = isPressed;
        else if (ch == 's')
            isDownPressed = isPressed;
        else if (ch == 'w')
            isUpPressed = isPressed;
        else if (ch == 'd')
            isRightPressed = isPressed;
        else if (ch == 'd')
            isRightPressed = isPressed;
        else if (ch == ' ')
            isSpacePressed = isPressed;
    }

    public void updateState(double delta) {
        if (isLeftPressed) {
            posX -= delta * moveSpeed;
        }
        if (isRightPressed) {
            posX += delta * moveSpeed;
        }
        if (isSpacePressed && !falling) {
            posY -= 2;
            fallSpeed -= jumpPower;
            isSpacePressed = false;
            falling = true;
        }

        calcPhysics(delta);
    }

    public void calcPhysics(double delta){
//        world.getTileAtIndex(posX-playerWidthHalf, posY-playerHeightHalf).setHighlighted(true);
//        world.getTileAtIndex(posX+playerWidthHalf, posY-playerHeightHalf).setHighlighted(true);
//        world.getTileAtIndex(posX-playerWidthHalf, posY+playerHeightHalf).setHighlighted(true);
//        world.getTileAtIndex(posX+playerWidthHalf, posY+playerHeightHalf).setHighlighted(true);

        if (falling) {
            if (isOverSolidGround()) {
                falling = false;
                fallSpeed = 0;
                posY -= getGroundOverlap();
            } else {
                falling = true;
                //fallSpeed = .1f;
            }
        }else{
            if (!isOverSolidGround()) {
                falling = true;
            }
        }

        if (falling){
            fallSpeed += delta * fallAcceleration;
            posY += delta * fallSpeed;
        }

    }

    private float getGroundOverlap() {
      //  System.out.println((posY+playerHeightHalf) - world.getTileAtIndex((int)posX-playerWidthHalf, (int)posY+playerHeightHalf).getTopPosition());
        //System.out.println((posY+playerHeightHalf) - (int)(posY+playerHeightHalf));
        return (posY+playerHeightHalf) - world.getTileAtIndex((int)posX-playerWidthHalf, (int)posY+playerHeightHalf).getTopPosition();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    private boolean isOverSolidGround(){
        return (world.getTileAtIndex((int)posX-playerWidthHalf, (int)posY+playerHeightHalf ).getTileType() != 0
                || world.getTileAtIndex((int)posX+playerWidthHalf, (int)posY+playerHeightHalf ).getTileType() != 0);
    }
}
