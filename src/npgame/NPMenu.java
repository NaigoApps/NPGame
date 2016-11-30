/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import npgame.graph.Arc;
import npgame.graph.Graph;
import npgame.graph.Node;

/**
 *
 * @author Lorenzo
 */
public class NPMenu extends JFrame implements ActionListener {

    JPanel pnCenter;

    JButton btnTutorial;
    JButton btnVeryEasy;
    JButton btnEasy;
    JButton btnMedium;
    JButton btnHard;
    JButton btnVeryHard;

    public NPMenu() throws HeadlessException {
        add(new JLabel(), BorderLayout.NORTH);
        add(new JLabel(), BorderLayout.SOUTH);
        add(new JLabel(), BorderLayout.WEST);
        add(new JLabel(), BorderLayout.EAST);
        pnCenter = new JPanel(new GridLayout(2, 3, 10, 10));
        add(pnCenter, BorderLayout.CENTER);
        initButtons();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initButtons() {
        btnTutorial = new JButton("Tutorial");
        btnTutorial.addActionListener(this);
        pnCenter.add(btnTutorial);
        btnVeryEasy = new JButton("Very Easy");
        btnVeryEasy.addActionListener(this);
        pnCenter.add(btnVeryEasy);
        btnEasy = new JButton("Easy");
        btnEasy.addActionListener(this);
        pnCenter.add(btnEasy);
        btnMedium = new JButton("Medium");
        btnMedium.addActionListener(this);
        pnCenter.add(btnMedium);
        btnHard = new JButton("Hard");
        btnHard.addActionListener(this);
        pnCenter.add(btnHard);
        btnVeryHard = new JButton("Very Hard");
        btnVeryHard.addActionListener(this);
        pnCenter.add(btnVeryHard);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graph g = generateGraph(0, 0);
        if (e.getSource() == btnTutorial) {
            g = generateGraph(5, 0);
        }else if(e.getSource() == btnVeryEasy){
            g = generateGraph(5, 2);
        }else if(e.getSource() == btnEasy){
            g = generateGraph(5, 4);
        }else if(e.getSource() == btnMedium){
            g = generateGraph(6, 3);
        }else if(e.getSource() == btnHard){
            g = generateGraph(6, 6);
        }else if(e.getSource() == btnVeryHard){
            g = generateGraph(7, 7);
        }
        NPFrame f = new NPFrame(g);
        f.setVisible(true);
    }

    private Graph generateGraph(int n, int extraArcs) {
        Graph g = new Graph();
        Node[] nodes = new Node[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new Node(g);
            g.addNode(nodes[i]);
        }
        for (int i = 0; i < nodes.length - 1; i++) {
            g.addArc(new Arc(nodes[i], nodes[i + 1]));
        }
        ArrayList<Arc> available = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes.length; j++) {
                if (i != j) {
                    Arc a = new Arc(nodes[i], nodes[j]);
                    Arc b = new Arc(nodes[j], nodes[i]);
                    if(!g.contains(a) && !g.contains(b)){
                        available.add(a);
                    }
                }
            }
        }
        for (int i = 0; i < extraArcs; i++) {
            Arc a = available.get((int)(Math.random()*available.size()));
            g.addArc(a);
            Arc b = new Arc(a.getDestination(), a.getSource());
            available.remove(a);
            available.remove(b);
        }
        return g;
    }

}
