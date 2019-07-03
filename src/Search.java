import java.util.*;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */
public class Search {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;
    private Set<Node> settledNodes;
    private Set<Node> unsettledNodes;
    private Map<Node,Node> predecessors;
    private Map<Node, Integer> distance;
    private Graph graph;
    private Heuristic heuristic;
    private ArrayList<Job> joblist;
    public Integer cost;
    public Integer hcost;
    public int nodeCount;

    /**
     * Constructs a search object given the graph, the jobs that have to be completed and the stratergy the search is meant to use
     * @param graph
     * @param joblist
     * @param stratergy
     */
    public Search(Graph graph, ArrayList<Job> joblist, Stratergy stratergy) {
        this.heuristic = new Heuristic(stratergy);
        this.graph = graph;
        this.nodes = graph.getNodes();
        this.edges = graph.getEdges();
        settledNodes = new HashSet<Node>();
        unsettledNodes = new HashSet<Node>();
        distance = new HashMap<Node,Integer>();
        predecessors = new HashMap<Node,Node>();
        this.joblist = joblist;
        cost = 0;
        hcost = 0;
        nodeCount = 0;
    }

    /**
     * returns the path cost of the search
     * @return
     */
    public Integer getCost() {
        return cost;
    }

    /**
     * returns the total Heuristic cost of a search
     * @return
     */
    public Integer getHcost() {
        return hcost;
    }


    /**
     * Returns the number of nodes who's neighbours have all been evaluated.
     * @return
     */
    public int getNodeCount() {
        return nodeCount;
    }

    /**
     * resets the cost and heuristic cost values
     * In case of a new search using the same object, this function allows us to reset the cost values
     * so that the costs reflect only the costs of the new search.
     */
    public void reset() {
        cost = 0;
        hcost = 0;
        nodeCount = 0;
    }

    /**
     * Returns the shortest path between two nodes.
     * Once every neighbour of the node is evaluated,
     * it gets pushed into the settled set.Once the destination node enters the settled set, the search has finished.
     * @param source
     *          The node the path starts from
     * @param destination
     *          The node that the path ends at
     * @return
     *          Returns a linked list of nodes. The order of the nodes represent the order of nodes in the path.
     */
    public LinkedList<Node> execute(Node source, Node destination) {
        settledNodes = new HashSet<Node>();
        unsettledNodes = new HashSet<Node>();
        distance = new HashMap<Node,Integer>();
        predecessors = new HashMap<Node,Node>();
        distance.put(source,0);
        unsettledNodes.add(source);
        while(unsettledNodes.size()>0){
            Node current = getMinimum(unsettledNodes);
            settledNodes.add(current);
            //System.out.println(current.getCity()+" Is in 'Settled'");
            unsettledNodes.remove(current);
            if (current.equals(destination)) {
                break;
            }
            findMinimalDistance(current);
        }
        nodeCount = settledNodes.size();
        //We start tracing back from the destination node after the loop terminates
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = destination;
        path.add(destination);
        if(predecessors.get(step) == null)
            return null;
        while (predecessors.get(step) != null) {
            cost = cost + graph.getWeight(step,predecessors.get(step));
            hcost = hcost + getDistance(predecessors.get(step),step);
            step = predecessors.get(step);
            path.add(step);
        }
        //System.out.println("Hcost : "+hcost);
        Collections.reverse(path);

        return path;
    }

    /**
     * Returns the node with the smallest heuristic cost from the source among a set of nodes
     * @param nodes
     * @return
     */
    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node node:nodes) {
            if (minimum == null)
                minimum = node;
            else if (getShortestDistance(node) < getShortestDistance(minimum))
                minimum = node;
        }
        return minimum;
    }

    /**
     * Returns the heuristic cost to a node from the source
     * @param node
     * @return
     */
    public int getShortestDistance(Node node) {
        Integer d = distance.get(node);
        if (d == null)
            return Integer.MAX_VALUE;
        else
            return d;
    }

    /**
     * Returns the heuristic cost between two adjacent nodes.
     * @param source
     * @param destination
     * @return
     */
    public int getDistance(Node source, Node destination) {
        return heuristic.getFn(source,destination,graph,joblist);
    }

    /**
     * Given a node, it checks all its neighbours to re evaluate the shortest path from the source node to each one
     * of the neighbours. It also stores the shortest neighbour each node has in a hashmap. unevaluated neighbours are added to the unsettled set
     * so that they can be evaluated on the next cycle
     * @param from
     */
    public void findMinimalDistance(Node from) {
        ArrayList<Node> adjacentNodes = getNeighbours(from);
        //System.out.print("Neighbours: ");
        for (Node to: adjacentNodes) {
            if (getShortestDistance(to) > getShortestDistance(from) + getDistance(from, to)) {
                distance.put(to, getShortestDistance(from) + getDistance(from, to));
                predecessors.put(to, from);
                unsettledNodes.add(to);
            }
        }
    }

    /**
     * Returns a list of all neighbours a node has
     * @param node
     * @return
     */
    public ArrayList<Node> getNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        //System.out.print("Finding Neighbours for :"+node.getCity()+" :");
        for(Edge current:edges)
            if (current.getSource().equals(node) && !settledNodes.contains(current.getDestination())) {
            //    System.out.print(" "+current.getDestination().getCity());
                neighbours.add(current.getDestination());
            }

        return neighbours;
    }



}
