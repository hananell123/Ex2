
package tests;


import myClasses.DWGraph_DS;
import myClasses.NodeData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

            myGraph.connect(2, 1, 3); // not contain
         myGraph.connect(4, 3, 6);
         myGraph.connect(4, 3, 6);//double
         myGraph.connect(6, 5, 5);
            myGraph.connect(7, 6, 1);
            myGraph.connect(6, 9, 3);
            myGraph.connect(9, 6, 4);
            myGraph.connect(7, 5, 10);
            myGraph.connect(5, 4, 2);
            myGraph.connect(9, 7, 1);
            myGraph.connect(7, 7, 1);//vertex to himself


            assertEquals(10 ,myGraph.nodeSize());

            assertEquals(8 ,myGraph.edgeSize());




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

    @Test
   static void edgesTest(){
        DWGraph_DS myGraph = new DWGraph_DS();
        NodeData temp;
        for (int i = 0; i < 5; i++) {
            temp = new NodeData();
            myGraph.addNode(temp);
        }

        assertTrue(myGraph.getE(0).size() == 0);
        assertTrue(myGraph.getE(1).size() == 0);
        assertTrue(myGraph.getE(2).size() == 0);

        myGraph.connect(0, 1, 1);
        myGraph.connect(1, 2, 2);
        myGraph.connect(2, 3, 3);
        myGraph.connect(3, 4, 4);
        myGraph.connect(4, 2, 5);

        assertTrue(myGraph.getE(0).size() == 1);
        assertTrue(myGraph.getE(1).size() == 1);
        assertTrue(myGraph.getE(2).size() == 1);

        assertEquals(myGraph.getEdges().size() , 5);

    }





}


