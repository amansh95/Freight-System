import java.util.ArrayList;

/**
 * Created by Rahul Gururaj on 5/6/2017.
 */

/**
 * This class implements stratergy so it can be used as a heuistic in the search.
 *
 * f(n) = g(n)+h(n);
 * where f(n) is the estimated cost, h(n) is the heuristic cost and g(n) is the weight of the edge.
 *
 * h(n) is first set as the average weight of all edges in the graph
 * if the destination of the edge is the start of a job, h(n) is reduced by a fraction.
 * if the destination is the start of a chain of consecutive jobs, the deduction is bigger.
 */
public class Astar implements Stratergy{
    @Override
    public int fn(Node source, Node destination, Graph g, ArrayList<Job> joblist) {
        Integer max=0, min = Integer.MAX_VALUE, avg=0,denominator=6;
        //Get max and min weights;
        for(Edge edge:g.getEdges()) {
            avg += edge.getWeight();
            /*if(min>edge.getWeight())
                min = edge.getWeight();
            if(max>edge.getWeight())
                max = edge.getWeight();*/
        }
        avg /= g.getEdges().size();
        int hn = avg, deduct=avg/denominator;
        for(Job job:joblist)
            if (source.equals(job.source)) {
                hn -= deduct;
                break;
            }
        String s="";
        Node current = destination;
        Node previous= null;
        int i=0;
        while(i<denominator) {
            for(Job job:joblist) {
                if(current.equals(job.source)) {
                    s+=" : "+current.getCity()+" to "+job.destination.getCity();
                    hn -= deduct;
                    current = job.destination;
                    break;
                }
            }
            previous = current;i++;
        }
        //System.out.println("Deductions :"+s+"\nScore: "+hn);

        return g.getWeight(source,destination) + hn;
    }


}
