/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import npgame.graph.Arc;
import npgame.graph.Graph;
import npgame.graph.Node;
import npgame.graph.graphic.GGraph;

/**
 *
 * @author Lorenzo
 */
public class NPFrame extends JFrame{

    private NPPanel panel;
    
    public NPFrame() throws HeadlessException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        Graph g = generateGraph(6);
        panel = new NPPanel(new GGraph(g, 40, 20));
        add(panel, BorderLayout.CENTER);
    }

    private Graph generateGraph(int n) {
        Graph g = new Graph();
        Node[] nodes = new Node[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node();
            g.addNode(nodes[i]);
        }
        for (int i = 0; i < nodes.length - 1; i++) {
            g.addArc(new Arc(nodes[i], nodes[i+1]));
        }
//        for (int i = 0; i < 10; i++) {
//            Arc a = new Arc(nodes[(int)(Math.random()*nodes.length)], nodes[(int)(Math.random()*nodes.length)]);
//            if(!g.contains(a)){
//                g.addArc(a);
//            }
//        }
        return g;
    }
    
}
