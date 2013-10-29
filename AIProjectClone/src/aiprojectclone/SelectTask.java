/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author joshkruger
 */
public class SelectTask extends JFrame implements ActionListener{   //Chooses the two avaliable functions of our game
       
       public SelectTask(){
           start();
       }
       
       JButton StartServer = new JButton("Host a Reversi Server");
       JButton StartClient = new JButton("Connect to a Reversi Server");
    
       private void start(){        //Builds GUI on swing/awt thread
           
       setTitle("Start a Server or connecto to one");
       setSize(400,400);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       
       
       Color backgroun = new Color(255,255,255);
       
   
       
       StartServer.setOpaque(true);
       StartClient.setOpaque(true);
       
       StartServer.setBackground(backgroun);
       StartClient.setBackground(backgroun);
       
       StartServer.addActionListener(this);
       StartClient.addActionListener(this);
       
       
       JPanel pane = new JPanel();
       TitledBorder plb= new TitledBorder("Choose a task");
       pane.setBorder(plb);
       GridLayout g = new GridLayout(2,0);
       pane.setLayout(g);
       
       pane.add(StartServer);
       pane.add(StartClient);
       
       add(pane); 
       setVisible(true);
    
       }
       
       

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if(source == StartServer){
            String theport = JOptionPane.showInputDialog ( "Enter a valid Port to host on:" ); 
            
            int p = Integer.parseInt (theport); 
            Server s = new Server(p);
            try {
                dispose();
                JOptionPane.showMessageDialog(null, "Runnging in background...");
                System.out.println("Listening...");
                s.listen();
                //System.out.println("Listening...");
            } catch (IOException ex) {
                Logger.getLogger(SelectTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(source == StartClient){
            SetupGUI begin = new SetupGUI();
            begin.start();
            dispose();
            
        }
        
        
    }
    
    
}
