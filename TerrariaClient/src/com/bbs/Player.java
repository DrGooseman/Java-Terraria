package com.bbs;

import javax.swing.*;
import java.awt.*;

public class Player extends JPanel {

    private float posX;
    private float posY;

    private int moveSpeed = 3;

    private int playerWidth = 40;//28;
    private int playerHeight = 72;//42;
    private int playerWidthHalf = playerWidth / 2;
    private int playerHeightHalf = playerHeight / 2;

    private boolean isLeftPressed = false;
    private boolean isUpPressed = false;
    private boolean isRightPressed = false;
    private boolean isDownPressed = false;
    private boolean isSpacePressed = false;

    private boolean falling = false;
    private float fallSpeed = 0;
    private float fallAcceleration = .24f;
    private float jumpPower = 10;

    private float attackCooldown = .2f;
    private float attackCooldownTimer = 0;
    private boolean isMouseDown = false;
    private Point mouseLocation;

    private World world;
    private Tile[][] worldTiles;


    public Player(int posX, int posY, World world) {
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
        g.fillRect(Game.screenWidthCenter - playerWidth / 2, Game.screenHeightCenter - playerHeight / 2, playerWidth, playerHeight);
        if (attackCooldownTimer > 0) {
            g.setColor(Color.RED);
            g.fillRect(Game.screenWidthCenter - playerWidth / 2, Game.screenHeightCenter - playerHeight / 2 - 4, (int) (playerWidth * (attackCooldownTimer / attackCooldown)), 4);
        }

    }

    public void press(char ch, boolean isPressed) {
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

        if (attackCooldownTimer > 0) {
            attackCooldownTimer -= delta * .01;
        }

        if (isMouseDown && attackCooldownTimer <= 0) {
            Point worldPosition = new Point((int) (mouseLocation.x - Game.screenWidthCenter + posX), (int)(mouseLocation.y - Game.screenHeightCenter + posY));
            attack(worldPosition);
        }

    }

    public void calcPhysics(double delta) {
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
        } else {
            if (!isOverSolidGround()) {
                falling = true;
            }
        }

        if (falling) {
            fallSpeed += delta * fallAcceleration;
            posY += delta * fallSpeed;
        }

    }

    private float getGroundOverlap() {
        //  System.out.println((posY+playerHeightHalf) - world.getTileAtIndex((int)posX-playerWidthHalf, (int)posY+playerHeightHalf).getTopPosition());
        //System.out.println((posY+playerHeightHalf) - (int)(posY+playerHeightHalf));
        return (posY + playerHeightHalf) - world.getTileAtIndex((int) posX - playerWidthHalf, (int) posY + playerHeightHalf).getTopPosition();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    private boolean isOverSolidGround() {
        return (world.getTileAtIndex((int) posX - playerWidthHalf, (int) posY + playerHeightHalf).getTileType() != 0
                || world.getTileAtIndex((int) posX + playerWidthHalf, (int) posY + playerHeightHalf).getTileType() != 0);
    }

    //For now, the player only has a pickaxe. Update later to use other items.
    public void attack(Point point) {

        world.getTileAtIndex(point.x, point.y).hit(1);

        attackCooldownTimer += attackCooldown;
    }

    public void mouseClicked(boolean isMouseDown) {
        this.isMouseDown = isMouseDown;
    }

    public void mouseMoved(Point mouseLocation) {
        this.mouseLocation = mouseLocation;
    }
}
