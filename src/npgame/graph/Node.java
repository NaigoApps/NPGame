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

    public Node() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Node){
            return ((Node)obj).getId().equals(getId());
        }
        return false;
    }
    
    
    
}
