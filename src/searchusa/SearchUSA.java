/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchusa;

import Generics.AStarPath;
import java.util.*;

 /**
 *
 * @author vandit NCSU Student ID : 001085913
 */
public class SearchUSA {

   /**
     * @param args the command line arguments
     */
    public static boolean goalFound = false;

    public static ArrayList<Road> initializePaths() {
        ArrayList<Road> roads = new ArrayList<Road>();
        roads.add(new Road("oradea", "zerind", 71));
        roads.add(new Road("zerind", "arad", 75));
        roads.add(new Road("arad", "timisoara", 118));
        roads.add(new Road("timisoara", "lugoj", 111));
        roads.add(new Road("lugoj", "mehadia", 70));
        roads.add(new Road("dobreta", "mehadia", 75));
        roads.add(new Road("oradea", "sibiu", 151));
        roads.add(new Road("arad", "sibiu", 140));
        roads.add(new Road("dobreta", "craiova", 120));
        roads.add(new Road("sibiu", "rimnicu_vilcea", 80));
        roads.add(new Road("sibiu", "fagaras", 99));
        roads.add(new Road("rimnicu_vilcea", "craiova", 146));
        roads.add(new Road("pitesti", "craiova", 138));
        roads.add(new Road("rimnicu_vilcea", "pitesti", 97));
        roads.add(new Road("bucharest", "pitesti", 101));
        roads.add(new Road("bucharest", "fagaras", 211));
        roads.add(new Road("bucharest", "giurgiu", 90));
        roads.add(new Road("bucharest", "urziceni", 85));
        roads.add(new Road("vaslui", "urziceni", 142));
        roads.add(new Road("hirsova", "urziceni", 98));
        roads.add(new Road("hirsova", "eforie", 86));
        roads.add(new Road("vaslui", "iasi", 92));
        roads.add(new Road("neamt", "iasi", 87));
        return roads;
    }

    public static LinkedList<Node> aStarSearch(String src, String dest, StateSpace stateSpace) throws Exception {
        LinkedList<Node> solution = new AStarPath(0);
        
        PriorityQueue<AStarPath> solutionQueue = new PriorityQueue<AStarPath>();
        
        Node root = stateSpace.getNodeForCity(src);
        Node goal = stateSpace.getNodeForCity(dest);
        root.setDistanceFromParent(0);
        AStarPath start = new AStarPath(stateSpace.calculateHeuristic(root, goal));
        start.add(root);
        solutionQueue.add(start);
        while (solutionQueue.size() > 0) {
            AStarPath currentPath = solutionQueue.remove();
            if (currentPath.getLast().getValue().equals(dest)) {
                solution = currentPath;
                //solution found ! Break !!
                break;
            } else {
                ArrayList<AStarPath> newPaths = stateSpace.generateNewPaths(currentPath);
                for (AStarPath path : newPaths) {
                    
                    solutionQueue.add(path);
                }

            }

        }

        return solution;


    }
/*
    // implementation of Depth first search.
    public static LinkedList<Node> depthFirstSearch(String src, String dest, StateSpace stateSpace) throws Exception {

        LinkedList<Node> solution = new LinkedList<Node>();
        LinkedList<Node> start = new LinkedList<Node>();
        Node root = stateSpace.getRoot();
        Stack<LinkedList> solutionStack = new Stack<LinkedList>();
        
        start.add(root);
        solutionStack.push(start);

        while (!solutionStack.isEmpty()) {
            LinkedList<Node> currentPath = solutionStack.pop();
            if (currentPath.getLast().getValue().equals(dest)) {
                solution = currentPath;
                // solution found !! Break !
                break;
            } else {
                ArrayList<LinkedList<Node>> newPaths = stateSpace.generateNewPaths(currentPath);
                for (ListIterator<LinkedList<Node>> it = newPaths.listIterator(newPaths.size()); it.hasPrevious();) {
                    LinkedList<Node> path = it.previous();
                    solutionStack.push(path);
                }
            }


        }
        
            return solution;
        
    }
*/
    public static void main(String[] args) {

        String searchType = args[0].trim();
        String srcCityName = args[1].trim();
        String destCityName = args[2].trim();
        
        
        ArrayList<Road> roads = initializePaths();
        Map<String,Node> allNodes = initializeNodes();
        StateSpace stateSpace = new StateSpace();
        try {
            //modify to get node from statespace rather than creating new nodes
            Node root = stateSpace.getNodeForCity(srcCityName);
            Node goal =stateSpace.getNodeForCity(destCityName);
            //modify upto here
            root.setParent(root);
            stateSpace.initializeStateSpace(roads,allNodes, root,goal);
            LinkedList<Node> solution = new LinkedList<Node>();
            if (searchType.equalsIgnoreCase("greedy")) {
                solution = greedySearch(srcCityName, destCityName, stateSpace);
            } else if (searchType.equalsIgnoreCase("astar")) {
                solution = aStarSearch(srcCityName, destCityName, stateSpace);
            } else {
                throw new Exception("Invalid Search Type. Enter either BFS or DFS");
            }

            if (solution.isEmpty()) {
                System.out.println("No path found!");
            } else {
                System.out.println("The solution path by " + searchType + " is as follows: ");
                Iterator<Node> it = solution.listIterator();
                while (it.hasNext()) {
                    Node n = it.next();
                    if (solution.getLast().equals(n)) {
                        System.out.println(n.getValue());
                    } else {
                        System.out.print(n.getValue() + " -- ");
                    }
                }

                System.out.println("Number of nodes Expanded: " + stateSpace.getExpandedStates().size() + "\n Expanded Nodes : " + stateSpace.getExpandedStates());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static Map<String, Node> initializeNodes() {
        throw new UnsupportedOperationException("Not yet implemented");
        
    }

    private static LinkedList<Node> greedySearch(String srcCityName, String destCityName, StateSpace stateSpace) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    
    
}
