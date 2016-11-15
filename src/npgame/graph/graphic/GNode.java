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
public class GNode implements Drawable{

    public static final int DEFAULT_SIZE = 25;

    private GGraph graph;
    private Node node;
    
    private int x;
    private int y;
    
    private int size;

    public GNode(Node node, int x, int y, int size, GGraph graph) {
        this.node = node;
        this.x = x;
        this.y = y;
        this.size = size;
        this.graph = graph;
    }
    
    @Override
    public void paint(Component parent, Graphics2D g) {
        g.setColor(Color.RED);
        int realX = getRealX(parent);
        int realY = getRealY(parent);
        g.fillOval(realX - size/2, realY - size/2, size, size);
        g.setStroke(new BasicStroke(5));
        g.setColor(Color.BLUE);
        g.drawOval(realX - size/2, realY - size/2, size, size);
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
    
    int getRealX(Component parent){
        return x * (parent.getWidth() - 2*graph.getGridMargin()) / graph.getGridWidth() + graph.getGridMargin();
    }

    int getRealY(Component parent){
        return y * (parent.getHeight()- 2*graph.getGridMargin()) / graph.getGridHeight()+ graph.getGridMargin();
    }
    
    public Node getNode() {
        return node;
    }

    @Override
    public boolean hits(Component c, int x, int y) {
        return (x - getRealX(c))*(x - getRealX(c)) + (y - getRealY(c))*(y - getRealY(c)) <= size*size/4;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int toGridX(Component parent, int i) {
        return (i - graph.getGridMargin())*graph.getGridWidth()/(parent.getWidth() - 2*graph.getGridMargin());
    }

    public int toGridY(Component parent, int i) {
        return (i - graph.getGridMargin())*graph.getGridHeight()/(parent.getHeight()- 2*graph.getGridMargin());
    }

    
    
}
