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

    public void addNode(Point p);
    public void addNode(int i, Point p);
    
    public Point getNode(int i);
    public Point getPoint(int i);

    public int getNodesNumber();
    public int getPointsNumber();
    
    public void printNodes();
}
