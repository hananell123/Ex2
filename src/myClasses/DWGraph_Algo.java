package myClasses;

import api.directed_weighted_graph;
import api.dw_graph_algorithms;
import api.edge_data;
import api.node_data;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.Double.POSITIVE_INFINITY;


public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph gAlgo = new DWGraph_DS();
    HashMap<Integer, Double> distance=new HashMap<>();

/**
 * node vairble is a int and for the shortesr path method we need an double
 * this class is a inner class that contains NodeData and double varible for path weight
 */
    private class NodeForPath implements Comparable<NodeForPath> {
        private node_data n;
        private double w;

        public NodeForPath(node_data n, double weight) {
            this.n = n;
            this.w = weight;
        }

        public void setN(node_data n) {
            this.n = n;
        }

        public void setW(double w) {
            this.w = w;
        }

        public double getW() {
            return w;
        }

        public node_data getN() {
            return n;
        }

    /**
     * compere to NodeForPath by their weight
     * @param o
     * @return
     */
    @Override
        public int compareTo(NodeForPath o) {
            int ans = 0;
            if (this.getW() - o.getW() > 0) ans = 1;
            else if (this.getW() - o.getW() < 0) ans = -1;
            return ans;

        }
    }

    /**
     * initialize the graph to directed weighted graph algo
     * @param g
     */
    @Override
    public void init(directed_weighted_graph g) {
        this.gAlgo = g;

    }

    @Override
    public directed_weighted_graph getGraph() {
        return gAlgo;
    }

    /**
     * make a deep copy of the graph (using the copy constructors)
     * @return
     */
    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copy = new DWGraph_DS((DWGraph_DS) gAlgo);
        return copy;

    }

    /**
     * this function check the connectivity of the graph
     * using BFS and revers BFS.
     * firs check from random node the path for all the other nodes and count them return false if one miss
     * second check that all the nodes have path to the same node in the firs part
     * @return
     */

    @Override
    public boolean isConnected() {
        if (gAlgo.nodeSize() == 0 || gAlgo.nodeSize() == 1) return true;
        int counter = 1, firstKey;
        for (node_data i : gAlgo.getV()) {
            i.setTag(0);
        }

        node_data first = gAlgo.getV().iterator().next();
        first.setTag(1);
        firstKey = first.getKey();
        LinkedList<Integer> fakeQ = new LinkedList<>();
        fakeQ.add(firstKey);
        while (!fakeQ.isEmpty()) {
            int current = fakeQ.removeFirst();
            for (edge_data j : gAlgo.getE(current)) {
                node_data tempNode = gAlgo.getNode(j.getDest());
                if (tempNode.getTag() == 0) {
                    fakeQ.addLast(tempNode.getKey());
                    counter++;
                    tempNode.setTag(1);
                }
            }
        }
        if (counter != gAlgo.nodeSize()) return false;
        // second part
        counter = 1;
        fakeQ.addLast(firstKey);
        gAlgo.getNode(firstKey).setTag(0);
        while (!fakeQ.isEmpty()) {
            int current = fakeQ.removeFirst();

            for (int i : ((DWGraph_DS) (gAlgo)).getPointOnMe(current)) {
                if (gAlgo.getNode(i).getTag() == 1) {
                    gAlgo.getNode(i).setTag(0);
                    fakeQ.addLast(i);
                    counter++;
                }
            }

        }
        System.out.println();
        if (counter == gAlgo.nodeSize()) return true;
        return false;
    }

    /**
     * find the shortest path from src node to dest node and return the distance
     * this mathod use Dijkstra algorithm using priority queue to minimise steps
     * every timee the algorithm take the node with the shortest distance from the src node and enter his
     * neighbers to the queue so when he get to the target node in the first time its guarantee that is the shortest path
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return -1;
        if (gAlgo.getE(src) == null || ((DWGraph_DS) (gAlgo)).getPointOnMe(dest).isEmpty()) return -1;
        if (src == dest) return 0;
        //HashMap<Integer, Double>
        distance = new HashMap<>();
        if (src == dest) return 0;
        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return -1;
        if (gAlgo.getE(src).isEmpty()) return -1;

        Queue<node_data> q = new PriorityQueue<node_data>();


        for (node_data t : gAlgo.getV()) {
            distance.put(t.getKey(), POSITIVE_INFINITY);
            t.setTag(0);
        }


        distance.put(src, 0.0);
        q.add(gAlgo.getNode(src));
        while (!q.isEmpty()) {

            node_data u = q.poll();

            for (edge_data v : gAlgo.getE(u.getKey())) {
                node_data tempNode = gAlgo.getNode(v.getDest());
                if (tempNode.getTag() == 0) {
                    if (distance.get(tempNode.getKey()) > distance.get(u.getKey()) + v.getWeight())
                        distance.put(tempNode.getKey(), distance.get(u.getKey()) + v.getWeight());
                    q.add(tempNode);
                }
            }
            u.setTag(1);
        }
        if (distance.get(dest) == POSITIVE_INFINITY) return -1;
        return distance.get(dest);
    }

    /**
     * Do the same as the last algorithm but also keep in HashMap the parent of every node
     * so when the algorithm finish his run we can extract list of node that show the real path
     * from src to dest
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {

        HashMap<Integer, Integer> parents = new HashMap<>();
        LinkedList<node_data> ans = new LinkedList<>();

        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return null;
        if (gAlgo.getE(src) == null || ((DWGraph_DS) (gAlgo)).getPointOnMe(dest).isEmpty()) return null;
        if (src == dest) {
            ans.addLast(gAlgo.getNode(src));
            return ans;
        }
        HashMap<Integer, NodeForPath> distance = new HashMap<>();
        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return null;
        if (gAlgo.getE(src).isEmpty()) return null;

        Queue<NodeForPath> q = new PriorityQueue<NodeForPath>();


        for (node_data t : gAlgo.getV()) {
            t.setTag(0);
            NodeForPath temp=new NodeForPath(t,POSITIVE_INFINITY);
            distance.put(t.getKey(), temp);
        }

        //distance.put(src, 0.0);
        q.add(distance.get(src));
        distance.get(src).setW(0.0);
        while (!q.isEmpty()) {

           // node_data u = q.poll();
            NodeForPath u=q.poll();

            for (edge_data v : gAlgo.getE(u.getN().getKey())) {
                NodeForPath tempNode = distance.get(v.getDest());
                if (tempNode.getN().getTag() == 0) {
                    if (tempNode.getW() > distance.get(u.getN().getKey()).getW() + v.getWeight()) {
                        tempNode.setW(distance.get(u.getN().getKey()).getW() + v.getWeight());
                        //distance.put(tempNode.getKey(), distance.get(u.getKey()) + v.getWeight());
                        parents.put(tempNode.getN().getKey(), u.getN().getKey());
                        q.add(tempNode);
                    }
                }
            }
            u.getN().setTag(1);
        }
        //if(distance.get(dest)==POSITIVE_INFINITY) return -1;
        //return distance.get(dest);
        int i = dest;
        ans.add(gAlgo.getNode(i));
        while (i != src) {
            ans.addFirst(gAlgo.getNode(parents.get(i)));
            i = parents.get(i);
        }
        if (ans.size() == 1) return null;
        return ans;
    }

    @Override

    /**
     * save the graph to the given path, using gson format
     */
    public boolean save(String file) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(gAlgo);
        System.out.println(json);

        //Write JSON to file
        try {
            PrintWriter pw = new PrintWriter(new File("Graph.json"));
            pw.write(json);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * load graph from the given file path
     * @param file - file name of JSON file
     * @return
     */
    @Override
    public boolean load(String file) {

        try {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(DWGraph_DS.class, new GraphJsonDeserializer());
            Gson gson = builder.create();

            FileReader reader = new FileReader(file);
            gAlgo = gson.fromJson(reader, DWGraph_DS.class); 

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
