package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{

    private HashMap <Integer,HashMap<Integer,edge_data>>the_great_graph=new HashMap<Integer,HashMap<Integer,edge_data>>();
    private HashMap<Integer,node_data> all_nodes=new HashMap<Integer,node_data>();
    private HashMap <Integer,HashMap<Integer,Integer>>point_on_me=new HashMap<Integer,HashMap<Integer,Integer>>();
    private int mc=0,edge_size=0;

    public DWGraph_DS(){

    }
    public DWGraph_DS(DWGraph_DS other){
       this.the_great_graph=new HashMap<Integer,HashMap<Integer,edge_data>>();
        this.all_nodes=new HashMap<Integer,node_data>();
       this.point_on_me=new HashMap<Integer,HashMap<Integer,Integer>>();
        this.all_nodes.putAll(other.all_nodes);
        for(int i:all_nodes.keySet()){
            this.the_great_graph.put(i,other.the_great_graph.get(i));
            this.point_on_me.put(i,other.point_on_me.get(i));
        }
        this.edge_size=other.edge_size;




    }






    @Override
    public node_data getNode(int key) {
        if(!all_nodes.containsKey(key)) return null;
        return all_nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(!all_nodes.containsKey(src)||!all_nodes.containsKey(dest)) return null;
        if(!the_great_graph.get(src).containsKey(dest)) return null;
        return the_great_graph.get(src).get(dest);
    }

    @Override
    public void addNode(node_data n) {
        if(all_nodes.containsKey(n.getKey()))return;
        HashMap<Integer,edge_data>temp=new HashMap<>();
        HashMap temp2=new HashMap();
        point_on_me.put(n.getKey(),temp2);
        all_nodes.put(n.getKey(),n);

        the_great_graph.put(n.getKey(),temp);
        mc++;

    }

    @Override
    public void connect(int src, int dest, double w) {
        if(src==dest)return;
        if(!all_nodes.containsKey(src)||!all_nodes.containsKey(dest)) return ;
        if(the_great_graph.get(src).containsKey(dest)){//already ni
            if(the_great_graph.get(src).get(dest).getWeight()==w)return;
            ((edge)(the_great_graph.get(src).get(dest))).setEdge_weight(w);
            mc++;


        }
        else {
            edge_data temp=new edge(src,dest,w);
            the_great_graph.get(src).put(dest,temp);
            point_on_me.get(dest).put(src,src);
            edge_size++;
            mc++;
        }



    }

    @Override
    public Collection<node_data> getV() {
        return all_nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return the_great_graph.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        if (!all_nodes.containsKey(key)) return null;
        int i = 0;
        removeEdge(key, i);
        for (int j : the_great_graph.get(key).keySet()) {

            removeEdge(key, i);
            i = j;
        }
        removeEdge(key, i);

        for(int j1:point_on_me.get(key).values()){
            removeEdge(j1,key);
        }
        the_great_graph.remove(key);
        point_on_me.remove(key);
         mc++;
         return all_nodes.remove(key);
    }
    public Collection<Integer> getPointOnMe(int key) {
        return point_on_me.get(key).values();
    }





    @Override
    public edge_data removeEdge(int src, int dest) {
        if(!all_nodes.containsKey(src)||!all_nodes.containsKey(dest)) return null;
        if(!the_great_graph.get(src).containsKey(dest)) return null;
        mc++;
        edge_size--;
        return the_great_graph.get(src).remove(dest);

    }

    @Override
    public int nodeSize() {
        return all_nodes.size();
    }

    @Override
    public int edgeSize() {
        return edge_size;
    }

    @Override
    public int getMC() {
        return mc;
    }
}
