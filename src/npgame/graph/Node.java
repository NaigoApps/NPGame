/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Lorenzo
 */
public class Node {
    private UUID id;
    private boolean visited;
    private final Graph graph;

    public Node(Graph g) {
        id = UUID.randomUUID();
        this.graph = g;
        visited = false;
    }

    public UUID getId() {
        return id;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            return ((Node)obj).getId().equals(getId());
        }
        return false;
    }

    public List<Node> getNeighbours() {
        Node[] nodes = graph.getNodes();
        ArrayList<Node> neighbours = new ArrayList<>();
        for (Node node : nodes) {
            Arc a = new Arc(this, node);
            if (graph.contains(a)) {
                neighbours.add(node);
            }
        }
        return neighbours;
    }
    
    
    
}
