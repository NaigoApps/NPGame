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
    private ArrayList<Point> blobs;
    private ArrayList<BSplineLine> blobsPts;

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
        if (blobs == null) {
            blobs = new ArrayList<>();
            for (GNode neighbour : getNeighbours()) {
                double blobX = parent.gridX2Px(neighbour.getX()) - xPx;
                double blobY = parent.gridY2Px(neighbour.getY()) - yPx;
                Point blobCoord = new Point(blobX, blobY);
                blobCoord = blobCoord.div(Math.sqrt(blobCoord.x * blobCoord.x + blobCoord.y * blobCoord.y) * 2 / size);
//                blobCoord = blobCoord.div(0.5);
                blobs.add(blobCoord);
            }
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
        if (blobsPts == null) {
            blobsPts = makeBlob(blobs, -size * 2, -size * 2, size * 2, size * 2);
        }

        for (int i = 0; i < blobsPts.size(); i++) {
            BSplineLine blobPts = blobsPts.get(i);
            blobPts.makeCurve();
            g.setColor(Color.RED);
            for (int j = 0; j < blobPts.getPointsNumber() - 1; j++) {
                g.drawLine((int) blobPts.getPoint(j).x, (int) blobPts.getPoint(j).y, (int) blobPts.getPoint(j + 1).x, (int) blobPts.getPoint(j + 1).y);
            }
            g.setColor(Color.BLUE);
            for (int j = 0; j < blobPts.getNodesNumber() - 1; j++) {
                g.drawLine((int) blobPts.getNode(j).x, (int) blobPts.getNode(j).y, (int) blobPts.getNode(j + 1).x, (int) blobPts.getNode(j + 1).y);
            }
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

    private ArrayList<BSplineLine> makeBlob(ArrayList<Point> blobs, double minX, double minY, double maxX, double maxY) {
        Point[][] grid = makeGrid(minX, minY, maxX, maxY);
        BlobFunction function = new BlobFunction(blobs.toArray(new Point[blobs.size()]), size / 4, size / 2);
        ArrayList<BSplineLine> lines = new ArrayList<>();
        for (int i = 0; i < grid.length - 1; i++) {
            for (int j = 0; j < grid[i].length - 1; j++) {
                Segment[] segments = function.zeroPoints(grid[i][j], grid[i][j + 1], grid[i + 1][j], grid[i + 1][j + 1]);
                for (Segment segment : segments) {
                    merge(lines, segment);
                }
            }
        }
        if (lines.size() != 1) {
            System.err.println("WARNING: FOUND " + lines.size() + " CURVES INSTEAD OF ONE");
        }
        return lines;
    }

    private void merge(ArrayList<BSplineLine> lines, Segment segment) {
        boolean merged = false;
        for (int i = 0; i < lines.size() && !merged; i++) {
            BSplineLine current = lines.get(i);
            if (current.getNode(0).equals(segment.getPoint(0))) {
                current.addNode(0, segment.getPoint(1));
                merged = true;
            } else if (current.getNode(0).equals(segment.getPoint(1))) {
                current.addNode(0, segment.getPoint(0));
                merged = true;
            } else if (current.getNode(current.getNodesNumber() - 1).equals(segment.getPoint(0))) {
                current.addNode(segment.getPoint(1));
                merged = true;
            } else if (current.getNode(current.getNodesNumber() - 1).equals(segment.getPoint(1))) {
                current.addNode(segment.getPoint(0));
                merged = true;
            }
        }
        if (!merged) {
            BSplineLine pts = new BSplineLine();
            pts.addNode(segment.getPoint(0));
            pts.addNode(segment.getPoint(1));
            lines.add(pts);
        }
    }

    private class BlobFunction {

        public static final double EPS = 0.01;

        private Point[] others;
        private double k;
        private double k1;

        public BlobFunction(Point[] others, double k, double k1) {
            this.others = others;
            this.k = k;
            this.k1 = k1;
        }

        public double f(Point p) {
            double z = 0;
            for (Point pt : others) {
                z += gauss(pt, new Point(size / 8, size / 8), p);
            }
            z += 1.5 * gauss(new Point(0, 0), new Point(size / 2, size / 2), p);
            return z - 0.8;
        }

        private double gauss(Point mi, Point sigma, Point p) {
            return Math.exp(-((p.x - mi.x) * (p.x - mi.x) / (2 * sigma.x * sigma.x)
                    + (p.y - mi.y) * (p.y - mi.y) / (2 * sigma.y * sigma.y)));
        }

        public Segment[] zeroPoints(Point nw, Point ne, Point sw, Point se) {
            int[] bitmap = new int[4];
            bitmap[0] = f(nw) < 0 ? 0 : 1;
            bitmap[1] = f(ne) < 0 ? 0 : 1;
            bitmap[2] = f(se) < 0 ? 0 : 1;
            bitmap[3] = f(sw) < 0 ? 0 : 1;
            int index = bitmap[0] * 8 + bitmap[1] * 4 + bitmap[2] * 2 + bitmap[3];
            Point nwne = nw.avg(ne);
            Point swse = sw.avg(se);
            Point nwsw = nw.avg(sw);
            Point nese = ne.avg(se);
            Segment[][] lookup = new Segment[][]{
                {},
                {new Segment(nwsw, swse)},
                {new Segment(swse, nese)},
                {new Segment(nwsw, nese)},
                {new Segment(nwne, nese)},
                {new Segment(swse, nese), new Segment(nwsw, nwne)},
                {new Segment(nwne, swse)},
                {new Segment(nwsw, nwne)},
                {new Segment(nwsw, nwne)},
                {new Segment(nwne, swse)},
                {new Segment(nwsw, swse), new Segment(nwne, nese)},
                {new Segment(nwne, nese)},
                {new Segment(nwsw, nese)},
                {new Segment(swse, nese)},
                {new Segment(nwsw, swse)},
                {}
            };
            return lookup[index];
        }
    }

    private Point[][] makeGrid(double minX, double minY, double maxX, double maxY) {
        int gridSize = 30;
        Point[][] grid = new Point[gridSize][gridSize];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Point((maxX - minX) * i / (gridSize - 1) + minX, (maxY - minY) * j / (gridSize - 1) + minY);
            }
        }
        return grid;
    }
}
