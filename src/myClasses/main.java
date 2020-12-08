package myClasses;

import Server.Game_Server_Ex2;
import api.game_service;
import frame.myFrameTEST;
import gameClient.MyFrame;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class main {
    public static void main(String[] r) {

        simpleGraph();
        //graphFromGame();
    }

        @Test
       static void simpleGraph(){
        DWGraph_DS g = new DWGraph_DS();
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


        myFrameTEST screen=new myFrameTEST(g);
        screen.setVisible(true);



        }

        @Test
   static void graphFromGame(){
      game_service game = Game_Server_Ex2.getServer(18);
      String s = game.getGraph();
        System.out.println(s);
         DWGraph_Algo graph = new DWGraph_Algo();

        try {
            PrintWriter pw = new PrintWriter(new File("gameGraph.json"));
            pw.write(s);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        }

        graph.load("C:\\Users\\hanan\\IdeaProjects\\ex2\\gameGraph.json");


       // MyFrame screen = new MyFrame(s);
        //screen.setVisible(true);

    }
}
