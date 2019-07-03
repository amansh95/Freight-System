import java.util.*;

/**
 * Created by Rahul Gururaj on 5/5/2017.
 */

/**
 * The central class that the user will be passing most commands through.
 *The class requires a graph, a list of jobs to be completed to function properly.
 * It stores the final path in a linkedlist of nodes.
 */
public class Manager {
    private Graph graph;
    public ArrayList<Job> joblist;
    private int totalCost;
    private int totalNodeCount;
    LinkedList<Node> finalPath;

    /**
     * Constructs a Manager object given a graph.
     * @param graph
     */
    public Manager(Graph graph) {
        this.graph = graph;
        joblist = new ArrayList<>();
        totalCost = 0;
        totalNodeCount = 0;
        finalPath = new LinkedList<>();
    }

    /**
     * Constructs a manager object but
     * a graph and a joblist will have to be added later on for it to function properly
     */
    public Manager() {
        joblist = new ArrayList<>();
        totalCost = 0;
        totalNodeCount = 0;
        finalPath = new LinkedList<>();
    }

    /**
     * Returns the total cost of executing all jobs
     * @return
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Adds a job to the joblist
     * @param source
     * @param destination
     */
    public void addJob (String source, String destination) {
        joblist.add(new Job(graph.getNode(source),graph.getNode(destination)));
    }

    /**
     * Sets a joblist given an arraylist of jobs
     * @param joblist
     */
    public void setJoblist(ArrayList<Job> joblist) {
        this.joblist = joblist;
    }

    /**
     * Prints all jobs that have to executed
     */
    public void printJobs() {
        for (Job job:joblist)
            System.out.println("Job from "+job.source.getCity()+" to "+job.destination.getCity());
    }

    /**
     * Checks if a there is a job in an arraylist of jobs such that the job starts from a node which is also passed
     * to this function
     * @param remainingJobs
     *              The joblist which will be searched
     * @param current
     *              The node from which the job is supposed to start
     * @return
     *              true/false value depending on if a job starts from the node.
     */
    public boolean isJobAtCurrent(ArrayList<Job> remainingJobs, Node current) {
        //Check if there are jobs from current node
        for (Job job : remainingJobs) {
            if (current.equals(job.source))
                return true;
        }
        return false;
    }


    /**
     * Removes a job from a joblist given the source and destination of the job that needs removing
     * @param source
     *              Source of the job
     * @param destination
     *          Destination of the job
     * @param list
     *          List from which the job needs to be removed
     */
    public void removeJob(Node source, Node destination, ArrayList<Job> list) {
        for (int i=0; i<list.size();i++)
            if (list.get(i).source.equals(source) && list.get(i).destination.equals(destination)){
                list.remove(i);
            }
    }

    /**
     * Finds the final path that needs to be taken to finish every job on the joblist
     */
    public void findSolution() {
        ArrayList<Job> remainingJobs = new ArrayList<>(joblist);
        Node current = graph.getNode("Sydney");
        finalPath = new LinkedList<Node>();
        finalPath.addFirst(current);
        Search search = new Search(graph,remainingJobs,new Astar());
        while(!remainingJobs.isEmpty()) {
            if(isJobAtCurrent(remainingJobs,current))
            {
                //Find the job with least heuristic costs and starts at current
                Integer minCost = Integer.MAX_VALUE;
                Node closest = new Node("current",100);
                for (Job job:remainingJobs)
                    if (current.equals(job.source)){
                        search.execute(current,job.destination);
                        totalNodeCount += search.getNodeCount();
                        //search.reset();
                       // System.out.println("Job Cost from"+current.getCity()+" to "+job.destination+ " is "+search.getDistance(current,job.destination));
                       // System.out.println("Job Cost from"+current.getCity()+" to "+job.destination+ " is "+search.getHcost());
                        if (minCost > search.getHcost()) {
                            closest = job.destination;
                            minCost = search.getHcost();
                          //  System.out.println("Job Cost from"+current.getCity()+" to "+job.destination+ " is "+search.getHcost());
                        }
                        search.reset();
                    }
                solve(current,closest);
                //Remove completed job from remainingJobs
                removeJob(current,closest,remainingJobs);
                current = closest;
        } else {
                Integer minCost = Integer.MAX_VALUE;
                Node closest = new Node("other", 100);
                //Find the closest city with a job
                for (Job job : remainingJobs) {
                    search.execute(current, job.source);
                    //totalCost += search.getCost();
                    totalNodeCount += search.getNodeCount();
                    // search.reset();
                    //System.out.println("empty Cost from"+current.getCity()+" to "+job.source +" is "+search.getDistance(current,job.source));
                   // System.out.println("Empty Cost from" + current.getCity() + " to " + job.source + " is " + search.getHcost());
                    if (minCost > search.getHcost()) {
                        minCost = search.getHcost();
                        closest = job.source;
//                        System.out.println("Empty Cost from" + current.getCity() + " to " + job.source + " is " + search.getHcost());
                    }
                    search.reset();
                }
                solve(current, closest);
                current = closest;
                removeJob(current,closest,remainingJobs);
            }
        }

    }


    /**
     * Finds the shortest path from a given source to a destination and adds that path
     * to the final path that will have to be taken to finish all jobs on the joblist
     * @param source
     * @param destination
     */
    public void solve(Node source, Node destination) {
        Search search = new Search(graph,joblist,new Astar());
        LinkedList<Node> path = search.execute(source,destination);
       // totalCost += search.getCost();
        totalNodeCount += search.getNodeCount();
        path.remove(0);
        finalPath.addAll(path);
    }

    /**
     * Returns a string that describes the shortest path that has already been computed
     * @return the string with the shortest path
     */
    public String printPath() {
        //System.out.println("Final Path is:");
       // for (Node node:finalPath)
        //    System.out.print(" -> "+node.getCity());
        String s="";
       // printJobs();
        for (int i=0;i+1<finalPath.size();i++){
            boolean flag= true;
            for(Job job:joblist)
                if (job.source.equals(finalPath.get(i)) && job.destination.equals(finalPath.get(i + 1))) {
                    s += "Job " + finalPath.get(i).getCity() + " to " + finalPath.get(i + 1).getCity() + "\n";
                    flag= false;
                    break;
                }
            if(flag)
            s += "Empty " + finalPath.get(i).getCity() + " to " + finalPath.get(i + 1).getCity() + "\n";
            //i++;
        }
       // System.out.print(s);
        return s;
    }

    /**
     * Get the total cost
     * @return
     *          Returns the totalcost of executing the job
     */
    public String printCost() {
        totalCost = 0;
        for(int i=0; i+1<finalPath.size();i++)
            totalCost += graph.getWeight(finalPath.get(i), finalPath.get(i + 1));
        for(Job job:joblist)
            totalCost += job.destination.getUnloadCost();
        //System.out.println("cost = "+totalCost);
        String s="cost = "+totalCost+"\n";
        return s;
    }

    /**
     * returns the total number of nodes that had all it's neighbours evaluated in order to find the final path
     * @return
     *          a string giving the number of nodes expanded.
     */
    public String printTotalNodeCount() {
        String s="";
        s+=totalNodeCount+" nodes expanded\n";
        return s;
    }


}
