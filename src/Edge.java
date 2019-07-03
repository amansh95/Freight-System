/**
 * Created by Rahul Gururaj on 5/5/2017.
 */

/**
 * Represents the path from one city to another. The length of the path between the source and destination cities is represented with a weight.
 */
public class Edge {
    private Node source;
    private Node destination;
    private int weight;

    /**
     * Constructs an edge given the source and destination cities with the lenght/"weight" of the path
     * @param source
     * @param destination
     * @param weight
     */
    public Edge(Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    /**
     * Returns the source Node
     * @return
     */
    public Node getSource() {
        return source;
    }

    /**
     * Returns the destination Node
     * @return
     */
    public Node getDestination() {
        return destination;
    }

    /***
     * Returns the weight of the edge
     * @return
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Returns the string detailing the source, destination and the weight of the node
     * @return
     */
    @Override
    public String toString() {
        return source + " " + destination + ", weight =" + weight;
    }
}
