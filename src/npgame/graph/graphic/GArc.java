/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
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

    private int arrow;

    public GArc(Arc arc, GNode source, GNode destination) {
        this.arc = arc;
        this.source = source;
        this.destination = destination;
        this.arrow = 0;
        line = new LagrangeLine();
        line.addPoint(new Point(source.getX(), source.getY()));
        line.addPoint(new Point(noise((2 * source.getX() + destination.getX()) / 3), noise((2 * source.getY() + destination.getY()) / 3)));
        line.addPoint(new Point(noise((source.getX() + 2 * destination.getX()) / 3), noise((source.getY() + 2 * destination.getY()) / 3)));
        line.addPoint(new Point(destination.getX(), destination.getY()));
    }

    private double noise(double val) {
        return (Math.random() * 0.1 + 0.95) * val;
    }

    @Override
    public void paint(NPPanel parent, final Graphics2D g) {
        //paintCurveArc(parent, g);
        AffineTransform old = g.getTransform();
        paintStraightArc(parent, g);

        if (arc.isVisited()) {
            arrow = (arrow + 1) % line.getPointsNumber();

            g.translate(parent.gridX2Px(line.getPoint(arrow).x),
                    parent.gridY2Px(line.getPoint(arrow).y));
            g.rotate(Math.PI / 2 + Math.atan2(
                    line.getDerivative(arrow).y,
                    line.getDerivative(arrow).x));

            g.setColor(Color.RED);
            g.fillPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
            g.setColor(Color.YELLOW);
            g.drawPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
        }
        g.setTransform(old);
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

    private void paintCurveArc(NPPanel parent, Graphics2D g) {
        int x[] = new int[line.getPointsNumber()];
        int y[] = new int[line.getPointsNumber()];
        for (int i = 0; i < line.getPointsNumber(); i++) {
            x[i] = (int) parent.gridX2Px(line.getPoint(i).x);
            y[i] = (int) parent.gridY2Px(line.getPoint(i).y);
        }
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(6));
        g.drawPolyline(x, y, line.getPointsNumber());
        g.setColor(Color.ORANGE);
        g.setStroke(new BasicStroke(2));
        g.drawPolyline(x, y, line.getPointsNumber());
        for (int i = 20; i < line.getPointsNumber() - 20; i += 20) {
            AffineTransform old = g.getTransform();

            g.translate(parent.gridX2Px(line.getPoint(i).x),
                    parent.gridY2Px(line.getPoint(i).y));
            g.rotate(Math.PI / 2 + Math.atan2(
                    line.getDerivative(i).y,
                    line.getDerivative(i).x));

            g.setColor(Color.CYAN);
            g.fillPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
            g.setColor(Color.BLUE);
            g.drawPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
            g.setTransform(old);

        }
    }

    private void paintStraightArc(NPPanel parent, Graphics2D g) {
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(6));
        g.drawLine((int) parent.gridX2Px(source.getX()), (int) parent.gridY2Px(source.getY()),
                (int) parent.gridX2Px(destination.getX()), (int) parent.gridY2Px(destination.getY()));
        g.setColor(Color.ORANGE);
        g.setStroke(new BasicStroke(2));
        g.drawLine((int) parent.gridX2Px(source.getX()), (int) parent.gridY2Px(source.getY()),
                (int) parent.gridX2Px(destination.getX()), (int) parent.gridY2Px(destination.getY()));
        AffineTransform old = g.getTransform();

        g.translate(parent.gridX2Px(destination.getX()) / 2 + parent.gridX2Px(source.getX()) / 2,
                parent.gridY2Px(destination.getY()) / 2 + parent.gridY2Px(source.getY()) / 2);
        g.rotate(Math.PI / 2 + Math.atan2(
                parent.gridY2Px(destination.getY()) - parent.gridY2Px(source.getY()),
                parent.gridX2Px(destination.getX()) - parent.gridX2Px(source.getX())));

        g.setColor(Color.CYAN);
        g.fillPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
        g.setColor(Color.BLUE);
        g.drawPolygon(new int[]{0, 10, 10, 0, -10, -10, 0}, new int[]{0, 10, 20, 10, 20, 10, 0}, 7);
        g.setTransform(old);

    }

}
