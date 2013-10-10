/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.util.ArrayList;

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
        
        
        //System.out.println("BoardSetup:" + server_flag);
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
    
    public boolean validate_move(String m) //Ensures new move is not bad
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
        if(c == '1'||c =='2'|| c=='3'||c == '4'||c =='5'|| c=='6'||c=='7'||c=='8') //accomidating if index with offset is sent
        {
             
           col = Character.getNumericValue(c); 
           col= col-1;//adjusting for offest.
           
           
        }
        else if(c =='a'||c =='b'|| c=='c'||c == 'd'||c=='e'||c=='f'||c=='g'||c=='h')
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
        
         ArrayList<String> pos_moves =get_avaliable_moves(m); //possible moveset
         
         boolean val = false;
         for(int i=0; i< pos_moves.size(); i++)
         {
            char rt = pos_moves.get(i).charAt(0);
            char ct = pos_moves.get(i).charAt(1);
            int ri = Character.getNumericValue(rt); 
            int ci = Character.getNumericValue(ct); 
            
            if(ri == row && ci == col)
                val=true;
            
         }
         if(!val)
         {
             System.out.println("Not a valid game movment");
             return false;
         }
        
      return true;  
    }
    
    private int move_to_int(char c) //Converts the move charcter to int and usable index
    {
        int col=0;
        int row=0;
        
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
           
          return col;    
        }
        
        else if(c == '1'||c =='2'||c=='3'||c== '4'||c=='5'||c=='6'||c=='7'||c=='8')
        {
             
           row = Character.getNumericValue(c); 
           row= row-1;//adjusting for offest.
           
           return row;
           
        }
        
        
        
        
        
     return 0; //If 0 unexpectadly  
    }
    
    public ArrayList<String> get_avaliable_moves(String m)
    {
        
       int row = move_to_int(m.charAt(0));   
       int col = move_to_int(m.charAt(1));
       ArrayList<Integer> rows = new ArrayList(); //all unchecked moves 
       ArrayList<Integer> cols = new ArrayList();
       ArrayList<String> valid_moves = new ArrayList();
       char c = 'O'; //player character default client
        
        if(color == 'w')
             c= 'O';
        if(color == 'b')
             c = '@';
        
        
        for(int i =0; i<8;i++)
        {
            for(int x =0; x<8;x++)
            {
              if(board[i][x] == c)
              {
                  rows.add(i);
                  cols.add(x);
                  //System.out.println("["+i+","+x+"]"); //testing
              }
                
            }
        }
        String temp;
        for(int i =0; i< rows.size();i++)
        {
            //check up one
            temp= Integer.toString(rows.get(i)-1) + Integer.toString(cols.get(i));
            if(valid_spot(temp))
            {
              valid_moves.add(temp);  
            }
            //check down one
            temp= Integer.toString(rows.get(i)+1) + Integer.toString(cols.get(i));
            if(valid_spot(temp))
            {
              valid_moves.add(temp);  
            }
            //check left
            temp= Integer.toString(rows.get(i)) + Integer.toString(cols.get(i)-1);
            if(valid_spot(temp))
            {
              valid_moves.add(temp);  
            }
            // check right
            temp= Integer.toString(rows.get(i)) + Integer.toString(cols.get(i)+1);
            if(valid_spot(temp))
            {
              valid_moves.add(temp);  
            }
            
        }
        
//        for(int i=0; i<valid_moves.size();i++)
//        {
//            char rt = valid_moves.get(i).charAt(0);
//            char ct = valid_moves.get(i).charAt(1);
//
//
//            System.out.println("["+rt+","+ct+"]"); //testing
//        }
        
        
        
       
       return valid_moves; //passes back indexs of spots that are valid moves ex. [0][0] 
    }
    
    
    
    
    
    public boolean valid_spot(String m) //assuming adjusted for index already, different than validate internal use
    {
        int row;
        int col = 0;
        //Valid length
        
        
        if(m.length() != 2)
        {
            //System.out.println("Not a space on board");
            return false;
        }    
        
        char r = m.charAt(0);
        char c = m.charAt(1);
        //Valid character with converstion to int
        if(r == '0'||r =='1'|| r=='2'||r == '3'||r =='4'|| r=='5'||r=='6'||r=='7')
        {
             
           row = Character.getNumericValue(r); 
           
           
        }
        else
        {
         //System.out.println("Invalid row");
         return false;
        }
        
       //valid character with conversion to int
        if(c == '0'||c =='1'|| c=='2'||c == '3'||c =='4'|| c=='5'||c=='6'||c=='7') //accomidating if index with offset is sent
        {
             
           col = Character.getNumericValue(c); 
            
        }
        else if(c =='a'||c =='b'|| c=='c'||c == 'd'||c=='e'||c=='f'||c=='g'||c=='h')
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
            //System.out.println("Invalid col");
            return false;
        }
        
        
        
        //testing to see if space is already occupied        
        if(board[row][col] != ' ')
        {
          //System.out.println("BoardSpace already Selected");
          return false;
        }
        
      return true; 
    }
    
    public boolean move(String n) //interface for actully moving
    {
        String m =n; //Avoiding reference mods 
        
        boolean valid = validate_move(m);
        if(valid == false)
        {
            System.out.println("\t Invalid move Selection");
            return false;
        }
        
       int row = move_to_int(m.charAt(0));   
       int col = move_to_int(m.charAt(1));
       
       //System.out.println(color);
       if(color == 'w')
       {
           //System.out.println("Row: "+row+" Col: "+ col);
           board[row][col]='O';
           return true;
       }
       if(color == 'b')
       {
           //System.out.println("Row: "+row+" Col: "+ col);
           board[row][col]='@';
           return true;
       }
       
        
        
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
        System.out.println("\t  a b c d e f g h");
                
        
        
    }
    
    
    
    
}//end of class
