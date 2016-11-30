/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private ThreadRenderer renderer;
    
    public NPFrame(Graph g) throws HeadlessException {
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                renderer.end();
                dispose();
            }
            
        });
        setExtendedState(MAXIMIZED_BOTH);
        panel = new NPPanel(new GGraph(g, 40, 20));
        add(panel, BorderLayout.CENTER);
        renderer = new ThreadRenderer(this);
        renderer.start();
    }
    
}
