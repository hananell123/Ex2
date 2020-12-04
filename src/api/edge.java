package api;

public class edge implements edge_data{
    private int src,dest,weight_tag;
    private double w;
    private String edge_info="";


    public edge(int src,int dest,double edge_weight){
        this.src=src;
        this.dest=dest;
        this.w=edge_weight;

    }


    /*public edge(edge other){
        other.weight_tag=this.weight_tag;
        other.edge_info=this.edge_info;
        other.edge_weight=this.edge_weight;
        other.dest=this.dest;
        other.src=this.src;
    }*/


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
