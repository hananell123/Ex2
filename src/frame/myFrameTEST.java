package frame;

import api.*;

import javax.swing.*;

public class myFrameTEST extends JFrame {


    public myFrameTEST(directed_weighted_graph g1){
        initFrame();
        initPanel(g1);
    }

    private void initFrame(){
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private void initPanel(directed_weighted_graph g1){
         MyPanelTEST panel=new MyPanelTEST();
        panel.init(g1);
         this.add(panel);

    }






}
