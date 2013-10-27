/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author joshkruger
 */
public class ReversiGUI extends JFrame implements ActionListener {
    
    GameBoard game; //Board that is the same by referance as client or server
    ArrayList<JButton> buttons = new ArrayList();
    JMenuItem undo = new JMenuItem("  Undo  ");
    
    
    public ReversiGUI(GameBoard g){
       game =g;//reference to original gameboard  
       setup_window();
        
    }

    ReversiGUI() { //Temporary constructor for building
        System.out.println("Starting GUI...");
        setup_window();
    }
    
    
    private String get_color_name(){
        char temp = game.get_color();
        
        if(temp == 'b')
            return "Black";
        else if(temp == 'w')
            return "White";
        
        return "Undefined";
    }
    
    private void setup_window(){    
       setTitle("Reversi Game ");
       setSize(710,710);
       setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       setLocationRelativeTo(null);
       
       
       
       TitledBorder gameboarder = new TitledBorder("Playing game as: White"); //+ get_color_name());
       
        JMenuBar Tools = new JMenuBar();
        JMenu File = new JMenu("  File  ");
        File.add(undo);
        Tools.add(File);
        setJMenuBar(Tools);
        undo.addActionListener(this);
       
       JPanel pane = new JPanel();
       GridLayout grid = new GridLayout(8,0);//Java ignores y comp when x is present
       pane.setLayout(grid);
       
       
       
       
       for(int i=0; i<64;i++)
       {
           buttons.add(new JButton());
           pane.add(buttons.get(i));
           buttons.get(i).setMargin(new Insets(0, 0, 0, 0));
           buttons.get(i).setBorder(new LineBorder(Color.gray, 2));
           buttons.get(i).addActionListener(this);
       }
       
       
        
        
       pane.setBorder(gameboarder);  
       add(pane); 
       setVisible(true);
        
    }
    
    public void update_board(GameBoard g){
        
        game =g;
        char[][] b = game.get_board();
        ArrayList<Character> col = new ArrayList();
        
        for(int x=0;x<8;x++){
            for(int y=0;y<8;y++){
                col.add(b[x][y]);
            }
            
        }
        
        
       ImageIcon black = new ImageIcon("black_trans.png");
       ImageIcon white = new ImageIcon("white_trans.png");

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
    

    @Override
    public void actionPerformed(ActionEvent ae) {
         Object source = ae.getSource();
         
         if(source == undo){
             //undo code here
             
         }
         
         for(int i=0;i<buttons.size();i++){
             
             if(source == buttons.get(i))
             {
                 //System.out.println("TESTING");
                 String move1 = num_to_move(i);
                 
                 if(!game.validate_move(game.move_to_index(move1)))
                     JOptionPane.showMessageDialog(null, "Invalid move");
                 else{
                     
                     //Move has to go somewhere
                     JOptionPane.showMessageDialog(null, "Move: "+ move1+" - the GUI is not hooked up to any client code-josh"
                             + ". To test use the 'l' in main, text entry only");
                 }
                 //----------------------
                 //testing board
                 
                 
                 
             }
             
         }
        
        
        
    }
    
    
}
