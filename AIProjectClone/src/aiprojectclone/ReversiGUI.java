/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author joshkruger
 * This class runs the GUI for the entire game, as well as displays any messages t
 * that the server 
 * 
 */
public class ReversiGUI extends JFrame implements ActionListener {
    
    GameBoard game; //Board that is the same by referance as client or server
    ArrayList<JButton> buttons = new ArrayList();   //Squares of the board
    JMenuItem undo = new JMenuItem("  Undo Last Move  ");
    JMenuItem show_a = new JMenuItem("   Toggle: Show Avaliable Moves");
    JMenuItem show_aa = new JCheckBoxMenuItem("  Show Avaliable Moves");
    JMenuItem QUIT = new JMenuItem(" Quit All");
    
    JMenuItem set_black = new JMenuItem("  Set Black Image");
    JMenuItem set_white = new JMenuItem("  Set White Image");
    JMenuItem update_g = new JCheckBoxMenuItem("  Use Custom");
    boolean show_moves = false;
    boolean showundo = false;
    
    String black_image_path = "black_trans.png";
    String white_image_path = "white_trans.png";
    String recent_move = "NULL";
    
    final Object SyncObj; //Sync object for determingin if move was made(for client class)
    
    
    public ReversiGUI(GameBoard g, Object syncobject){
       game =g;//reference to original gameboard  
       SyncObj = syncobject;
       setup_window();
        
    }

//    ReversiGUI() { //Temporary constructor for building
//        System.out.println("Starting GUI...");
//        setup_window();
//    }
    
    
    public void kill_gui() //Shuts down GUI remotly
    {
        dispose();
    }
    
    private String get_color_name(){
        char temp = game.get_color();
        
        if(temp == 'b')
            return "Black";
        else if(temp == 'w')
            return "White";
        
        return "Undefined";
    }
    
    private void setup_window(){        //Makes and displays board on swing/awt thread
        
       //UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
       //System.out.println(UIManager.getInstalledLookAndFeels()); 
       setTitle("Reversi Game ");
       setSize(710,710);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       
       set_black.addActionListener(this);
       set_white.addActionListener(this);
       update_g.addActionListener(this);
       show_aa.addActionListener(this);
       
       TitledBorder gameboarder = new TitledBorder("Playing game as: " + get_color_name());
       
        JMenuBar Tools = new JMenuBar();
        JMenu File = new JMenu("  File  ");
        JMenu Edit = new JMenu("  Edit  ");
        QUIT.addActionListener(this);
        File.add(undo);
        File.add(show_aa);
        File.add(QUIT);
        Edit.add(set_black);
        Edit.add(set_white);
        Edit.add(update_g);
        
        Tools.add(File);
        Tools.add(QUIT);
        //Tools.add(Edit);
        setJMenuBar(Tools);
        undo.addActionListener(this);
       
       JPanel pane = new JPanel();
       GridLayout grid = new GridLayout(8,0);//Java ignores y comp when x is present
       pane.setLayout(grid);
       //205-192-176
       Color backgroun = new Color(205,192,176);
       
       
       for(int i=0; i<64;i++)
       {
           buttons.add(new JButton());
           pane.add(buttons.get(i));
           buttons.get(i).setOpaque(true);
           buttons.get(i).setBackground(backgroun);
           buttons.get(i).setMargin(new Insets(0, 0, 0, 0));
           buttons.get(i).setBorder(new LineBorder(Color.gray, 2));
           buttons.get(i).addActionListener(this);
       }
       
       
        
        
       pane.setBorder(gameboarder);  
       add(pane); 
       setVisible(true);
        
    }
    
    public void update_board(GameBoard g){      //Updates the board with new gamebaord, and redraws peices
        
       if(show_moves){
            for(int i=0; i<64;i++)
            {
            buttons.get(i).setBorder(new LineBorder(Color.gray, 2));

            }
       }
        
        game =g;
        
        char[][] b = game.get_board();
        ArrayList<Character> col = new ArrayList();
        
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                col.add(b[x][y]);
            }
            
        }
        
        if(show_moves){
            ArrayList<String> m = game.get_avaliable_indexs();
            
            for(int i = 0; i < m.size(); i++ ){
                char co = m.get(i).charAt(0);
                char ro = m.get(i).charAt(1);
                int colo = Character.getNumericValue(co);
                int rowo = Character.getNumericValue(ro);
                
                int z = 0;
                for(int x=0;x<8;x++){
                    for(int y=0;y<8;y++){
                        
                        if(x ==rowo && y == colo){
                        buttons.get(z).setBorder(new LineBorder(Color.yellow, 2));
                        }
                     z++;   
                    }
            
                }
                
                
                
            }
            
        }
        
        
        
       //URL loadURL = ReversiGUI.class.getResource("black_trans.png"); 
        
       ImageIcon black = new ImageIcon(getClass().getResource("black_trans.png"));
       ImageIcon white = new ImageIcon(getClass().getResource("white_trans.png"));

       //System.out.println("TEST: "+ black.getImageLoadStatus());
       
       Image img = black.getImage();  
       Image newimg = img.getScaledInstance(70, 66,  java.awt.Image.SCALE_SMOOTH);  
       ImageIcon nblack = new ImageIcon(newimg); 
       
       Image img2 = white.getImage();
       Image nimg = img2.getScaledInstance(78, 74,  java.awt.Image.SCALE_SMOOTH);
       ImageIcon nwhite = new ImageIcon(nimg);
       
       for(int i =0; i<col.size();i++){
           
           if(col.get(i) == 'O'){
              
               buttons.get(i).setIcon(nwhite);
               
           }
           else if(col.get(i) == '@'){
               buttons.get(i).setIcon(nblack);
               
           }
           else{
               buttons.get(i).setIcon(null);
           }
               
           
           
       }
       
       
        
        
        
        
        
    }
    
    private String num_to_move(int i){
        
        char[][] c = game.get_board();
        
        int test =0;
        String move;
        
        for(int x =0; x<8;x++){
            for(int y =0; y<8;y++){
                
                if(test == i){
                    move = Integer.toString(y) + Integer.toString(x);
                    move = game.index_to_move(move);
                    return move;
                }
                test++;
                        
            }
            
        }
        return "UNDEFINED";
    }
    
    public String get_move(){
        return recent_move;
    }
    public void set_move_null(){
        recent_move = "NULL";
    }
    
    public boolean get_undo(){
        return showundo;
    }
    public void reset_undo(){
        showundo = !showundo;
    }
  
    public void show_message(String s)
    {
        JOptionPane.showMessageDialog(null, s);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
         Object source = ae.getSource();
         
         if(source == undo){
             //undo code here
             //showundo = true;
             synchronized(SyncObj){
                    showundo = true;
                     SyncObj.notify();
                    }
             
         }
         
         if(source == set_black){
             Background getimg = new Background();
             getimg.setBackground("BLACK");
             
         }
         
         if(source == show_aa)
         {
             show_moves= !show_moves;
            
            if(!show_moves){
            for(int i=0; i<64;i++)
            {
            buttons.get(i).setBorder(new LineBorder(Color.gray, 2));

            }
       }
             
         }
         
         if(source == update_g){
             Background getimg = new Background();
             white_image_path =getimg.getBackgroundURL("WHITE");
             black_image_path =getimg.getBackgroundURL("BLACK");
         }
         
         if(source == set_white){
            Background getimg = new Background();
            getimg.setBackground("WHITE");
             
         }
         
         if(source == QUIT){
             System.exit(0);
         }
         
         for(int i=0;i<buttons.size();i++){
             
             if(source == buttons.get(i))
             {
                 //System.out.println("TESTING");
                 String move1 = num_to_move(i);
                 
                 if(!game.validate_move(game.move_to_index(move1)))
                     JOptionPane.showMessageDialog(null, "Invalid move");
                 else{
                     
                     synchronized(SyncObj){
                     recent_move =move1;
                     SyncObj.notify();
                    }
   
                 }
                 
                 
                 
             }
             
         }
        
        
        
    }
    

}
