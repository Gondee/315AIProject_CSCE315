/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JFrame;

/**
 *
 * @author joshkruger
 */
public class ReversiGUI extends JFrame implements ActionListener {
    
    GameBoard game; //Board that is the same by referance as client or server
    
    public ReversiGUI(GameBoard g){
       game =g;//reference to original gameboard  
        
    }
    
    public ArrayList<String> setup_window(){    //Returns ArrayList containing anynumber of configuration items
        ArrayList<String> config = new ArrayList();
        
        
        
        
        
        
        return config;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
