package myClasses;

import api.geo_location;
import api.node_data;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class NodeData implements node_data,Comparable<node_data>  {

    private int key,tag=0;
    private static int key_counter=0;
    private geo_location pos=null;
    private double weight=0;
    private String info="";


    public NodeData(){
        this.key=key_counter++;
    }

    public NodeData(int id ){
    this.key=id;

    }


    @Override
    public int getKey() {
        return key;
    }

    @Override
    public geo_location getLocation() {
        return pos;
    }

    @Override
    public void setLocation(geo_location p) {
        this.pos=p;

    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight=w;

    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info=s;

    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
    this.tag=t;
    }

    @Override
    public int compareTo( node_data o) {
        int ans = 0;
        if (this.getTag() - o.getTag() > 0) ans = 1;
        else if (this.getTag() - o.getTag() < 0) ans = -1;
        return ans;
    }

    //    @Override
//    public String toString() {
//        return  "pos: "+ pos + " id: " + key ;
//
//    }


}
