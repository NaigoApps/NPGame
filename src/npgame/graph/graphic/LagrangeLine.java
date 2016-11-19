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
public class LagrangeLine implements Line {

    private ArrayList<Point> nodes;
    private ArrayList<Point> points;
    private ArrayList<Point> derivative;

    public LagrangeLine() {
        nodes = new ArrayList<>();
        points = new ArrayList<>();
        derivative = new ArrayList<>();
    }

    @Override
    public void addPoint(Point p) {
        nodes.add(p);
        buildCurve();
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

    private void buildCurve() {
        points.clear();
        derivative.clear();
        for (double x = 0; x <= nodes.size() - 1; x += 0.05) {
            double x1 = 0;
            double x2 = 0;
            for (int i = 0; i < nodes.size(); i++) {
                x1 += nodes.get(i).x * l(i, x);
                x2 += nodes.get(i).y * l(i, x);
            }
            double xd1 = 0;
            double xd2 = 0;
            for (int i = 0; i < nodes.size(); i++) {
                xd1 += nodes.get(i).x * ld(i, x);
                xd2 += nodes.get(i).y * ld(i, x);
            }
            points.add(new Point(x1, x2));
            derivative.add(new Point(xd1, xd2));
        }
    }

    private double l(int i, double x) {
        int n = nodes.size() - 1;
        double prod = 1;
        for (int j = 0; j <= n; j++) {
            if (i != j) {
                prod *= (x - j) / (i - j);
            }
        }
        return prod;
    }

    private double ld(int j, double x) {
        int n = nodes.size() - 1;
        double sum = 0;
        for (int m = 0; m <= n; m++) {
            if (j != m) {
                double prod = 1;
                for (int l = 0; l <= n; l++) {
                    if(l != m && l != j){
                        prod *= (x - l);
                    }
                }
                sum += prod;
            }
        }
        double prod = 1;
        for (int m = 0; m <= n; m++) {
            if (j != m) {
                prod *= (j - m);
            }
        }
        return sum / prod;
    }

    @Override
    public void printNodes() {
        System.out.println("Points:");
        for (Point point : nodes) {
            System.out.println("(" + point.x + ";" + point.y + ")");
        }
    }
}
