/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.util.ArrayList;
import npgame.Matrix;

/**
 *
 * @author Lorenzo
 */
public class BSplineLine implements Line {

    public static final int P = 3;
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
    }
    
    public void makeCurve(){
        buildCurve();
    }

    @Override
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

    @Override
    public int getNodesNumber() {
        return nodes.size();
    }

    private void buildCurve() {
        if (nodes.size() > 3) {
            points.clear();
            derivative.clear();
            Matrix mSpline = new Matrix(nodes.size() + 2, nodes.size() + 2);
            for (int i = 0; i < mSpline.rows() - 2; i++) {
                for (int j = 0; j < mSpline.columns(); j++) {
                    mSpline.set(i, j, b(3, j - 3, i));
                }
            }
            for (int j = 0; j < mSpline.columns(); j++) {
                mSpline.set(mSpline.rows() - 2, j, b2(3, j - 3, 0));
                mSpline.set(mSpline.rows() - 1, j, b2(3, j - 3, nodes.size() - 1));
            }
            Matrix mDataX = new Matrix(nodes.size() + 2);
            Matrix mDataY = new Matrix(nodes.size() + 2);
            for (int i = 0; i < mDataX.rows() - 2; i++) {
                mDataX.set(i, 0, nodes.get(i).x);
                mDataY.set(i, 0, nodes.get(i).y);
            }
            for (int i = mDataX.rows() - 2; i < mDataX.rows(); i++) {
                mDataX.set(i, 0, 0);
                mDataY.set(i, 0, 0);
            }
            Matrix paramsX = Matrix.solve(mSpline, mDataX);
            Matrix paramsY = Matrix.solve(mSpline, mDataY);
            for (double x = 0; x <= nodes.size(); x += 0.05) {
                double x1 = 0;
                double x2 = 0;
                for (int i = 0; i < nodes.size(); i++) {
                    x1 += paramsX.get(i) * b(3, i - 3, x);
                    x2 += paramsY.get(i) * b(3, i - 3, x);

                }
                points.add(new Point(x1, x2));
            }
        }
    }

    private double b(int p, int i, double x) {
        if (p == 0) {
            if (x > i && x <= i + 1) {
                return 1;
            }
            return 0;
        }
        if (p == 1) {
            if (x > i && x <= i + 1) {
                return (x - i);
            } else if (x > i + 1 && x < i + 2) {
                return (i + 2 - x);
            }
            return 0;
        }
        return (x - i) / p * b(p - 1, i, x) + (i + p + 1 - x) / p * b(p - 1, i + 1, x);
    }

    private double b1(int p, int i, double x) {
        if (p == 1) {
            return b(0, i, x) - b(0, i + 1, x);
        }
        return b(p - 1, i, x) / p + (x - i) / p * b1(p - 1, i, x) - b(p - 1, i + 1, x) / p + (i + p + 1 - x) / p * b1(p - 1, i + 1, x);
    }

    private double b2(int p, int i, double x) {
        if (p == 2) {
            return b1(1, i, x) - b1(1, i + 1, x);
        } else {
            return 2 * b1(p - 1, i, x) / p + (x - i) / p * b2(p - 1, i, x)
                    - 2 * b1(p - 1, i + 1, x) / p + (i + p + 1 - x) / p * b2(p - 1, i + 1, x);
        }
    }

    @Override
    public void printNodes() {
        System.out.println("Points:");
        for (Point point : nodes) {
            System.out.println("(" + point.x + ";" + point.y + ")");
        }
    }
}
