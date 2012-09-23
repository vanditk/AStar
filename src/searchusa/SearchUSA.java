/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchusa;

import Generics.AStarPath;
import java.io.BufferedReader;
import java.io.FileReader;
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
        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/vandit/NetBeansProjects/SearchUSA/src/searchusa/roads.txt"));
            String rawLine = br.readLine();
            while (rawLine != null) {

                String[] parts = rawLine.split(",");
                roads.add(new Road(parts[0].trim(), parts[1].trim(), new Integer(parts[2].trim())));
                rawLine = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total number of paths : " + roads.size());
        return roads;
    }

    public static AStarPath aStarSearch(String src, String dest, StateSpace stateSpace) throws Exception {
        AStarPath solution = new AStarPath(0);

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
                    //logic to prune paths
                    Node lastNode = path.getLast();
                    boolean shorterPathFound = false;
                    if (stateSpace.getExpandedStates().contains(lastNode)) {

                        Object[] tempSolutions = solutionQueue.toArray();
                        AStarPath[] solutions = new AStarPath[tempSolutions.length];
                        for (int i = 0; i < tempSolutions.length; i++) {

                            solutions[i] = (AStarPath) tempSolutions[i];
                        }
                        for (int i = 0; i < solutions.length; i++) {
                            if (solutions[i].contains(lastNode)) {   //found some path through which node was expanded
                                int indexOfNode = solutions[i].indexOf(lastNode);
                                if (solutions[i].get(indexOfNode).getDistanceFromRoot() > path.getPathLength()) {
                                    //currentpath is shortest path. merge paths
                                    for (int k = 0; k < indexOfNode; k++) {
                                        solutions[i].remove(0);
                                    }
                                    solutions[i].getFirst().setParent(lastNode);

                                    solutions[i].addAll(0, path);

                                } else {
                                    //older path was shorter.. don't save currentPath to the PriorityQueue
                                }
                                solutionQueue = new PriorityQueue<AStarPath>();
                                solutionQueue.addAll(Arrays.asList(solutions));
                            }
                        }

                    }

                    //logic ends
                    if (!shorterPathFound) {
                        solutionQueue.add(path);
                    }
                }

            }

        }

        return solution;


    }
    /*
     * // implementation of Depth first search. public static LinkedList<Node>
     * depthFirstSearch(String src, String dest, StateSpace stateSpace) throws
     * Exception {
     *
     * LinkedList<Node> solution = new LinkedList<Node>(); LinkedList<Node>
     * start = new LinkedList<Node>(); Node root = stateSpace.getRoot();
     * Stack<LinkedList> solutionStack = new Stack<LinkedList>();
     *
     * start.add(root); solutionStack.push(start);
     *
     * while (!solutionStack.isEmpty()) { LinkedList<Node> currentPath =
     * solutionStack.pop(); if (currentPath.getLast().getValue().equals(dest)) {
     * solution = currentPath; // solution found !! Break ! break; } else {
     * ArrayList<LinkedList<Node>> newPaths =
     * stateSpace.generateNewPaths(currentPath); for
     * (ListIterator<LinkedList<Node>> it =
     * newPaths.listIterator(newPaths.size()); it.hasPrevious();) {
     * LinkedList<Node> path = it.previous(); solutionStack.push(path); } }
     *
     *
     * }
     *
     * return solution;
     *
     * }
     */

    public static void main(String[] args) {

        String searchType = args[0].trim();
        String srcCityName = args[1].trim();
        String destCityName = args[2].trim();


        ArrayList<Road> roads = initializePaths();
        Map<String, Node> allNodes = initializeNodes();
        StateSpace stateSpace = new StateSpace();

        try {

            stateSpace.initializeStateSpace(roads, allNodes, srcCityName, destCityName);
            Node root = stateSpace.getNodeForCity(srcCityName);
            Node goal = stateSpace.getNodeForCity(destCityName);
            if (root == null || goal == null) {
                throw new Exception("Please enter valid source and destination city names");
            }
            root.setParent(root);
            //LinkedList<Node> solution = new LinkedList<Node>();
            AStarPath solution = null;
            if (searchType.equalsIgnoreCase("greedy")) {
                solution = greedySearch(srcCityName, destCityName, stateSpace);
            } else if (searchType.equalsIgnoreCase("astar")) {
                solution = aStarSearch(srcCityName, destCityName, stateSpace);
            }else if(searchType.equalsIgnoreCase("dynamic")){
                solution = dynamicProgrammingSearch(srcCityName, destCityName, stateSpace);
            } 
            
            else {
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
                System.out.println("Total path distance:" + solution.calculatePathLength());
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private static Map<String, Node> initializeNodes() {

        Map<String, Node> cityMap = new HashMap<String, Node>();
        //cityMap.put("albanyGA",new Node("albanyGA",        31.58,  84.17));

        try {
            BufferedReader br = new BufferedReader(new FileReader("/home/vandit/NetBeansProjects/SearchUSA/src/searchusa/cities.txt"));
            String rawLine = br.readLine();
            while (rawLine != null) {

                String[] parts = rawLine.split(",");
                cityMap.put(parts[0].trim(), new Node(parts[0].trim(), new Double(parts[1].trim()), new Double(parts[2].trim())));
                rawLine = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total number of cities : " + cityMap.size());
        return cityMap;

    }

    private static AStarPath greedySearch(String srcCityName, String destCityName, StateSpace stateSpace) {

        AStarPath solution = new AStarPath(0);

        int initialCapacity = 20;
        GreedyComparator gcomparator = new GreedyComparator();
        PriorityQueue<AStarPath> solutionQueue = new PriorityQueue<AStarPath>(initialCapacity,gcomparator );

        Node root = stateSpace.getNodeForCity(srcCityName);
        Node goal = stateSpace.getNodeForCity(destCityName);
        root.setDistanceFromParent(0);

        AStarPath start = new AStarPath(stateSpace.calculateHeuristic(root, goal));
        start.add(root);
        solutionQueue.add(start);
        while (solutionQueue.size() > 0) {
            AStarPath currentPath = solutionQueue.remove();
            if (currentPath.getLast().getValue().equals(destCityName)) {
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

    private static AStarPath dynamicProgrammingSearch(String srcCityName, String destCityName, StateSpace stateSpace) {
        AStarPath solution = new AStarPath(0);

        PriorityQueue<AStarPath> solutionQueue = new PriorityQueue<AStarPath>();

        Node root = stateSpace.getNodeForCity(srcCityName);
        Node goal = stateSpace.getNodeForCity(destCityName);
        root.setDistanceFromParent(0);

        AStarPath start = new AStarPath(stateSpace.calculateHeuristic(root, goal));
        start.add(root);
        solutionQueue.add(start);
        while (solutionQueue.size() > 0) {
            AStarPath currentPath = solutionQueue.remove();
            if (currentPath.getLast().getValue().equals(destCityName)) {
                solution = currentPath;
                //solution found ! Break !!
                break;
            } else {
                ArrayList<AStarPath> newPaths = stateSpace.generateNewPaths(currentPath);
                for (AStarPath path : newPaths) {
                    //logic to prune paths
                    Node lastNode = path.getLast();
                    boolean shorterPathFound = false;
                    if (stateSpace.getExpandedStates().contains(lastNode)) {

                        Object[] tempSolutions = solutionQueue.toArray();
                        AStarPath[] solutions = new AStarPath[tempSolutions.length];
                        for (int i = 0; i < tempSolutions.length; i++) {

                            solutions[i] = (AStarPath) tempSolutions[i];
                        }
                        for (int i = 0; i < solutions.length; i++) {
                            if (solutions[i].contains(lastNode)) {   //found some path through which node was expanded
                                int indexOfNode = solutions[i].indexOf(lastNode);
                                if (solutions[i].get(indexOfNode).getDistanceFromRoot() > path.getPathLength()) {
                                    //currentpath is shortest path. merge paths
                                    for (int k = 0; k < indexOfNode; k++) {
                                        solutions[i].remove(0);
                                    }
                                    solutions[i].getFirst().setParent(lastNode);

                                    solutions[i].addAll(0, path);

                                } else {
                                    //older path was shorter.. don't save currentPath to the PriorityQueue
                                }
                                solutionQueue = new PriorityQueue<AStarPath>();
                                solutionQueue.addAll(Arrays.asList(solutions));
                            }
                        }

                    }

                    //logic ends
                    if (!shorterPathFound) {
                        solutionQueue.add(path);
                    }
                }

            }

        }

        return solution;


    }
}
