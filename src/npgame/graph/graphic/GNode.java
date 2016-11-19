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
import npgame.NPPanel;
import npgame.graph.Node;

/**
 *
 * @author Lorenzo
 */
public class GNode implements Drawable {

    public static final int DEFAULT_SIZE = 25;

    private GGraph graph;
    private Node node;

    private int x;
    private int y;

    private int size;

    private boolean highlighted;

    public GNode(Node node, int x, int y, int size, GGraph graph) {
        this.node = node;
        this.x = x;
        this.y = y;
        this.size = size;
        this.graph = graph;
    }

    @Override
    public void paint(NPPanel parent, Graphics2D g) {
        if (node.isVisited()) {
            g.setColor(Color.RED);
            int realX = parent.getRealX(x);
            int realY = parent.getRealY(y);
            g.translate(realX, realY);
            g.fillOval(-size / 2, -size / 2, size, size);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.BLUE);
            g.drawOval(-size / 2, -size / 2, size, size);

            g.translate(-realX, -realY);

        } else if (isHighlighted()) {
            g.setColor(Color.GRAY.brighter());
            int realX = parent.getRealX(x);
            int realY = parent.getRealY(y);
            g.translate(realX, realY);
            g.fillOval(-size / 2, -size / 2, size, size);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.GRAY.darker());
            g.drawOval(-size / 2, -size / 2, size, size);

            g.translate(-realX, -realY);
        } else {
            g.setColor(Color.GRAY.darker());
            int realX = parent.getRealX(x);
            int realY = parent.getRealY(y);
            g.translate(realX, realY);
            g.fillOval(-size / 2, -size / 2, size, size);
            g.setStroke(new BasicStroke(5));
            g.setColor(Color.GRAY.brighter());
            g.drawOval(-size / 2, -size / 2, size, size);

            g.translate(-realX, -realY);
        }
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public boolean hits(NPPanel parent, int x, int y) {
        return (x - parent.getRealX(this.x)) * (x - parent.getRealX(this.x)) + (y - parent.getRealY(this.y)) * (y - parent.getRealY(this.y)) <= size * size / 4;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

}
