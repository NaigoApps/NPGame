/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package npgame.graph;

/**
 *
 * @author Lorenzo
 */
public class Arc {
    private Node source;
    private Node destination;
    private boolean visited;

    public Arc(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
        this.visited = true;
    }

    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Arc){
            return ((Arc)obj).getSource().equals(getSource()) && ((Arc)obj).getDestination().equals(getDestination());
        }
        return false;
    }

    public boolean isVisited() {
        return visited;
    }
    
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    
    
}
