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
       JButton EndServer = new JButton("End Server");
    
       private void start(){        //Builds GUI on swing/awt thread
           
       setTitle("Start a Server or connecto to one");
       setSize(400,400);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       
       
       Color backgroun = new Color(255,255,255);
       
   
       
       StartServer.setOpaque(true);
       StartClient.setOpaque(true);
       EndServer.setOpaque(true);
       
       StartServer.setBackground(backgroun);
       StartClient.setBackground(backgroun);
       EndServer.setBackground(backgroun);
       
       StartServer.addActionListener(this);
       StartClient.addActionListener(this);
       EndServer.addActionListener(this);
       
       
       JPanel pane = new JPanel();
       TitledBorder plb= new TitledBorder("Choose a task");
       pane.setBorder(plb);
       GridLayout g = new GridLayout(0,2);
       pane.setLayout(g);
       
       pane.add(StartServer);
       pane.add(EndServer);
       pane.add(StartClient);
       
       
       add(pane); 
       setVisible(true);
    
       }
       
       

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if(source == EndServer){
            System.exit(0);
            
        }
        
        if(source == StartServer){
            StartClient.setEnabled(false);
            final String theport = JOptionPane.showInputDialog ( "Enter a valid Port to host on:" ); 
            
            Thread t = new Thread() {
            public void run() {
                
                int p = Integer.parseInt (theport); 
            Server s = new Server(p);
            
            
            try {
                
                System.out.println("Listening...");
                s.listen();

               
                //System.out.println("Listening...");
            } catch (IOException ex) {
                Logger.getLogger(SelectTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
                
                
        
            }
            };
            t.start();
            
            
            
            
        }
        if(source == StartClient){
            SetupGUI begin = new SetupGUI();
            begin.start();
            dispose();
            
        }
        
        
    }
    
    
}
