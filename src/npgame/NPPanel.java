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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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

    private GNode mem;
    private int memX;
    private int memY;
    private int memXScreen;
    private int memYScreen;

    private int mode;

    private Thread renderer;
    private GGraph graph;

    public NPPanel(GGraph graph) {
        this.graph = graph;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.mode = EMPTY_MODE;
        renderer = new ThreadRenderer(this);
        renderer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.GRAY.brighter());
        for (int i = 0; i < graph.getGridWidth(); i++) {
            for (int j = 0; j < graph.getGridHeight(); j++) {
                if (i < graph.getGridWidth() - 1) {
                    g.drawLine(getRealX(i), getRealY(j), getRealX(i + 1), getRealY(j));
                }
                if (j < graph.getGridHeight() - 1) {
                    g.drawLine(getRealX(i), getRealY(j), getRealX(i), getRealY(j + 1));
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
                node.getNode().setVisited(true);
                for (GArc arc : graph.getArcs()) {
                    if(arc.getSource() == node || arc.getDestination() == node){
                        if(arc.getSource().getNode().isVisited() && arc.getDestination().getNode().isVisited()){
                            arc.getArc().setVisited(true);
                        }
                    }
                }
                mem = node;
//                memX = node.getX();
//                memY = node.getY();
//                memXScreen = e.getXOnScreen();
//                memYScreen = e.getYOnScreen();
//                mode = MOVE_NODE_MODE;
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
            mem.setX(memX + toGridDeltaX(this, e.getXOnScreen() - memXScreen));
            mem.setY(memY + toGridDeltaY(this, e.getYOnScreen() - memYScreen));
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

    public int getRealX(double x) {
        return (int) (x * (getWidth() - 2 * graph.getGridMargin()) / graph.getGridWidth() + graph.getGridMargin());
    }

    public int getRealY(double y) {
        return (int) (y * (getHeight() - 2 * graph.getGridMargin()) / graph.getGridHeight() + graph.getGridMargin());
    }

}
