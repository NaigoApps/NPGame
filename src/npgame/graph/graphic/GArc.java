/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import npgame.NPPanel;
import npgame.ThreadRenderer;
import npgame.graph.Arc;

/**
 *
 * @author Lorenzo
 */
public class GArc implements Drawable {

    private GNode source;
    private GNode destination;
    private LagrangeLine line;

    private Arc arc;

    public GArc(Arc arc, GNode source, GNode destination) {
        this.arc = arc;
        this.source = source;
        this.destination = destination;
        line = new LagrangeLine();
        line.addPoint(new Point(source.getX(), source.getY()));
        line.addPoint(new Point(noise((2 * source.getX() + destination.getX()) / 3), noise((2 * source.getY() + destination.getY()) / 3)));
        line.addPoint(new Point(noise((source.getX() + 2 * destination.getX()) / 3), noise((source.getY() + 2 * destination.getY()) / 3)));
        line.addPoint(new Point(destination.getX(), destination.getY()));
    }

    private double noise(double val) {
        return (Math.random() * 0.2 + 0.9) * val;
    }

    @Override
    public void paint(NPPanel parent, Graphics2D g) {
        g.setColor(Color.RED);
        int x[] = new int[line.getPointsNumber()];
        int y[] = new int[line.getPointsNumber()];
        for (int i = 0; i < line.getPointsNumber(); i++) {
            x[i] = parent.getRealX(line.getPoint(i).x);
            y[i] = parent.getRealY(line.getPoint(i).y);
        }
        g.drawPolyline(x,y,line.getPointsNumber());
        
        if (arc.isVisited()) {
            int arrow = (int) (ThreadRenderer.rendererCounter() % line.getPointsNumber());

            AffineTransform old = g.getTransform();
            g.translate(parent.getRealX(line.getPoint(arrow).x),
                    parent.getRealY(line.getPoint(arrow).y));
            g.rotate(Math.PI / 2 + Math.atan2(
                    line.getDerivative(arrow).y,
                    line.getDerivative(arrow).x));
            //g.rotate(Math.PI / 3);
            g.setColor(Color.CYAN);
            
            g.fillPolygon(new int[]{0,10,10,0,-10,-10,0}, new int[]{0,10,20,10,20,10,0}, 7);
            g.setColor(Color.BLUE);
            g.drawPolygon(new int[]{0,10,10,0,-10,-10,0}, new int[]{0,10,20,10,20,10,0}, 7);
            
            g.setTransform(old);

        }
    }

    @Override
    public boolean hits(NPPanel c, int x, int y) {
        return false;
    }

    public Arc getArc() {
        return arc;
    }

    public GNode getDestination() {
        return destination;
    }

    public GNode getSource() {
        return source;
    }

}
