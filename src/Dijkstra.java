import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */
public class Dijkstra implements Stratergy{
    @Override
    public int fn(Node source, Node destination, Graph g, ArrayList<Job> joblist) {
        return g.getWeight(source,destination);
    }
}
