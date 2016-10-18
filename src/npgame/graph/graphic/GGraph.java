/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.util.ArrayList;
import npgame.graph.Arc;
import npgame.graph.Graph;
import npgame.graph.Node;

/**
 *
 * @author Lorenzo
 */
public class GGraph {

    private Graph graph;

    private ArrayList<GArc> arcs;
    private ArrayList<GNode> nodes;

    public GGraph(Graph g) {
        this.graph = g;
        this.nodes = new ArrayList<>();
        this.arcs = new ArrayList<>();
        init();
    }

    public void init() {
        nodes.clear();
        arcs.clear();
        for (Node n : graph.getNodes()) {
            nodes.add(new GNode(n, 0, 0, GNode.DEFAULT_SIZE));
        }
        for (Arc a : graph.getArcs()) {
            arcs.add(new GArc(findGraphicNode(a.getSource()), findGraphicNode(a.getDestination())));
        }
    }

    public GArc[] getArcs() {
        return arcs.toArray(new GArc[arcs.size()]);
    }

    public GNode[] getNodes() {
        return nodes.toArray(new GNode[nodes.size()]);
    }

    public void addNode(GNode node) {
        nodes.add(node);
        graph.addNode(node.getNode());
    }

    private GNode findGraphicNode(Node n) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getNode().equals(n)) {
                return nodes.get(i);
            }
        }
        return null;
    }

    public GNode getNode(int x, int y) {
        for (GNode node : nodes) {
            if(node.hits(x, y)){
                return node;
            }
        }
        return null;
    }

    public void addArc(GNode src, GNode dst) {
        if(graph.addArc(new Arc(src.getNode(), dst.getNode()))){
            arcs.add(new GArc(src, dst));
        }
    }
}
