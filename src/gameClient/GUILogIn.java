package gameClient;

import Server.Game_Server_Ex2;
import api.game_service;
import gameClient.Ex2_Client;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;

public class GUILogIn implements ActionListener {
    private static JLabel gameLevelLabel;
    private static JTextField gameLevelText;
    private static JLabel IDLabel;
    private static JTextField IDText;
    public static JButton button;
    public static String gameLevel= "-1";
    private static String id="-1";
    private static JPanel panel = new JPanel();
    private static JFrame frame = new JFrame();

    public static void run(){
      frame.setSize(100 , 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.add(panel);
      gameLevelLabel = new JLabel("gameLevel");
      gameLevelLabel.setBounds(10,20,80,25);
      panel.add(gameLevelLabel);

      gameLevelText = new JTextField(20);
      gameLevelText.setBounds(100,20,165,25);
      panel.add(gameLevelText);

      IDLabel = new JLabel("ID");
      IDLabel.setBounds(10,50,80,40); ///
      panel.add(IDLabel);

      IDText = new JTextField(20);
      IDLabel.setBounds(100,20,165,25);
      panel.add(IDText);

      button = new JButton("login");
      button.setBounds(10,80,80,25);
      button.addActionListener(new GUILogIn());
      panel.add(button);

      frame.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
       gameLevel = gameLevelText.getText();
       id = IDText.getText();
       //frame.setVisible(false);
        System.out.println("---------------------------------"+Integer.parseInt(gameLevel));
      //System.out.println(""+gameLevel);
    }

    public int getGameLevel(){

        return Integer.parseInt(gameLevel);
    }

    public int getIDText(){
        return Integer.parseInt(id);
    }

    public void closeFrame(){
        frame.setVisible(false);
    }






}
