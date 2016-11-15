/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

/**
 *
 * @author Lorenzo
 */
public class GArc implements Drawable {

    private GNode source;
    private GNode destination;

    public GArc(GNode source, GNode destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void paint(Component c, Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(3));
        
        int realSX = source.getRealX(c);
        int realSY = source.getRealY(c);
        int realDX = destination.getRealX(c);
        int realDY = destination.getRealY(c);
        g.setStroke(new BasicStroke(10));
        g.drawLine(realSX, realSY, realDX, realDY);
        g.setStroke(new BasicStroke(4));
        g.setColor(Color.PINK);
        g.drawLine(realSX, realSY, realDX, realDY);
        double m = (double) (realSY - realDY) / (realSX - realDX);
        double r = (double) GNode.DEFAULT_SIZE / 2;
        g.setColor(Color.BLACK);
        double x1 = -Math.sqrt(r * r / (m * m + 1));
        double x2 = +Math.sqrt(r * r / (m * m + 1));
        double y1 = m * x1;
        double y2 = m * x2;
        x1 += realDX;
        x2 += realDX;
        y1 += realDY;
        y2 += realDY;
        if (Math.pow(realSX - x1, 2) + Math.pow(realSY - y1, 2) < Math.pow(realSX - x2, 2) + Math.pow(realSY - y2, 2)) {
            g.fillOval((int) (x1 - 7), (int) (y1 - 7), 15, 15);
        } else {
            g.fillOval((int) (x2 - 7), (int) (y2 - 7), 15, 15);
        }
    }

    @Override
    public boolean hits(Component c, int x, int y) {
        return false;
    }

}
