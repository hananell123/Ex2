package tests;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.NodeData;

public class MyTest {
    public static void main(String[] args) {
        DWGraph_DS unConnected = new DWGraph_DS();
        NodeData temp;
        for(int i=0;i<10;i++) {
             temp = new NodeData();
            unConnected.addNode(temp);
        }// mc=10



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

        DWGraph_Algo algo=new DWGraph_Algo();
        algo.init(unConnected);
        DWGraph_DS copy=new DWGraph_DS(unConnected);

        System.out.println(algo.shortestPath(7,2));

        // mc=20, node_size=10, edge_size =9
        /*

        System.out.println("node size = "+unConnected.nodeSize());
        System.out.println("mc = "+unConnected.getMC());
        System.out.println("edge size = "+unConnected.edgeSize());

        unConnected.removeEdge(2,1);
        unConnected.removeEdge(8,9);// not connected
        unConnected.removeEdge(8,20);

        System.out.println("node size 10 = "+unConnected.nodeSize());
        System.out.println("edge size 8 = "+unConnected.edgeSize());
        System.out.println("mc 21 = "+unConnected.getMC());




        System.out.println("node size 9 = "+unConnected.nodeSize());
        System.out.println("edge size 4 = "+unConnected.edgeSize());
        System.out.println("mc 26 = "+unConnected.getMC());







        DWGraph_DS Connected = new DWGraph_DS();
        for(int i=1;i<5;i++) {
            temp = new NodeData();
            Connected.addNode(temp);
        }
        System.out.println(Connected.getPointOnMe(10));


        Connected.connect(12,10,2);
        Connected.connect(10,12,4);
        Connected.connect(11,10,7);
        Connected.connect(10,11,6);//double
        Connected.connect(11,13,5);
        Connected.connect(13,10,1);
        Connected.connect(13,12,3);

        DWGraph_Algo algo2=new DWGraph_Algo();
        System.out.println(algo2.isConnected());
        algo2.init(Connected);



        System.out.println("connect " + algo2.isConnected());

*/


        System.out.println(algo.save("test"));

    }
}
