/**
 * Created by Rahul Gururaj on 5/5/2017.
 */
/**
 * The main class that reads the user input and passes its values to
 * a manager object through which all the operations are called.
 */

import java.io.FileNotFoundException;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class FreightSystem {

    public static void main(String[] args) throws ParseException, FileNotFoundException {

           Scanner sc = new Scanner(new FileReader(args[0]));

        //catch (FileNotFoundException e) {}
        Graph g = new Graph();
        ArrayList<Job> jerbs = new ArrayList<>();
        String output="";
        while(sc.hasNextLine()){
            String s = sc.nextLine();
            if(s.split(" ")[0].equals("Unloading")){
                int unloadCost = Integer.parseInt(s.split(" ")[1]);
                String city = s.split(" ")[2];
                g.addNode(city, unloadCost);

            } else if(s.split(" ")[0].equals("Cost")){
                String from = s.split(" ")[2];
                String to = s.split(" ")[3];
                int weight = Integer.parseInt(s.split(" ")[1]);
                g.addEdge(from,to, weight);
                g.addEdge(to,from, weight);

            }else if(s.split(" ")[0].equals("Job")) {
                String from = s.split(" ")[1];
                String to = s.split(" ")[2];
                jerbs.add(new Job(g.getNode(from),g.getNode(to)));
            }
        }
   // System.out.println(g.toString());

        Manager manager = new Manager(g);
        manager.setJoblist(jerbs);
        manager.findSolution();
        output+=manager.printTotalNodeCount();
        output+=manager.printCost();
        output+=manager.printPath();
        System.out.println(output);
    } //End of main

}