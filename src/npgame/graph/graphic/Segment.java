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
public class Segment implements Line {

    private Point p1;
    private Point p2;

    public Segment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void addNode(Point p) {
        if (p1 == null) {
            p1 = p;
        } else if (p2 == null) {
            p2 = p;
        }
    }

    @Override
    public Point getPoint(int i) {
        if (i == 0) {
            return p1;
        } else if (i == 1) {
            return p2;
        }
        return null;
    }

    @Override
    public int getPointsNumber() {
        if (p1 == null) {
            return 0;
        } else {
            if (p2 == null) {
                return 1;
            } else {
                return 2;
            }
        }
    }

    @Override
    public void printNodes() {

    }

    @Override
    public void addNode(int i, Point p) {
        addNode(p);
    }

    @Override
    public int getNodesNumber() {
        return getPointsNumber();
    }

    @Override
    public Point getNode(int i) {
        return getPoint(i);
    }

}
