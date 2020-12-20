package myClasses;

import api.directed_weighted_graph;
import api.edge_data;
import api.node_data;

import java.util.Collection;
import java.util.HashMap;

/**
 * this class contains graph and build mathods of it.
 */
public class DWGraph_DS implements directed_weighted_graph {

    private HashMap <Integer,HashMap<Integer, edge_data>>Edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
    private HashMap<Integer, node_data> Nodes=new HashMap<Integer,node_data>();
    private HashMap <Integer,HashMap<Integer,Integer>>point_on_me=new HashMap<Integer,HashMap<Integer,Integer>>();
    private HashMap<Integer,edge_data> edges = new  HashMap<Integer,edge_data>();
    private double allEdgeWeight=0;
    private int mc=0,edge_size=0;

    public DWGraph_DS(){

    }

    /**
     * copy constructor for "Copy" mathod in DWGraph_Algo class
     * @param other
     */
    public DWGraph_DS(DWGraph_DS other){
       this.Edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.Nodes=new HashMap<Integer,node_data>();
       this.point_on_me=new HashMap<Integer,HashMap<Integer,Integer>>();
        this.Nodes.putAll(other.Nodes);
        for(int i:Nodes.keySet()){
            this.Edges.put(i,other.Edges.get(i));
            this.point_on_me.put(i,other.point_on_me.get(i));
        }
        this.edge_size=other.edge_size;
    }

    /**
     * returns the node_data by the node_id,
     * @param key - the node_id
     * @return the node_data by the node_id, null if none.
     */
    @Override
    public node_data getNode(int key) {
        if(!Nodes.containsKey(key)) return null;
        return Nodes.get(key);
    }
    /**
     * returns the data of the edge (src,dest), null if none.
     * @param src
     * @param dest
     * @return
     */
    @Override
    public edge_data getEdge(int src, int dest) {
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return null;
        if(!Edges.get(src).containsKey(dest)) return null;
        return Edges.get(src).get(dest);
    }
    /**
     * adds a new node to the graph with the given node_data.
     * @param n
     */
    @Override
    public void addNode(node_data n) {
        if(Nodes.containsKey(n.getKey()))return;
        HashMap<Integer,edge_data>temp=new HashMap<>();
        HashMap temp2=new HashMap();
        point_on_me.put(n.getKey(),temp2);
        Nodes.put(n.getKey(),n);

        Edges.put(n.getKey(),temp);
        mc++;

    }
    /**
     * Connects an edge with weight w between node src to node dest.
     * @param src - the source of the edge.
     * @param dest - the destination of the edge.
     * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */

    @Override
    public void connect(int src, int dest, double w) {
        if(src==dest)return;
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return ;
        if(Edges.get(src).containsKey(dest)){//already ni
            if(Edges.get(src).get(dest).getWeight()==w)return;
           allEdgeWeight-= Edges.get(src).get(dest).getWeight();
           allEdgeWeight+=w;
            ((edge)(Edges.get(src).get(dest))).setEdge_weight(w);
            mc++;


        }
        else {
            edge_data temp=new edge(src,dest,w);
            Edges.get(src).put(dest,temp);
            point_on_me.get(dest).put(src,src);
            edge_size++;
            allEdgeWeight+=w;
            edges.put(temp.getSrc() , temp);
            mc++;
        }



    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the nodes in the graph.
     * @return Collection<node_data>
     */

    @Override
    public Collection<node_data> getV() {
        return Nodes.values();
    }
    /**
     * This method returns a pointer (shallow copy) for the
     * collection representing all the edges getting out of
     * the given node (all the edges starting (source) at the given node).
     * @return Collection<edge_data>
     */

    @Override
    public Collection<edge_data> getE(int node_id) {
        return Edges.get(node_id).values();
    }
    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return the data of the removed node (null if none).
     * @param key
     */
    @Override
    public node_data removeNode(int key) {
        if (!Nodes.containsKey(key)) return null;
        int i = 0;
        removeEdge(key, i);
        for (int j : Edges.get(key).keySet()) {

            removeEdge(key, i);
            i = j;
        }
        removeEdge(key, i);

        for(int j1:point_on_me.get(key).values()){
            removeEdge(j1,key);
        }
        Edges.remove(key);
        point_on_me.remove(key);
         mc++;
         return Nodes.remove(key);
    }

    /**
     * Deletes the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * @return collection of keys of all the nodes that have edge to the node with the given key
     * @param key
     */
    public Collection<Integer> getPointOnMe(int key) {
        return point_on_me.get(key).values();
    }




    /**
     * Deletes the edge from the graph,
     * @param src
     * @param dest
     * @return the data of the removed edge (null if none).
     */
    @Override
    public edge_data removeEdge(int src, int dest) {
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return null;
        if(!Edges.get(src).containsKey(dest)) return null;
        mc++;
        edge_size--;
        return Edges.get(src).remove(dest);

    }
    /** Returns the number of vertices (nodes) in the graph.
     * Note: this method should run in O(1) time.
     * @return
     */
    @Override
    public int nodeSize() {
        return Nodes.size();
    }
    /**
     * Returns the number of edges (assume directional graph).
     * @return
     */
    @Override
    public int edgeSize() {
        return edge_size;
    }
    /**
     * Returns the Mode Count - for testing changes in the graph.
     * @return mc
     */
    @Override
    public int getMC() {
        return mc;
    }

    /**
     * Returns HashMap of all the edges.
     * @return edges
     */
    public HashMap<Integer,edge_data> getEdges() {
        return edges;
    }

    public double getWeightAvrg(){

        return allEdgeWeight/edge_size;
    }
}


