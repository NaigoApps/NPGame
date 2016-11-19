/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

/**
 *
 * @author Lorenzo
 */
public interface Line{

    public void addPoint(Point p);
    
    public Point getPoint(int i);

    public int getPointsNumber();
    
    public void printNodes();
}
