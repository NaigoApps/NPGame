/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import npgame.graph.Node;
import npgame.graph.graphic.GArc;
import npgame.graph.graphic.GGraph;
import npgame.graph.graphic.GNode;

/**
 *
 * @author Lorenzo
 */
public class NPPanel extends JPanel implements MouseListener, MouseMotionListener {

    public static final int EMPTY_MODE = 0;
    public static final int NEW_NODE_MODE = 1;
    public static final int NEW_ARC_MODE = 2;
    public static final int MOVE_NODE_MODE = 3;

    private GNode last;
    private int memX;
    private int memY;
    private int memXScreen;
    private int memYScreen;

    private int mode;

    private GGraph graph;

    public NPPanel(GGraph graph) {
        this.graph = graph;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.mode = EMPTY_MODE;
        this.last = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GRAY.brighter());
        for (int i = 0; i < graph.getGridWidth(); i++) {
            for (int j = 0; j < graph.getGridHeight(); j++) {
                if (i < graph.getGridWidth() - 1) {
                    g.drawLine((int) gridX2Px(i),(int)  gridY2Px(j),(int)  gridX2Px(i + 1),(int)  gridY2Px(j));
                }
                if (j < graph.getGridHeight() - 1) {
                    g.drawLine((int) gridX2Px(i),(int)  gridY2Px(j),(int)  gridX2Px(i),(int)  gridY2Px(j + 1));
                }
            }
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (GArc a : graph.getArcs()) {
            a.paint(this, (Graphics2D) g);
        }
        for (GNode n : graph.getNodes()) {
            n.paint(this, (Graphics2D) g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        GNode node = graph.getNode(this, x, y);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (node != null) {
                if (last == null || last.getNode().getNeighbours().contains(node.getNode())) {
                    node.getNode().setVisited(true);

                    for (GArc arc : graph.getArcs()) {
                        if (arc.getSource() == last && arc.getDestination() == node) {
                            arc.getArc().setVisited(true);
                        }
                    }
                    last = node;
                    GNode[] nodes = graph.getNodes();
                    boolean winDetected = true;
                    for (GNode n : nodes) {
                        if (!n.getNode().isVisited()) {
                            winDetected = false;
                        }
                    }
                    if (!winDetected) {
                        List<Node> newNeighbours = last.getNode().getNeighbours();
                        boolean lostDetected = true;
                        for (Node newNeighbour : newNeighbours) {
                            if (!newNeighbour.isVisited()) {
                                lostDetected = false;
                            }
                        }
                        if(lostDetected){
                            JOptionPane.showMessageDialog(this, "LOST");
                        }
                    }else{
                        JOptionPane.showMessageDialog(this, "WIN");
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mode == MOVE_NODE_MODE) {
            last.setX(memX + toGridDeltaX(this, e.getXOnScreen() - memXScreen));
            last.setY(memY + toGridDeltaY(this, e.getYOnScreen() - memYScreen));
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        GNode curNode = graph.getNode(this, x, y);
        for (GNode node : graph.getNodes()) {
            node.setHighlighted(node == curNode);
        }
    }

    public int toGridX(Component parent, int i) {
        return Math.round(((float) i - graph.getGridMargin()) * graph.getGridWidth() / (getWidth() - 2 * graph.getGridMargin()));
    }

    public int toGridDeltaX(Component parent, int x) {
        return Math.round((float) x * graph.getGridWidth()) / (getWidth() - 2 * graph.getGridMargin());
    }

    public int toGridDeltaY(Component parent, int y) {
        return Math.round((float) y * graph.getGridHeight()) / (getHeight() - 2 * graph.getGridMargin());
    }

    public int toGridY(Component parent, int i) {
        return Math.round(((float) i - graph.getGridMargin()) * graph.getGridHeight() / (getHeight() - 2 * graph.getGridMargin()));
    }

    public double gridX2Px(double x) {
        return x * (getWidth() - 2 * graph.getGridMargin()) / graph.getGridWidth() + graph.getGridMargin();
    }

    public double gridY2Px(double y) {
        return y * (getHeight() - 2 * graph.getGridMargin()) / graph.getGridHeight() + graph.getGridMargin();
    }

}
