/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph;

import java.util.UUID;

/**
 *
 * @author Lorenzo
 */
public class Node {
    private UUID id;
    private boolean visited;

    public Node() {
        id = UUID.randomUUID();
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
    
    
    
}
