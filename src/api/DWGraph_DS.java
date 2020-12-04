package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph{

    private HashMap <Integer,HashMap<Integer,edge_data>>Edges=new HashMap<Integer,HashMap<Integer,edge_data>>();
    private HashMap<Integer,node_data> Nodes=new HashMap<Integer,node_data>();
    private HashMap <Integer,HashMap<Integer,Integer>>point_on_me=new HashMap<Integer,HashMap<Integer,Integer>>();
    private HashMap<Integer,edge_data> edges = new  HashMap<Integer,edge_data>();
    private int mc=0,edge_size=0;

    public DWGraph_DS(){

    }
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


    @Override
    public node_data getNode(int key) {
        if(!Nodes.containsKey(key)) return null;
        return Nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return null;
        if(!Edges.get(src).containsKey(dest)) return null;
        return Edges.get(src).get(dest);
    }

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

    @Override
    public void connect(int src, int dest, double w) {
        if(src==dest)return;
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return ;
        if(Edges.get(src).containsKey(dest)){//already ni
            if(Edges.get(src).get(dest).getWeight()==w)return;
            ((edge)(Edges.get(src).get(dest))).setEdge_weight(w);
            mc++;


        }
        else {
            edge_data temp=new edge(src,dest,w);
            Edges.get(src).put(dest,temp);
            point_on_me.get(dest).put(src,src);
            edge_size++;
            edges.put(temp.getSrc() , temp);
            mc++;
        }



    }

    @Override
    public Collection<node_data> getV() {
        return Nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        return Edges.get(node_id).values();
    }

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
    public Collection<Integer> getPointOnMe(int key) {
        return point_on_me.get(key).values();
    }





    @Override
    public edge_data removeEdge(int src, int dest) {
        if(!Nodes.containsKey(src)||!Nodes.containsKey(dest)) return null;
        if(!Edges.get(src).containsKey(dest)) return null;
        mc++;
        edge_size--;
        return Edges.get(src).remove(dest);

    }

    @Override
    public int nodeSize() {
        return Nodes.size();
    }

    @Override
    public int edgeSize() {
        return edge_size;
    }

    @Override
    public int getMC() {
        return mc;
    }

    public HashMap<Integer,edge_data> getEdges() {
        return edges;

    }
}


