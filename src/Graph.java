import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */

/**
 * Represents a map with different cities( Nodes) and the paths between them using Edges
 * We maintain a separate arraylist of all cities and all edges
 */
public class Graph {
    private ArrayList<Node> nodes;
    private ArrayList<Edge> edges;

    /**
     * Constructs a graph given an arraylist of nodes and edges
     * @param nodes
     * @param edges
     */
    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = new ArrayList<Node>(nodes);
        this.edges = new ArrayList<Edge>(edges);
    }

    public Graph() {
        this.nodes = new ArrayList<Node>();
        this.edges = new ArrayList<Edge>();
    }

    /**
     * Returns arraylist of edges the graph contains
     * @return
     */
    public ArrayList<Edge> getEdges() {
        return edges;
    }

    /**
     * Returns arraylist of Nodes the graph contains
     * @return
     */
    public ArrayList<Node> getNodes() {
        return nodes;
    }

    /**
     * Checks if a given city is on the graph and returns a true or fasle value
     * @param city
     * @return
     */
    public boolean isNode(String city) {
        for (Node node : nodes)
            if (city.equals(node.getCity()))
                return true;
        return false;
    }

    /**
     * Checks if there is a direct edge (path) between 2 given nodes (cities).
     * @param source
     * @param destination
     * @return
     */
    public boolean isEdge(Node source, Node destination) {
        for (Edge edge:edges)
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination))
                return true;
        return false;
    }

    /**
     * Returns the weight of the edge between two given Nodes (cities).
     * @param source
     * @param destination
     * @return
     */
    public int getWeight(Node source, Node destination) {
        if(source.equals(destination))
            return 0;
        for (Edge edge: edges)
            if (edge.getSource().equals(source) && edge.getDestination().equals(destination))
                return edge.getWeight();
        throw new RuntimeException("No such edge found");
    }

    /**
     * Returns a Node object of a city given just its name.
     * @param city
     *              Is the name of the city passed as a string
     * @return
     */
    public Node getNode(String city) {
        for (Node node:nodes)
            if (city.equals(node.getCity())){
            return node;
            }
        throw new RuntimeException("This shouldn't happen");
    }

    /**
     * Adds a Node onto the graph given a city and its unload cost
     * @param city is the name of the city
     * @param unloadCost is its unload cost
     */
    public void addNode(String city, int unloadCost) {
        if (isNode(city))
                throw new RuntimeException("City: "+city+" already lready exists");
        nodes.add(new Node(city, unloadCost));
    }

    /**
     * Adds an edge between given two nodes.
     * @param source the path starts from here
     * @param destination and ends here
     * @param weight amd is this long
     */
    public void addEdge (String source, String destination, int weight) {
         if (!isNode(source) || !isNode(destination))
             throw new RuntimeException("These cities don't exist");
            edges.add(new Edge(getNode(source), getNode(destination),weight));
    }

    /**
     * Returns a string detailing all the edges the graph has and their weights.
     * @return
     */
    @Override
    public String toString() {
        String allEdges = "";
        for (Edge edge:edges)
            allEdges = allEdges + "\n" + edge;
        return allEdges;
    }

}



