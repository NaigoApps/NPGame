/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph;

import java.util.ArrayList;

/**
 *
 * @author Lorenzo
 */
public class Graph {
    private ArrayList<Arc> arcs;
    private ArrayList<Node> nodes;
    
    public Graph(){
        this.arcs = new ArrayList<>();
        this.nodes = new ArrayList<>();
    }
    
    public void addNode(Node n){
        if(!nodes.contains(n)){
            nodes.add(n);
        }
    }
    
    public boolean addArc(Arc a){
        if(!arcs.contains(a) && nodes.contains(a.getSource()) && nodes.contains(a.getDestination())){
            arcs.add(a);
            return true;
        }else{
            return false;
        }
    }

    public Arc[] getArcs() {
        return arcs.toArray(new Arc[arcs.size()]);
    }

    public Node[] getNodes() {
        return nodes.toArray(new Node[nodes.size()]);
    }

    public boolean contains(Arc a) {
        for (Arc arc : arcs) {
            if (arc.equals(a)) {
                return true;
            }
        }
        return false;
    }

    
    
}
