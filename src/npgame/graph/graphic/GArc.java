/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
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
    public void paint(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(3));
        g.drawLine(source.getX(), source.getY(), destination.getX(), destination.getY());
        double m = (double) (source.getY() - destination.getY()) / (source.getX() - destination.getX());
        double r = (double) GNode.DEFAULT_SIZE / 2;
        g.setColor(Color.BLACK);
        double x1 = -Math.sqrt(r * r / (m * m + 1));
        double x2 = +Math.sqrt(r * r / (m * m + 1));
        double y1 = m * x1;
        double y2 = m * x2;
        x1 += destination.getX();
        x2 += destination.getX();
        y1 += destination.getY();
        y2 += destination.getY();
        if (Math.pow(source.getX() - x1, 2) + Math.pow(source.getY() - y1, 2) < Math.pow(source.getX() - x2, 2) + Math.pow(source.getY() - y2, 2)) {
            g.fillOval((int) (x1 - 7), (int) (y1 - 7), 15, 15);
        } else {
            g.fillOval((int) (x2 - 7), (int) (y2 - 7), 15, 15);
        }
    }

    @Override
    public boolean hits(int x, int y) {
        return false;
    }

}
