/**
 * Created by Rahul Gururaj on 5/4/2017.
 */

/**
 * Represents a city. Each Node contains the name of the city and the unloading cost.
 */
public class Node {
    private String city;
    private int unloadCost;

    /**
     * Constructs a Node object given a city name and its unloading cost
     * @param city
     *          name of the city
     * @param unloadCost
     *          unloading cost
     */
    public Node(String city, int unloadCost) {
        this.city = city;
        this.unloadCost = unloadCost;
    }

    /**
     * returns the name of the city
     * @return
     */
    public String getCity() { return city;}

    public int getUnloadCost() { return unloadCost; }

    /**
     * Allows us to check if two nodes have the same attributes and are of the same object type
     * @param obj The arguement we'll be using to pass the Node we're comparing
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        return true;
    }

    /**
     * Returns the name of the city
     * @return
     */
    @Override
    public String toString() {
        return city;
    }
}
