package main;

import mino.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayManager {

    // Main Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    // Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>(); // add inactive mino

    // Others
    public static int dropInterval = 60; // Mino drops in every 60 frames
    boolean gameOver;

    // Effect Delete
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    public PlayManager() {
        // Main play Area Frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        // Mino initialisation
        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;

        // Set the starting Mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);
    }

    // Picking up a random tetromino
    private Mino pickMino() {
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0 -> mino = new Mino_L1();
            case 1 -> mino = new Mino_L2();
            case 2 -> mino = new Mino_Square();
            case 3 -> mino = new Mino_Bar();
            case 4 -> mino = new Mino_T();
            case 5 -> mino = new Mino_Z1();
            case 6 -> mino = new Mino_Z2();
        }
        return mino;
    }

    public void update() {
        // Check if the currentMino is active
        if (!this.currentMino.active) {
            // put it into the static Blocks
            staticBlocks.add(this.currentMino.b[0]);
            staticBlocks.add(this.currentMino.b[1]);
            staticBlocks.add(this.currentMino.b[2]);
            staticBlocks.add(this.currentMino.b[3]);

            // Check if the game is over
            if (this.currentMino.b[0].x == MINO_START_X && this.currentMino.b[0].y == MINO_START_Y) {
                // this means the current Mino immediately collided a block and couldn't move at all
                // So it's xy are the same with the nextMino's
                gameOver = true;
            }

            this.currentMino.deactivating = false;

            // replace the current Mino with the next Mino
            this.currentMino = this.nextMino;
            this.currentMino.setXY(MINO_START_X,MINO_START_Y);
            this.nextMino = this.pickMino();
            this.nextMino.setXY(NEXTMINO_X,NEXTMINO_Y);

            // When a mino becomes inactive, check if line(s) can be deleted
            this.checkDelete();
        } else {
            this.currentMino.update();
        }
    }

    private void checkDelete() {
        int x = left_x;
        int y = top_y;
        int blockCount = 0;

        while (x < right_x && y < bottom_y) {

            for (Block staticBlock : staticBlocks) {
                if (staticBlock.x == x && staticBlock.y == y) {
                    // increase the count if there is a static block
                    blockCount++;
                }
            }

            x += Block.SIZE;

            if (x == right_x) {

                // if the blockCount hits 12, that means the current y line is all filled with blocks,
                // so we can delete them
                if (blockCount == 12) {

                    this.effectCounterOn = true;
                    effectY.add(y);

                    for (int i = staticBlocks.size() - 1; i > -1; i--) {
                        // remove all the blocks in the current y line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    // a line has been deleted so need to slide down blocks that are above it
                    for (Block staticBlock : staticBlocks) {
                        // if a block is above the current y, move it down by the block size
                        if (staticBlock.y < y) {
                            staticBlock.y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }
    }

    public void draw(Graphics2D g2) {
        // Draw play Area Frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

        // Draw Next Mino Frame
        int x = right_x + 100;
        int y = bottom_y -200;
        g2.drawRect(x,y,200,200);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("NEXT", x+60, y+60);

        //Draw the currentMino
        if (this.currentMino != null) {
            currentMino.draw(g2);
        }

        // Draw next mino
        nextMino.draw(g2);

        // Draw Static Blocks
        for (Block staticBlock : staticBlocks) {
            staticBlock.draw(g2);
        }

        // Draw Effect
        if (this.effectCounterOn) {
            this.effectCounter++;

            g2.setColor(Color.black);
            for (Integer integer : effectY) {
                g2.fillRect(left_x, integer, WIDTH, Block.SIZE);
            }

            if (this.effectCounter == 10) {
                this.effectCounterOn = false;
                this.effectCounter = 0;
                this.effectY.clear();
            }
        }

        // Draw Pause and Game Over
        g2.setColor(Color.YELLOW);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x,y);
        }
        else if (KeyHandler.pausePressed) {
            x = left_x + 70;
            y = top_y + 320;
            g2.drawString("PAUSED",x,y);
        }
    }
}
