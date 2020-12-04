
package tests;


import api.DWGraph_DS;
import api.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DWGraph_DS_test {


    //build graph with 10 vectors
     @Test
     void build10() {
        DWGraph_DS myGraph = new DWGraph_DS();
        NodeData temp;
        for (int i = 0; i < 10; i++) {
            temp = new NodeData();
            myGraph.addNode(temp);
        }

            myGraph.connect(2, 1, 3);
            myGraph.connect(1, 2, 8);
            myGraph.connect(4, 1, 7);
            myGraph.connect(4, 1, 7);//double
            myGraph.connect(4, 3, 6);
            myGraph.connect(6, 5, 5);
            myGraph.connect(7, 6, 1);
            myGraph.connect(6, 9, 3);
            myGraph.connect(9, 6, 4);
            myGraph.connect(0, 8, 2.7);
            myGraph.connect(0, 20, 2.7);// not contain node 20
            myGraph.connect(0, 8, 3);
            myGraph.connect(7, 5, 10);
            myGraph.connect(5, 4, 2);
            myGraph.connect(9, 7, 1);
            myGraph.connect(7, 7, 1);//vertex to himself


            assertEquals(10 ,myGraph.nodeSize());
            assertEquals(12 ,myGraph.edgeSize());


        }

    @Test
    void empty() {
        DWGraph_DS myGraph = new DWGraph_DS();
        NodeData n = new NodeData();

        assertEquals(0 ,myGraph.nodeSize());
        assertEquals(0 ,myGraph.edgeSize());
        assertEquals(0 ,myGraph.getMC());

        myGraph.addNode(n);
        myGraph.removeEdge(2,3);//edge not exist

        assertEquals(1 ,myGraph.nodeSize());
        assertEquals(0 ,myGraph.edgeSize());
        assertEquals(1 ,myGraph.getMC());

        NodeData m = new NodeData();
        myGraph.addNode(m);
        myGraph.connect(n.getKey() , m.getKey() , 2);

       assertEquals(myGraph.getEdge(n.getKey() , m.getKey()).getWeight(), 2);
       myGraph.addNode(n);

       assertEquals(myGraph.getEdge(m.getKey(),n.getKey()), null);

    }




}


