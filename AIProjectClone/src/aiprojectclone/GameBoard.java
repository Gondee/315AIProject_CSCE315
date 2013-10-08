/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 */
public class GameBoard extends Reverse{
    
    public char [][] board;
    

    
    public GameBoard(){         //Default constructor
        
        board = new char [8][8]; //setting up board
    }
    
    
    //-------------------------------------Functions Below
    
    public boolean check_state()
    {
        
        
        return false;
    }
    
    public boolean validate_move()
    {
        
      return false;  
    }
    
    public boolean move(String m)
    {
        
      return false;  
    }
    
    
    
    public char [][] get_board() //Returns copy of current board state
    {
        char [][] temp_board = board; //protection
        
        return temp_board;
    }
    
    
    
    
    
    
}
