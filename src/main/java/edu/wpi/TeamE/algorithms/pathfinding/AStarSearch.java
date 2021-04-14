package edu.wpi.TeamE.algorithms.pathfinding;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A* Search Implementation
 * Contains specific implementation of A*
 * Required for use of A* as pathfinding solution
 */
public class AStarSearch extends Searcher {

    public AStarSearch(){
        super();
    }

    /**
     * Searches for a Path from startId to endId
     * Utilizing the A* Search Algorithm
     * i.e. will make an evaluation based on euclidean distance
     *      and prioritize Nodes based on this evaluation
     * @param startId the Node to begin Path at
     * @param endId the Node to end Path at
     * @return Path object representing the route from start to end
     */
    public Path search(String startId, String endId){

        //get nodes from database
        Node start = getNode(startId);
        Node end = getNode(endId);

        System.out.printf("\nFinding path from '%s' to '%s'\n...\n", start.get("longName"), end.get("longName"));

        PriorityQueue<Node> potentials = new PriorityQueue<>();
        HashMap<Node, Double> prevCost = new HashMap<>();
        HashMap<Node, Node> cameFrom = new HashMap<>();

        Double zero = Double.valueOf(0);
        prevCost.put(start, zero);
        start.setCost(zero);

        potentials.add(start);

        while(!potentials.isEmpty()){
            Node current = potentials.peek();
            if(current.equals(end)){
                //success case
                Path path = new Path();
                path.add(start.copy());
                path.add(reconstructPath(cameFrom, current));
                return path;
            }

            current = potentials.poll();

            HashMap<String, Double> neighbors = getNeighbors(current.get("id"));

            for(String neighborId : neighbors.keySet()){
                Node neighbor = getNode(neighborId);
                Double neighborCost = prevCost.get(current) + neighbors.get(neighborId);
                if(!prevCost.containsKey(neighbor) || neighborCost < prevCost.get(neighbor)){
                    prevCost.put(neighbor, neighborCost);
                    cameFrom.put(neighbor, current);
                    neighbor.setCost(neighborCost + dist(neighbor, end));

                    //remove and re insert because value has been updated
                    potentials.remove(neighbor);
                    potentials.add(neighbor);
                }
            }
        }

        //failure case
        //end not found
        return null;
    }

    /**
     * Calculate the euclidean distance between two nodes
     * Pythagorean theorem
     * TODO: include floor changes
     * @param n1,n2 Nodes to calculate the distance between
     * @return the distance between two nodes
     */
    private double dist(Node n1, Node n2){
        double dist = 0.0;
        if ("ELEV".equalsIgnoreCase(n1.get("type")) && "ELEV".equalsIgnoreCase(n2.get("type"))) {
             dist = Math.sqrt(Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2));
        } else {
            int floorChange = floor.get(n1.get("floor")) - floor.get(n2.get("floor"));
            floorChange = Math.abs(floorChange);

            dist = floorChange * 30;
        }
        return dist;
    }
}