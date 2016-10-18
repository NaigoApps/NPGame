/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import npgame.graph.Node;

/**
 *
 * @author Lorenzo
 */
public class GNode implements Drawable{

    public static final int DEFAULT_SIZE = 50;

    private Node node;
    
    private int x;
    private int y;
    
    private int size;

    public GNode(Node node, int x, int y, int size) {
        this.node = node;
        this.x = x;
        this.y = y;
        this.size = size;
    }
    
    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.RED);
        g.fillOval(x - size/2, y - size/2, size, size);
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLUE);
        g.drawOval(x - size/2, y - size/2, size, size);
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
    public boolean hits(int x, int y) {
        return (x - getX())*(x - getX()) + (y - getY())*(y - getY()) <= size*size/4;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    
    
}
