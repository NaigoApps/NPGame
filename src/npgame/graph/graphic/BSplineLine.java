/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.util.ArrayList;

/**
 *
 * @author Lorenzo
 */
public class BSplineLine implements Line {
    
    public static final int P = 1;
    private ArrayList<Point> nodes;
    private ArrayList<Point> points;
    private ArrayList<Point> derivative;
    
    public BSplineLine() {
        nodes = new ArrayList<>();
        points = new ArrayList<>();
        derivative = new ArrayList<>();
    }
    
    public void addNode(int i, Point p) {
        nodes.add(i, p);
        buildCurve();
    }
    
    @Override
    public void addNode(Point p) {
        nodes.add(p);
        buildCurve();
    }
    
    public Point getNode(int i) {
        return nodes.get(i);
    }
    
    @Override
    public Point getPoint(int i) {
        return points.get(i);
    }
    
    public Point getDerivative(int i) {
        return derivative.get(i);
    }
    
    @Override
    public int getPointsNumber() {
        return points.size();
    }
    
    public int getNodesNumber() {
        return nodes.size();
    }
    
    private void buildCurve() {
        points.clear();
        derivative.clear();
        for (double x = 0; x <= nodes.size(); x += 0.05) {
            double x1 = 0;
            double x2 = 0;
            for (int i = -P; i < nodes.size(); i++) {
                x1 += nodes.get(i).x * b(P, i, x);
                x2 += nodes.get(i).y * b(P, i, x);
            }
            points.add(new Point(x1, x2));
        }
    }
    
    private double b(int p, int i, double x) {
        if (p == 0) {
            if (x >= i && x < i + 1) {
                return 1;
            }
            return 0;
        }
        return (x - i) / p * b(p - 1, i, x) + (i + p + 1 - x) / p * b(p - 1, i + 1, x);
    }
    
    @Override
    public void printNodes() {
        System.out.println("Points:");
        for (Point point : nodes) {
            System.out.println("(" + point.x + ";" + point.y + ")");
        }
    }
}
