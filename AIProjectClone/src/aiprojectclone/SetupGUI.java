/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;

/**
 *
 * This class just esists so that the user can choose between hosting a server on 
 * his or her local machine, and connecting to an existing server
 * 
 * 
 * 
 * @author joshkruger
 */
public class SetupGUI extends JFrame implements ActionListener {

    
    public void start(){
        build_gui();
        
        
        
    }
    boolean serverVSserver = false; //Determins if UI needs to open additinoal settings options
    
    String[] serverop = {"AI-Easy", "AI-Medium","AI-Hard"};
    JComboBox serveroptions = new JComboBox(serverop); //server drop down menu
    String[] clientop = {"Human", "AI-Easy","AI-Medium","AI-Hard"};
    JComboBox clientoptions = new JComboBox(clientop); //client drop down menu
    JTextField port = new JTextField(20);
    JTextField ip = new JTextField(20);     //Entry fields of ip and ports of servers
    JTextField porttwo = new JTextField(20);
    JTextField iptwo = new JTextField(20);
    JLabel ipd = new JLabel(" Enter IP Adress:");
    JLabel ipp = new JLabel(" Enter Port:");
    JLabel ipdtwo = new JLabel(" Enter IP Adress(2):");
    JLabel ipptwo = new JLabel(" Enter Port(2):");
    
    
    JButton cancel = new JButton("Cancel");
    JButton start = new JButton("Start");
    
    String[] cerop = {"Black", "White"};
    JComboBox coloroptions = new JComboBox(cerop);
    
    JPanel cset = new JPanel();
    
    private void build_gui(){       //Build the GUI and display on awt/swing thread
       setTitle("Reversi Game Setup");
       setSize(300,400);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       
       TitledBorder boarder = new TitledBorder("Setup your game!");
       JPanel pane = new JPanel();
       pane.setBorder(boarder);
       
       GridLayout grid = new GridLayout(4,0);
       pane.setLayout(grid);
       
       //Setting up Player Choices
       TitledBorder playerboarder = new TitledBorder("Step 1: Setup game players!");
       
       JPanel plset = new JPanel();
       plset.setBorder(playerboarder);
       GridLayout gridplayers = new GridLayout(2,0);
       plset.setLayout(gridplayers);
       
       JLabel client = new JLabel(" Client Player: ");
       JLabel server = new JLabel(" Server Difficutly : ");
       
       

       clientoptions.setSelectedIndex(0);
       clientoptions.addActionListener(this);
        serveroptions.setSelectedIndex(0);
       //serveroptions.addActionListener(this);

       plset.add(client);
       plset.add(clientoptions);
       plset.add(server);
       plset.add(serveroptions);
       
       //Connection Setup
       TitledBorder networkboarder = new TitledBorder("Step 2: Setup game connection!");
       cset.setBorder(networkboarder);
       GridLayout gridconnection = new GridLayout(0,2);
       cset.setLayout(gridconnection);
       
       
       ip.addActionListener(this);
       port.addActionListener(this);
       
       

       cset.add(ipd);
       cset.add(ip);
       cset.add(ipp);
       cset.add(port);
       
       //Start pane
       TitledBorder startboarder = new TitledBorder("Step 4: Begin Game!");
       JPanel sset = new JPanel();
       sset.setBorder(startboarder);
       GridLayout gridstart = new GridLayout(0,2);
       sset.setLayout(gridstart);
       
       start.addActionListener(this);
       cancel.addActionListener(this);
       
       iptwo.setText("");
       porttwo.setText("0000");
       
       sset.add(cancel);
       sset.add(start);
       
        //color Selection
       TitledBorder colorboarder = new TitledBorder("Step 3: Choose Client Color");
       JPanel c = new JPanel();
       c.setBorder(colorboarder);
       GridLayout colorstart = new GridLayout(2,0);
       c.setLayout(colorstart);
       JLabel note = new JLabel(" Note: Server will be opposite color.");
       coloroptions.setSelectedIndex(0);
       c.add(coloroptions);
       c.add(note);
       
       
       pane.add(plset);
       pane.add(cset);
       pane.add(c);
       pane.add(sset);
       
       add(pane);
       setVisible(true);
        
    }
    
    public class ClientRunnable implements Runnable { 

        String clientsetup, serversetup, sip, col,iptwo;
        int portt, porttwo;
        
        
        
        public ClientRunnable(String clientsetup, String serversetup, String sip, int portt, String col,String iptwo, int porttwo)
        {
            this.clientsetup = clientsetup;
            this.serversetup = serversetup;
            this.sip = sip;
            this.portt = portt;
            this.col = col;
            this.porttwo = porttwo;
            this.iptwo = iptwo;
        }
        
        @Override
        public void run() {
            try {
                 Client startclient = new Client(clientsetup,serversetup,sip,portt,col,iptwo,porttwo);
             } catch (UnknownHostException ex) {
                 Logger.getLogger(SetupGUI.class.getName()).log(Level.SEVERE, null, ex);
             } catch (IOException ex) {
                 Logger.getLogger(SetupGUI.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(SetupGUI.class.getName()).log(Level.SEVERE, null, ex);
             }
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {       
        Object source = ae.getSource();
        
        if(source == start){        //Begin game process
            
            String clientsetup = (String)clientoptions.getSelectedItem();  
            String serversetup = (String)serveroptions.getSelectedItem();  
            String sip = ip.getText();
            String spo = port.getText();
            String col = (String)coloroptions.getSelectedItem();
            String porttwoo = porttwo.getText();
            String ipptwo = iptwo.getText();
            
            if("".equals(ip.getText()) || "".equals(port.getText())){
                JOptionPane.showMessageDialog(null, "You must enter both IP and Port "); 
            }
            else{
                
               dispose();
               
               if("".equals(porttwoo))
                   porttwoo = "0";
               
               int portt = Integer.parseInt(spo);
               int porttnumber2 = Integer.parseInt(porttwoo);
               Thread clientThread = new Thread(new ClientRunnable(clientsetup,serversetup,sip,portt,col,ipptwo,porttnumber2), "client thread");
               clientThread.start();
                
               
               
                
            }
              
           
            
        }//start button
        
        if(source == clientoptions){
            
            String clientsetup = (String)clientoptions.getSelectedItem();  
            String serversetup = (String)serveroptions.getSelectedItem();  

            if("AI-Easy".equals(clientsetup) || "AI-Medium".equals(clientsetup) || "AI-Hard".equals(clientsetup)){
              
              if(!serverVSserver){      //open additional settings to user
              setSize(300,500);
              cset.add(ipdtwo);
              cset.add(iptwo);
              cset.add(ipptwo);
              cset.add(porttwo);
              coloroptions.setSelectedIndex(1);
              coloroptions.setEnabled(false);
              
              }
              serverVSserver=true;
  
            }
            else{       //Close additional settings if open
                serverVSserver=false;
                setSize(300,400);
                cset.remove(iptwo);
                cset.remove(porttwo);
                cset.remove(ipdtwo);
                cset.remove(ipptwo);
                coloroptions.setEnabled(true);
                porttwo.setText("");
                iptwo.setText("");
            }
                
            
                   
            
        } //clientoptions
        
        if(source == cancel)
            dispose();
    }
    
}
