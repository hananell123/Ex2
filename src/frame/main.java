package frame;

import Server.Game_Server_Ex2;
import api.*;

public class main {
    public static void main(String[] args) {


      game_service game = Game_Server_Ex2.getServer(2);
      String s = game.getGraph();
        System.out.println(s);
     // DWGraph_DS g = new DWGraph_DS();
     DWGraph_Algo graph = new DWGraph_Algo();

     graph.load(s);
        /*
        NodeData a = new NodeData();
        NodeData b = new NodeData();
        NodeData c = new NodeData();

        geoLocation ag = new geoLocation(1,1,0);
        geoLocation bg = new geoLocation(200,200,0);
        geoLocation cg = new geoLocation(300,300,0);

        a.setLocation(ag);
        b.setLocation(bg);
        c.setLocation(cg);

        g.addNode(a);
        g.addNode(b);
        g.addNode(c);

        g.connect(0,1,2);
        g.connect(0,2,2);
 myFrame screen=new myFrame(g)
   screen.setVisible(true);


        */
        myFrame screen=new myFrame(graph.getGraph());

        screen.setVisible(true);

    }
}
