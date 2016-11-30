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
public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point div(double d) {
        return new Point(x / d, y / d);
    }

    Point avg(Point p2) {
        return new Point((x + p2.x)/2, (y + p2.y)/2);
    }
    
}
