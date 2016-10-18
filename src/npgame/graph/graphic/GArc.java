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
public class GArc implements Drawable{
    private GNode source;
    private GNode destination;

    public GArc(GNode source, GNode destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(5));
        g.drawLine(source.getX(), source.getY(), destination.getX(), destination.getY());
    }

    @Override
    public boolean hits(int x, int y) {
        return false;
    }
    
    
}
