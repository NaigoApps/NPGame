/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.Graphics2D;

/**
 *
 * @author Lorenzo
 */
public interface Drawable {
    public void paint(Graphics2D g);
    public boolean hits(int x, int y);
}
