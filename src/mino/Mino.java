package mino;


import main.KeyHandler;
import main.PlayManager;

import java.awt.*;

public class Mino {

    public Block[] b = new Block[4];
    public Block[] tempB = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // There are 4 directions (1/2/3/4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;


    public void create(Color c) {
        this.b[0] = new Block(c);
        this.b[1] = new Block(c);
        this.b[2] = new Block(c);
        this.b[3] = new Block(c);
        this.tempB[0] = new Block(c);
        this.tempB[1] = new Block(c);
        this.tempB[2] = new Block(c);
        this.tempB[3] = new Block(c);
    }

    public void setXY(int x, int y) {}

    public void updateXY (int direction) {
        this.checkRotationCollision();
        if (!leftCollision && !rightCollision  && !bottomCollision) {
            this.direction = direction;
            this.b[0].x = tempB[0].x;
            this.b[0].y = tempB[0].y;

            this.b[1].x = tempB[1].x;
            this.b[1].y = tempB[1].y;

            this.b[2].x = tempB[2].x;
            this.b[2].y = tempB[2].y;

            this.b[3].x = tempB[3].x;
            this.b[3].y = tempB[3].y;
        }
    }

    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}

    public void checkMovementCollision() {
        this.leftCollision = false;
        this.rightCollision = false;
        this.bottomCollision = false;

        // Check frame collision
        // Left wall
        for (Block block : b) {
            if (block.x == PlayManager.left_x) {
                this.leftCollision = true;
                break;
            }
        }
        // Right wall
        for (Block block : b) {
            if (block.x + Block.SIZE == PlayManager.right_x) {
                this.rightCollision = true;
                break;
            }
        }

        // Bottom floor
        for (Block block:b) {
            if (block.y + Block.SIZE == PlayManager.bottom_y) {
                this.bottomCollision = true;
                break;
            }
        }
    }
    public void checkRotationCollision() {
        this.leftCollision = false;
        this.rightCollision = false;
        this.bottomCollision = false;

        // Check frame collision
        // Left wall
        for (Block block : tempB) {
            if (block.x < PlayManager.left_x) {
                this.leftCollision = true;
                break;
            }
        }
        // Right wall
        for (Block block : tempB) {
            if (block.x + Block.SIZE > PlayManager.right_x) {
                this.rightCollision = true;
                break;
            }
        }

        // Bottom floor
        for (Block block: tempB) {
            if (block.y + Block.SIZE > PlayManager.bottom_y) {
                this.bottomCollision = true;
                break;
            }
        }
    }

    public void update() {

        // Move the mino
        if (KeyHandler.upPressed) {
            switch (this.direction) {
                case 2 -> getDirection1();
                case 3 -> getDirection2();
                case 4 -> getDirection3();
                case 1 -> getDirection4();
            }
            KeyHandler.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandler.downPressed) {
            if (!this.bottomCollision) {
                this.b[0].y += Block.SIZE;
                this.b[1].y += Block.SIZE;
                this.b[2].y += Block.SIZE;
                this.b[3].y += Block.SIZE;

                // When moved down, reset the autoDropCounter
                this.autoDropCounter = 0;
            }
            KeyHandler.downPressed = false;
        }

        if (KeyHandler.leftPressed) {
            if (!this.leftCollision) {
                this.b[0].x -= Block.SIZE;
                this.b[1].x -= Block.SIZE;
                this.b[2].x -= Block.SIZE;
                this.b[3].x -= Block.SIZE;
            }
            KeyHandler.leftPressed = false;
        }

        if (KeyHandler.rightPressed) {
            if (!this.rightCollision) {
                b[0].x += Block.SIZE;
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }
            KeyHandler.rightPressed = false;
        }

        if (this.bottomCollision) {
            this.active = false;
        } else {
            this.autoDropCounter++; // the counter increases in every frame
            if (this.autoDropCounter == PlayManager.dropInterval) {
                // the mino goes down
                this.b[0].y += Block.SIZE;
                this.b[1].y += Block.SIZE;
                this.b[2].y += Block.SIZE;
                this.b[3].y += Block.SIZE;
                this.autoDropCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
        g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
    }
}
