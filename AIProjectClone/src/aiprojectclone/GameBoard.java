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
    char color;
    
    
    
    
    
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
        
        if(get_server_flag() == 2) //this instance is client
        {
            color = client_color;
        }
        else
        {
            color = server_color;
        }
        
        
        display_board();
        
       
        
      
    }
    
    public boolean check_state() //checks for winning conditions or special cases such as overtakes
    {
            
        return false;
    }
    
    public boolean validate_move(String m) //Ensures new move is valid 
    {
        
        int row;
        int col = 0;
        //Valid length
        
        
        if(m.length() != 2)
        {
            System.out.println("Not a space on board");
            return false;
        }      
        char r = m.charAt(0);
        char c = m.charAt(1);
        //Valid character with converstion to int
        if(r == '1'||r =='2'|| r=='3'||r == '4'||r =='5'|| r=='6'||r=='7'||r=='8')
        {
             
           row = Character.getNumericValue(r); 
           row= row-1;//adjusting for offest.
           
        }
        else
        {
         System.out.println("Invalid row");
         return false;
        }
        
       //valid character with conversion to int 
        if(c =='a'||c =='b'|| c=='c'||c == 'd'||c=='e'||c=='f'||c=='g'||c=='h')
        {
           switch(c){
               case 'a': col=0;
                   break;
               case 'b':col=1;
                   break;
               case 'c':col=2;
                   break;
               case 'd':col=3;
                   break;
               case 'e':col=4;
                   break;
               case 'f':col=5;
                   break;
               case 'g':col=6;
                   break;
               case 'h':col=7;
                   break;
               default:
                   break;
           }
           
              
        }
        else
        {
            System.out.println("Invalid col");
            return false;
        }
        
        
        
        //testing to see if space is already occupied
        System.out.println("Row: "+row+" Col: "+ col);
        if(board[row][col] != ' ')
        {
          System.out.println("BoardSpace already Selected");
          return false;
        }
        
        //valid move by game rules
        //O is white
        //@ is black
        char s_char;
        
        if(color == 'w')
             s_char= 'O';
        if(color == 'b')
             s_char = '@';
        
        //find avaliable moves
        
        
        
        
        
      return true;  
    }
    
    public void get_avaliable_moves(String m)
    {
        
        
    }
    
    public boolean move(String m) //interface for actully moving
    {
        
        boolean valid = validate_move(m);
        if(valid == false)
        {
            System.out.println("\t Invalid move Selection");
            return false;
        }
        
        
        
        
       
        
        
      return true;  
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
        System.out.println("\t  a b c d e f g h");
                
        
        
    }
    
    
    
    
}
