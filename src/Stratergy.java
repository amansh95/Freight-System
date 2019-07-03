import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */
public interface Stratergy {
    public int fn(Node source, Node destination, Graph graph, ArrayList<Job> joblist);
}
