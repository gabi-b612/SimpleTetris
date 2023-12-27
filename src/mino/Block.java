package mino;

import java.awt.*;

public class Block extends Rectangle {
    public int x,y;
    public static final int SIZE = 30; // block 30 x 30
    public Color c;
    int margin = 2;

    public Block(Color c) {
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(c);
        g2.fillRect(x + this.margin,y+ this.margin,SIZE - (this.margin * 2),SIZE - (this.margin * 2));
    }

}
