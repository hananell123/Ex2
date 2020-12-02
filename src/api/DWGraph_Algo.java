package api;

import java.util.*;

import static java.lang.Double.POSITIVE_INFINITY;


public class DWGraph_Algo implements dw_graph_algorithms {
    directed_weighted_graph gAlgo=new DWGraph_DS();
    @Override
    public void init(directed_weighted_graph g) {
        this.gAlgo=g;
        System.out.println("init succsesfully");
    }

    @Override
    public directed_weighted_graph getGraph() {
        return gAlgo;
    }

    @Override
    public directed_weighted_graph copy() {
        directed_weighted_graph copy=new DWGraph_DS((DWGraph_DS) gAlgo);
        return copy;

    }

    @Override
    public boolean isConnected() {
        if(gAlgo.nodeSize()==0 ||gAlgo.nodeSize()==1) return true;
        int counter=1,firstKey;
        for(node_data i:gAlgo.getV()){
            i.setTag(0);
        }

        node_data first=gAlgo.getV().iterator().next();
        first.setTag(1);
        firstKey=first.getKey();
        LinkedList<Integer> fakeQ=new LinkedList<>();
        fakeQ.add(firstKey);
        while (!fakeQ.isEmpty()){
            int current=fakeQ.removeFirst();
            for(edge_data j:gAlgo.getE(current)){
                node_data tempNode = gAlgo.getNode(j.getDest());
                if(tempNode.getTag()==0){
                    fakeQ.addLast(tempNode.getKey());
                    counter++;
                    tempNode.setTag(1);
                }
            }
        }
        if(counter!=gAlgo.nodeSize()) return false;
        counter=1; // everyone tag=1
        fakeQ.addLast(firstKey);
        gAlgo.getNode(firstKey).setTag(0);
        while(!fakeQ.isEmpty()){
            int current=fakeQ.removeFirst();

            for(int i:((DWGraph_DS)(gAlgo)).getPointOnMe(current)){
                if(gAlgo.getNode(i).getTag()==1){
                    gAlgo.getNode(i).setTag(0);
                    fakeQ.addLast(i);
                    counter++;
                }
            }

        }
        System.out.println();
        if(counter==gAlgo.nodeSize()) return true;
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if(gAlgo.getNode(src)==null ||gAlgo.getNode(dest)==null) return -1;
        if(gAlgo.getE(src)==null || ((DWGraph_DS)(gAlgo)).getPointOnMe(dest).isEmpty()) return -1;
        if(src==dest)return 0;
        HashMap<Integer, Double> distance = new HashMap<>();
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
        if(distance.get(dest)==POSITIVE_INFINITY) return -1;
        return distance.get(dest);
    }





    @Override
    public List<node_data> shortestPath(int src, int dest) {
        HashMap<Integer,Integer>parents=new HashMap<>();
        LinkedList<node_data>ans=new LinkedList<>();

        if(gAlgo.getNode(src)==null ||gAlgo.getNode(dest)==null) return null;
        if(gAlgo.getE(src)==null || ((DWGraph_DS)(gAlgo)).getPointOnMe(dest).isEmpty()) return null;
        if(src==dest){
             ans.addLast(gAlgo.getNode(src));
             return ans;
        }
        HashMap<Integer, Double> distance = new HashMap<>();
        if (gAlgo.getNode(src) == null || gAlgo.getNode(dest) == null) return null;
        if (gAlgo.getE(src).isEmpty()) return null;

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
                    parents.put(tempNode.getKey(),u.getKey());
                    q.add(tempNode);
                }
            }
            u.setTag(1);
        }
        //if(distance.get(dest)==POSITIVE_INFINITY) return -1;
        //return distance.get(dest);
        int i=dest;
        ans.add(gAlgo.getNode(i));
        while(i!=src){
            ans.addFirst(gAlgo.getNode(parents.get(i)));
            i=parents.get(i);
        }
        if(ans.size()==1) return null;
        return ans;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return false;
    }
}
