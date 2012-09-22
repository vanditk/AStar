/**
 *
 * @author vandit NCSU Student ID : 001085913
 */
package searchusa;

import java.util.ArrayList;


public class Node implements Comparable<Node>{

    private Node parent;
    private ArrayList<Node> children = new ArrayList<Node>();
    private String value;
    private double latitude,longitude;
    private double distanceFromParent = 0;

    public Node(String valuex) {
        value = valuex;
        
    }
    
    @Override
    public int hashCode()
    {
        return value.hashCode();
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the children
     */
    public ArrayList<Node> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object n) {
        if(n instanceof Node)
        {
        return value.equalsIgnoreCase(((Node)n).getValue());
        }
        else
        {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Node: " + value;
    }

    @Override
    public int compareTo(Node o) {
        return this.value.compareToIgnoreCase(o.value);
    }

    /**
     * @return the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * @return the distanceFromParent
     */
    public double getDistanceFromParent() {
        return distanceFromParent;
    }

    /**
     * @param distanceFromParent the distanceFromParent to set
     */
    public void setDistanceFromParent(double distanceFromParent) {
        this.distanceFromParent = distanceFromParent;
    }

 
}
