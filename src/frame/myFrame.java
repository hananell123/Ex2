package frame;

import api.DWGraph_Algo;
import api.DWGraph_DS;
import api.*;

import javax.swing.*;
import java.awt.*;

public class myFrame extends JFrame {


    public myFrame(directed_weighted_graph g1){
        initFrame();
        initPanel(g1);
    }

    private void initFrame(){
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initPanel(directed_weighted_graph g1){
         MyPanel panel=new MyPanel();
        panel.init(g1);
         this.add(panel);

    }






}
