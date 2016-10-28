/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.Component;
import java.awt.Graphics2D;

/**
 *
 * @author Lorenzo
 */
public interface Drawable {
    public void paint(Component parent, Graphics2D g);
    public boolean hits(Component parent, int x, int y);
}
