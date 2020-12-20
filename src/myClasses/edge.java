package myClasses;

import api.edge_data;

/**
 *
 */
public class edge implements edge_data {
    private int src,dest,weight_tag;
    private double w;
    private String edge_info="";

    /**
     * constructor of ddge int the graph
     * @param src node of the edge
     * @param dest node
     * @param edge_weight
     */
    public edge(int src,int dest,double edge_weight){
        this.src=src;
        this.dest=dest;
        this.w=edge_weight;

    }

    /**
     * getters and setters
     * @return
     */

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
            return dest;
    }

    @Override
    public double getWeight() {
        return w;
    }

    @Override
    public String getInfo() {
        return edge_info;
    }

    @Override
    public void setInfo(String s) {
        this.edge_info=s;

    }

    @Override
    public int getTag() {
        return weight_tag;
    }

    @Override
    public void setTag(int t) {
        this.weight_tag=t;

    }

    public void setEdge_weight(double edge_weight) {
        this.w = edge_weight;
    }
}
