/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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

    private GNode mem;
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (GArc a : graph.getArcs()) {
            a.paint((Graphics2D) g);
        }
        for (GNode n : graph.getNodes()) {
            n.paint((Graphics2D) g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        GNode node = graph.getNode(x, y);
        if(SwingUtilities.isLeftMouseButton(e)){
            if (node == null && mode == EMPTY_MODE) {
                mode = NEW_NODE_MODE;
            } else {
                mem = node;
                mode = NEW_ARC_MODE;
            }
        }else if(SwingUtilities.isRightMouseButton(e)){
            if(node != null){
                mem = node;
                memX = node.getX();
                memY = node.getY();
                memXScreen = e.getXOnScreen();
                memYScreen = e.getYOnScreen();
                mode = MOVE_NODE_MODE;
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        GNode n = graph.getNode(x, y);
        if (mode == NEW_NODE_MODE && n == null) {
            Node newNode = new Node();
            GNode newGNode = new GNode(newNode, x, y, GNode.DEFAULT_SIZE);
            graph.addNode(newGNode);
        } else if (mode == NEW_ARC_MODE) {
            if (n != null) {
                graph.addArc(mem, n);
            }
        }
        mode = EMPTY_MODE;
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
        if(mode == MOVE_NODE_MODE){
            mem.setX(memX + e.getXOnScreen() - memXScreen);
            mem.setY(memY + e.getYOnScreen() - memYScreen);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
