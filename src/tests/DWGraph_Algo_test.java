

package tests;


import myClasses.DWGraph_Algo;
import myClasses.DWGraph_DS;
import myClasses.NodeData;
import org.junit.jupiter.api.Test;

public class DWGraph_Algo_test {




    DWGraph_Algo build_unConnect() {
        DWGraph_DS unConnected = new DWGraph_DS();
        NodeData temp;
        for(int i=0;i<10;i++) {
            temp = new NodeData();
            unConnected.addNode(temp);
        }

        unConnected.connect(2,1,3);
        unConnected.connect(1,2,8);
        unConnected.connect(4,1,7);
        unConnected.connect(4,1,7);//double
        unConnected.connect(4,3,6);
        unConnected.connect(6,5,5);
        unConnected.connect(7,6,1);
        unConnected.connect(6,9,3);
        unConnected.connect(9,6,4);
        unConnected.connect(0,8,2.7);//mc=19
        unConnected.connect(0,20,2.7);// not contain node 20
        unConnected.connect(0,8,3);//mc++ edge_size not++
        unConnected.connect(7,5,10);
        unConnected.connect(5,4,2);
        unConnected.connect(9,7,1);

        DWGraph_Algo unConnectAlgo=new DWGraph_Algo();
        unConnectAlgo.init(unConnected);

        return unConnectAlgo;
    }


    @Test
    void save_load() {
    DWGraph_Algo g = new DWGraph_Algo();
   // g = build_unConnect();
  // g.save("testSave");
   g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A0");
 g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A1");
//    //g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A2");
//    g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A3");
//    g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A4");
//    g.load("C:\\Users\\Hodaya\\IdeaProjects\\ex2\\data\\A5");


    }



}
