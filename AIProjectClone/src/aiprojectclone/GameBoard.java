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
    
    private char [][] board;// actual board
    AI ai;                  //working instance of the AI 

    
    public GameBoard(){         //Default constructor
        
        board = new char [8][8]; //setting up board
        ai = new AI(get_ai_flag()); //creates new AI with appropriate difficulty
    }
    
    
    //-------------------------------------Functions Below
    
    public boolean check_state() //checks for winning conditions or special cases such as overtakes
    {
            
        return false;
    }
    
    public boolean validate_move(String m) //Ensures new move is valid 
    {
        
      return false;  
    }
    
    public boolean move(String m) //interface for actully moving
    {
        
      return false;  
    }
    
    
    
    public char [][] get_board() //Returns copy of current board state
    {
        char [][] temp_board = board; //protection
        
        return temp_board;
    }
    
    
    
    
    
    
}
