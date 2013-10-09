/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 */
public class GameBoard extends Reversi{
    
    private char [][] board;// actual board
    AI ai;                  //working instance of the AI 
    boolean ai_active; //if AI will be needed 
    
    
    
    
    
    public GameBoard(){         //Default constructor
        
        board = new char [8][8]; //setting up board
        
        
        if(get_ai_flag()) //If AI is needed
        {
            ai = new AI(get_l_difficulty()); //creates new AI with appropriate difficulty
            ai_active = true;
        }
        else 
        {
            ai_active = false; //ai will not be needed 
        }
        setup_board();
    }//end of constructor
        
    
    
    //-------------------------------------Functions Below
    private void setup_board()
    {
        
        for(int i=0; i<8; i++)
        {
            for(int x=0; x<8;x++)
            {
                board[i][x] = ' ';
                
            }
        }
        board[3][3] = 'O';
        board[3][4] = '@';
        board[4][3] = '@';
        board[4][4] = 'O';
        
        display_board();
        
        
      
    }
    
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
    
    public void display_board()
    {
        
        System.out.println("\t  - - - - - - - - ");
        for(int i =0; i<8;i++)
        {
        System.out.println("\t"+(i+1)+"|"+board[i][0]+"|"+board[i][1]+"|"+board[i][2]+"|"+board[i][3]
                +"|"+board[i][4]+"|"+board[i][5]+"|"+board[i][6] +"|"+ board[i][7]+"|");
        System.out.println("\t  - - - - - - - - ");
        }
        System.out.println("\t  a b c d e f g f");
                
        
        
    }
    
    
    
    
}
