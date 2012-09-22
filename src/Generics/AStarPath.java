/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Generics;

import java.util.LinkedList;

/**
 *
 * @author vandit
 */
public class AStarPath extends LinkedList{
    
    private double heuristicDistanceFromLastNode = 0.0;

    /**
     * @return the heuristicDistanceFromLastNode
     */
    public double getHeuristicDistanceFromLastNode() {
        return heuristicDistanceFromLastNode;
    }

    /**
     * @param heuristicDistanceFromLastNode the heuristicDistanceFromLastNode to set
     */
    public void setHeuristicDistanceFromLastNode(double heuristicDistanceFromLastNode) {
        this.heuristicDistanceFromLastNode = heuristicDistanceFromLastNode;
    }
    
    
    
    
    
}
