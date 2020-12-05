package frame;
import api.*;
import api.DWGraph_DS;
import api.NodeData;

import javax.swing.*;
import java.awt.*;
import java.awt.color.*;
import java.awt.event.MouseListener;

public class MyPanel extends JPanel  {
    directed_weighted_graph graph;

    public MyPanel(){
        this.setBackground(Color.pink);
    }
    public void init(directed_weighted_graph g1){
        graph=g1;
    }

    public void DrawGraph(directed_weighted_graph g){
        for(node_data n:g.getV()){

        }
    }
    public void paintComponent(Graphics g){
        repaint();
        super.paintComponent(g);
        g.fillOval( 100,100,30,30);
        g.fillOval( 200,200,30,30);


       for (node_data n:graph.getV()){
            geo_location loc=n.getLocation();
           System.out.println(loc.x());
           System.out.println(loc.y());
           g.fillOval( (int) loc.x(),(int) loc.y(),30,30);
        }
       // for( n:((DWGraph_DS)(graph)).getEdges().values(){

        }


    }



