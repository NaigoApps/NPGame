/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import npgame.graph.Graph;
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
        panel = new NPPanel(new GGraph(new Graph()));
        add(panel, BorderLayout.CENTER);
    }
    
}
