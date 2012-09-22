/**
 *
 * @author vandit NCSU Student ID : 001085913
 */
package searchusa;

import java.util.*;


public class StateSpace {

    private Node root;
    private ArrayList<Road> links;
    private Map<String,Node> allNodes = new HashMap<String,Node>();
    private HashSet<Node> expandedStates = new HashSet<Node>();

    public HashSet<Node> getExpandedStates()
    {
        return expandedStates;
    }
    public void initializeStateSpace(ArrayList<Road> roadLinks,Map<String,Node> allNodesx, Node rootx) throws Exception {

        links = roadLinks;
        root = rootx;
        allNodes = allNodesx;
    }

    private void convertToMap(Iterable allNodesx) {
        Iterator<Node> it = allNodesx.iterator();
        //convert to a hashmap to enable getting nodes by city name -- reduce memory print
        while (it.hasNext())
        {
            Node x = it.next();
            allNodes.put(x.getValue(), x);
        }
    }
    public Node getNodeForCity(String city)
    {
        return allNodes.get(city);
    }
    // If a given node is already expanded, it returns a empty list (because this means we have entered into a loop
    // and backtracking is needed.)
    public ArrayList<Node> expandNode(Node n) throws Exception {
        
        if(expandedStates.contains(n))
        {
            //throw new Exception("tried to re-expand node : "+n);
            return new ArrayList<Node>();
        }
        expandedStates.add(n);
        for (Road road : links) {
            if (road.containsCity(n.getValue())) {
                try {
                    //modify to look up for nodes.
                    Node newCity = getNodeForCity(n.getValue());
                    //modify to look up for nodes.
                    if (!newCity.equals(n) && !newCity.equals(n.getParent())) {
                        newCity.setParent(n);
                        ArrayList<Node> children = n.getChildren();
                        if (!children.contains(n)) {
                            children.add(newCity);
                        }
                        n.setChildren(children);
                    }
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }

        }

         ArrayList<Node> children = n.getChildren();
         Collections.sort(children);
         
         n.setChildren(children);
         return children;
         
    }
    
     //city(City1, Lat1, Long1),
     //city(City2, Lat2, Long2),
     //Value is sqrt((69.5 * (Lat1 - Lat2)) ^ 2 + (69.5 * cos((Lat1 + Lat2)/360 * pi) * (Long1 - Long2)) ^ 2)
    public double calculateHeuristic(Node city1,Node city2)
    {
        double lat1 = city1.getLatitude(),lat2=city2.getLatitude();
        double long1 = city1.getLongitude(),long2=city2.getLongitude();
        double value = 0.0;
        Math.sqrt(((69.5*(lat1-lat2)*69.5*(lat1-lat2))) + (69.5*Math.cos((lat1+lat2)/360 * Math.PI)*(long1 - long2)*(long1 - long2)));
        return value;
    }
    
    public ArrayList<LinkedList<Node>> generateNewPaths(LinkedList<Node> currentPath) {
        Node n = currentPath.getLast();
        ArrayList<LinkedList<Node>> newPaths = new ArrayList<LinkedList<Node>>();
        try {
            for (Node child : expandNode(n)) {
                LinkedList<Node> newPath = new LinkedList<Node>();
                newPath.addAll(currentPath);
                newPath.add(child);
                newPaths.add(newPath);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return newPaths;
    }

    /**
     * @return the root
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param root the root to set
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * @return the links
     */
    public ArrayList<Road> getLinks() {
        return links;
    }

    /**
     * @param links the links to set
     */
    public void setLinks(ArrayList<Road> links) {
        this.links = links;
    }
}
