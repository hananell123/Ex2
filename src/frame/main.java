package frame;

import Server.Agent_Graph_Algo;
import Server.Game_Server_Ex2;
import api.game_service;
import api.edge_data;
import api.directed_weighted_graph;
//import Server.DWGraph;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import Server.Game_Server_Ex2;
import api.*;
import api.DWGraph_DS;
import api.NodeData;
import api.game_service;
import gameClient.MyFrame;

import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args) {

        DWGraph_DS g1= new DWGraph_DS();
        DWGraph_Algo  g1algo=new DWGraph_Algo();
        g1algo.init(g1);
        node_data a=new NodeData();
        node_data b=new NodeData();
        node_data c=new NodeData();
        geo_location a1=new geoLocation(150,300,0);
        geo_location a2=new geoLocation(380,100,0);
        geo_location a3=new geoLocation(200,200,0);
        a.setLocation(a1);
        b.setLocation(a2);
        c.setLocation(a3);
       // directed_weighted_graph gg=new DWGraph_DS();
//        gg.addNode(a);
//        gg.addNode(b);
//        gg.addNode(c);
        game_service game = Game_Server_Ex2.getServer(11);
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();




        myFrame screen=new myFrame(gg);
        screen.setVisible(true);
    }
}
