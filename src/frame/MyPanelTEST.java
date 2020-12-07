package frame;

import api.*;

import javax.swing.*;
import java.awt.*;

public class MyPanelTEST extends JPanel {
    directed_weighted_graph graph;

    public MyPanelTEST() {
        this.setBackground(Color.pink);
    }

    public void init(directed_weighted_graph g1) {
        graph = g1;
    }


    public void paintComponent(Graphics g) {
        repaint();
        super.paintComponent(g);

       drawNodes(g);
        drawLines(g);

    }


    public void drawNodes(Graphics g) {

        for (node_data n : graph.getV()) {
            geo_location loc = n.getLocation();
            g.fillOval((int) loc.x(), (int) loc.y(), 10, 10);
        }

    }

    public void drawLines(Graphics g) {
        for (node_data n : graph.getV()) {
            for (edge_data e : graph.getE(n.getKey())) {
                int x1 = (int) graph.getNode(e.getSrc()).getLocation().x();
                int y1 = (int) graph.getNode(e.getSrc()).getLocation().y();
                int x2 = (int) graph.getNode(e.getDest()).getLocation().x();
                int y2 = (int) graph.getNode(e.getDest()).getLocation().y();

                g.drawLine(x1, y1, x2, y2);

            }

        }


    }
}



