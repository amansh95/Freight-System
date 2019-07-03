import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */

/**
 * This class is used to pass a heuristic object to the Search so that the strategy used can be changed with ease.
 * The strategy in this case being the way we calculate the heuristic cost.
 */
public class Heuristic {
    private Stratergy stratergy;

    public Heuristic(Stratergy stratergy) {
        this.stratergy = stratergy;
    }

    /**
     * Calculates the f(n) value given:
     * @param source
     *              source node of the edge
     * @param destination
     *              destination edge of the edge
     * @param g
     *              the graph we're using
     * @param joblist
     *              the jobllist containing all the jobs.
     * @return
     */
    public int getFn(Node source, Node destination, Graph g,ArrayList<Job> joblist){
        return stratergy.fn(source, destination,g, joblist);
    }
}
