/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import npgame.NPPanel;
import npgame.graph.Node;

/**
 *
 * @author Lorenzo
 */
public class GNode implements Drawable {

    public static final int DEFAULT_SIZE = 50;

    private GGraph graph;
    private Node node;

    private int x;
    private int y;

    private int size;

    private boolean highlighted;

    public GNode(Node node, int x, int y, int size, GGraph graph) {
        this.node = node;
        this.x = x;
        this.y = y;
        this.size = size;
        this.graph = graph;
    }

    @Override
    public void paint(NPPanel parent, Graphics2D g) {
        Color inColor, outColor;
        if (node.isVisited()) {
            outColor = Color.RED;
            inColor = Color.BLUE;
        } else if (isHighlighted()) {
            outColor = Color.GRAY.brighter();
            inColor = Color.GRAY.darker();
        } else {
            outColor = Color.GRAY.darker();
            inColor = Color.GRAY.brighter();
        }
        double xPx = parent.gridX2Px(x);
        double yPx = parent.gridY2Px(y);
        ArrayList<Point> blobs = new ArrayList<>();
        for (GNode neighbour : getNeighbours()) {
            double blobX = parent.gridX2Px(neighbour.getX()) - xPx;
            double blobY = parent.gridY2Px(neighbour.getY()) - yPx;
            Point blobCoord = new Point(blobX, blobY);
            blobCoord = blobCoord.div(Math.sqrt(blobCoord.x * blobCoord.x + blobCoord.y * blobCoord.y) * 2 / size);
            blobs.add(blobCoord);
        }
        AffineTransform old = g.getTransform();
        g.translate(xPx, yPx);
        g.setColor(outColor);
        g.fillOval(-size / 2, -size / 2, size, size);
        g.setColor(inColor);
        g.fillOval(-size / 4, -size / 4, size / 2, size / 2);
        for (Point blob : blobs) {
            g.setColor(outColor);
            g.fillOval(-size / 2, -size / 2, size, size);
            g.setColor(inColor);
            g.fillOval(-size / 4, -size / 4, size / 2, size / 2);
            g.setColor(Color.GREEN);
            g.fillOval((int) (blob.x - 2), (int) (blob.y - 2), 5, 5);
        }
        g.setColor(Color.MAGENTA);
        Point[] blobPts = makeBlob(xPx, yPx, blobs, -size, -size, size, size);
        for (Point blobPt : blobPts) {
            g.fillOval((int) (blobPt.x - 1), (int) (blobPt.y - 1), 3, 3);
        }

        g.setTransform(old);
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getNode() {
        return node;
    }

    @Override
    public boolean hits(NPPanel parent, int x, int y) {
        return (x - parent.gridX2Px(this.x)) * (x - parent.gridX2Px(this.x)) + (y - parent.gridY2Px(this.y)) * (y - parent.gridY2Px(this.y)) <= size * size / 4;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public GArc[] getOutgoingArcs() {
        ArrayList<GArc> arcs = new ArrayList<>();
        for (GArc arc : graph.getArcs()) {
            if (arc.getSource().equals(this)) {
                arcs.add(arc);
            }
        }
        return arcs.toArray(new GArc[arcs.size()]);
    }

    public GNode[] getNeighbours() {
        ArrayList<GNode> nodes = new ArrayList<>();
        GArc[] arcs = getOutgoingArcs();
        for (GArc arc : arcs) {
            if (!nodes.contains(arc.getDestination())) {
                nodes.add(arc.getDestination());
            }
        }
        return nodes.toArray(new GNode[nodes.size()]);
    }

    private Point[] makeBlob(double xPx, double yPx, ArrayList<Point> blobs, double minX, double minY, double maxX, double maxY) {
        Point[][] grid = makeGrid(minX, minY, maxX, maxY);
        BlobFunction function = new BlobFunction(blobs.toArray(new Point[blobs.size()]), size/2);
        ArrayList<Point> pts = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                Point zero = function.findZeroBetween(grid[i][j], grid[i][j + 1]);
                if (zero != null) {
                    pts.add(zero);
                }
            }
        }
//        for (int i = 0; i < grid.length-1; i++) {
//            for (int j = 0; j < grid[i].length; j++) {
//                Point zero = function.findZeroBetween(grid[i][j], grid[i+1][j]);
//                if (zero != null) {
//                    pts.add(zero);
//                }
//            }
//        }
        return pts.toArray(new Point[pts.size()]);
    }

    private class BlobFunction {

        public static final double EPS = 0.01;

        private Point[] others;
        private double distance;

        public BlobFunction(Point[] others, double d) {
            this.others = others;
            this.distance = d;
        }

        public double f(Point p) {
            double z = 0;
            for (Point pt : others) {
                z += Math.sqrt((p.x - pt.x) * (p.x - pt.x) + (p.y - pt.y) * (p.y - pt.y));
            }
            z += Math.sqrt(p.x*p.x + p.y * p.y);
            return z - distance;
        }

        private Point findZeroBetween(Point p1, Point p2) {
            if (f(p1) * f(p2) < 0) {
                Point mid = p1.avg(p2);
                if (isZero(f(mid))) {
                    return mid;
                } else {
                    if (f(mid) * f(p1) < 0) {
                        findZeroBetween(p1, mid);
                    } else {
                        findZeroBetween(p2, mid);
                    }
                }
            }
            return null;
        }

        private boolean isZero(double f) {
            return Math.abs(f) < EPS;
        }
    }

    private Point[][] makeGrid(double minX, double minY, double maxX, double maxY) {
        int gridSize = 500;
        Point[][] grid = new Point[gridSize][gridSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Point((maxY - minX)*i/gridSize + minX, (maxY - minY)*j/gridSize + minY);
            }
        }
        return grid;
    }
}
