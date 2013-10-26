/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 *
 * @author joshkruger
 */
public class SetupGUI extends JFrame implements ActionListener {

    
    public void start(){
        build_gui();
        
        
        
    }
    
    String[] serverop = {"AI-Easy", "AI-Medium","AI-Hard"};
    JComboBox serveroptions = new JComboBox(serverop);
    String[] clientop = {"Human", "AI-Hard"};
    JComboBox clientoptions = new JComboBox(clientop);
    JTextField port = new JTextField(20);
    JTextField ip = new JTextField(20);
    
    JButton cancel = new JButton("Cancel");
    JButton start = new JButton("Start");
    
    String[] cerop = {"Black", "White"};
    JComboBox coloroptions = new JComboBox(cerop);
    
    private void build_gui(){
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
       JPanel cset = new JPanel();
       cset.setBorder(networkboarder);
       GridLayout gridconnection = new GridLayout(0,2);
       cset.setLayout(gridconnection);
       
       
       ip.addActionListener(this);
       port.addActionListener(this);
       
       JLabel ipd = new JLabel(" Enter IP Adress:");
       JLabel ipp = new JLabel(" Enter Port:");

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
       
       
       
       sset.add(cancel);
       sset.add(start);
       
        //color Selection
       TitledBorder colorboarder = new TitledBorder("Step 3: Choose Client Color");
       JPanel c = new JPanel();
       c.setBorder(colorboarder);
       GridLayout colorstart = new GridLayout(2,0);
       c.setLayout(colorstart);
       JLabel note = new JLabel(" Note: Server will be opposite color.");

       c.add(coloroptions);
       c.add(note);
       
       
       pane.add(plset);
       pane.add(cset);
       pane.add(c);
       pane.add(sset);
       
       add(pane);
       setVisible(true);
        
    }
    
    
    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        
        if(source == start){
            
            String clientsetup = (String)clientoptions.getSelectedItem();  
            String serversetup = (String)serveroptions.getSelectedItem();  
            String sip = ip.getText();
            String spo = port.getText();
            
            if("".equals(ip.getText()) || "".equals(port.getText())){
                JOptionPane.showMessageDialog(null, "You must enter both IP and Port "); 
            }
            else{
                
                JOptionPane.showOptionDialog(null, source, sip, WIDTH, WIDTH, null, serverop, source);
                
                //--------------------------------------------
                //-Return these elements in string form to command parser 
                // to be validated
                //Need some place to send commands, need dedicated command parser class
            
            
                //send string of stuff somewhere
            
            
            
            //-------------------------------------------- 
                
                
                
                
                
            }
              
           
            
        }//start button
        
        if(source == clientoptions){
            
            String clientsetup = (String)clientoptions.getSelectedItem();  
            String serversetup = (String)serveroptions.getSelectedItem();  

            if("AI-Hard".equals(clientsetup)){
                serveroptions.setSelectedIndex(2);
                serveroptions.setEnabled(false);
            }
            else
                serveroptions.setEnabled(true);
            
                   
            
        } //clientoptions
        
        if(source == cancel)
            dispose();
    }
    
}
