/**
 * Created by Rahul Gururaj on 5/6/2017.
 */

/**
 * represents a single job on the joblist
 * which will be an arrayist of job objectts
 */
public class Job {
    public Node source;
    public Node destination;

    /**
     * Constructs a Job object given:
     * @param source where the job starts
     * @param destination where job ends
     */
    public Job(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }
}
