/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph.graphic;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import npgame.NPPanel;
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
    private int gridWidth;
    private int gridHeight;

    public GGraph(Graph g, int gridw, int gridh) {
        this.graph = g;
        this.gridWidth = gridw;
        this.gridHeight = gridh;
        this.nodes = new ArrayList<>();
        this.arcs = new ArrayList<>();
        init(gridw, gridh);
    }

    public void init(int w, int h) {
        nodes.clear();
        arcs.clear();
        ArrayList<Integer[]> grid = new ArrayList<>();
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                grid.add(new Integer[]{i, j});
            }
        }
        for (Node n : graph.getNodes()) {
            int gridindex = (int) (Math.random() * grid.size());
            Integer[] point = grid.get(gridindex);
            for (Iterator<Integer[]> i = grid.iterator(); i.hasNext();) {
                Integer[] v = i.next();
                if(Objects.equals(v[0], point[0]) || Objects.equals(v[1], point[1])){
                    i.remove();
                }
            }
            nodes.add(new GNode(n, point[0], point[1], GNode.DEFAULT_SIZE, this));
        }
        for (Arc a : graph.getArcs()) {
            arcs.add(new GArc(a, findGraphicNode(a.getSource()), findGraphicNode(a.getDestination())));
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

    public GNode getNode(NPPanel parent, int x, int y) {
        for (GNode node : nodes) {
            if (node.hits(parent, x, y)) {
                return node;
            }
        }
        return null;
    }

    public void addArc(GNode src, GNode dst) {
        Arc a = new Arc(src.getNode(), dst.getNode());
        if (graph.addArc(a)) {
            arcs.add(new GArc(a, src, dst));
        }
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridMargin() {
        return 50;
    }

}
