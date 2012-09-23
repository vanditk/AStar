/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Generics;

import java.util.Iterator;
import java.util.LinkedList;
import searchusa.Node;

/**
 *
 * @author vandit
 */
public class AStarPath extends LinkedList<Node> implements Comparable {
    
    private double pathLength =0;
    private double heuristicDistanceFromLastNode = 0.0;
    private int pathId = 0;
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

    /**
     * @return the pathLength
     */
    public double getPathLength() {
        return pathLength;
    }

    /**
     * @param pathLength the pathLength to set
     */
    public void setPathLength(double pathLength) {
        this.pathLength = pathLength;
    }
    public AStarPath(double heuristic)
    {
        super();
        heuristicDistanceFromLastNode =  heuristic;
        
    }

    @Override
    public int compareTo(Object o) {
        AStarPath path = (AStarPath)o;
        Double weightCurrentPath = pathLength + heuristicDistanceFromLastNode;
        Double weightOtherPath  = path.getPathLength() + path.getHeuristicDistanceFromLastNode();
        return  weightCurrentPath.compareTo(weightOtherPath);
    }
    
    public double calculatePathLength()
    {
        double pathLengthx = 0;
        
        Iterator<Node> it = this.iterator();
        while(it.hasNext())
        {
            pathLengthx += it.next().getDistanceFromParent();
        }
        return pathLengthx;
    }

    /**
     * @return the pathId
     */
    public int getPathId() {
        return pathId;
    }

    /**
     * @param pathId the pathId to set
     */
    public void setPathId(int pathId) {
        this.pathId = pathId;
    }

   
   
    
    
    
    
}
