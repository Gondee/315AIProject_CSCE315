/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;


/**
 *
 * @author joshkruger
 * 
 * if something is labled with move in the name, it means it accepts user style
 * move commands like 3d
 * 
 * if something has the label index in the name, it means it accepts index locations
 * like 22 or 33
 * 
 * 
 * 
 */
public class GameBoard{
    
    private char [][] board;// actual board
    private char winner =' ';
    private char color; //working color
    
    boolean ai_active; //if AI will be needed
    ArrayList<String> previous_moves;
    
    
    
    
    public GameBoard(char c){         //Default constructor
        super();
        board = new char [8][8]; //setting up board 
        previous_moves = new ArrayList(); //Initalizing new list of previous moves
        color = c; //
        setup_board();
    }//end of constructor
        
    public void set_color(char c) //accepts 'w' or 'b'
    {
        color = c;
    }
    
    public char get_winner()
    {
        if(winner != ' ')
            return winner;
        
        return ' '; //No winner
    }
    
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
        
        
    }
    
    
    
    
    
    private int move_to_index(char c) //Converts the move charcter to int and usable index, internal use
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
          
     return 0; //If the value is already adjusted for index
    }
    
    public String index_to_move(String m) //For external use
    {
        char r = m.charAt(0);
        char c = m.charAt(1);
        
        
        int row=0;
        char col= ' ';//initalize as wrong input in case user has bad input
        
        if(r == '0'||r =='1'|| r=='2'||r == '3'||r =='4'|| r=='5'||r=='6'||r=='7')
        {
             
           row = Character.getNumericValue(r); 
           row= row+1;//adjusting for offest.
           
        }
        else
        {
         System.out.println("Invalid row");
         
        }
        
       //valid character with conversion to int
        
        if(c =='0'||c =='1'|| c=='2'||c == '3'||c=='4'||c=='5'||c=='6'||c=='7')
        {
           switch(c){
               case '0': col='a';
                   break;
               case '1':col='b';
                   break;
               case '2':col='c';
                   break;
               case '3':col='d';
                   break;
               case '4':col='e';
                   break;
               case '5':col='f';
                   break;
               case '6':col='g';
                   break;
               case '7':col='h';
                   break;
               default:
                   break;
           }
           
             
        }
        else
        {
            System.out.println("Invalid col");
            
        }
   
     
      String mov = Integer.toString(row) + col;
      //System.out.println("index_to_move: "+ mov);
      return mov;  
    }
    
    public boolean check_state() //checks for winning conditions or special cases such as overtakes //return true if someone win
    {
        //Test game over
        int space_count=0;
        int white_count=0;
        int black_count=0;
        for(int i =0; i <8;i++)
        {
            for(int x=0; x<8;x++)
            {
              if(board[i][x] != ' ')
              {
                  space_count++;
                  
                  if(board[i][x] == 'O')
                      white_count++;
                  if(board[i][x] == '@')
                      black_count++;
    
              }
                  
                
            }
            
        }
        if(space_count >63)
        {
            System.out.println("Game Over...");
            if(white_count > black_count)
            {
                winner = 'w';
                return true;
            }
            else if(white_count < black_count)
            {
                winner = 'b';
                return true;
            }
            else
            {
                winner = 't'; //tie
                return true;
            }
        }
        
        
        
        //check for overtakes 
        
            
        return false;
    }
    
    public boolean validate_move(String m) //Ensures new move is not bad, validates move structure ex. 'a2' or '11' Not index adjuested
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
        
         ArrayList<String> pos_moves =get_avaliable_indexs(); //possible moveset
         
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
    
    
    
    public ArrayList<String> get_avaliable_indexs()//takes in move, returns index locations of avaliable moves
    {
        
       ArrayList<Integer> rows = new ArrayList(); //all unchecked moves 
       ArrayList<Integer> cols = new ArrayList();
       ArrayList<String> valid_moves = new ArrayList();
       char c = 'O'; //player character default client
        
        if(color == 'w')
             c= '@';
        if(color == 'b')
             c = 'O';
        
        
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
        for(int i =0; i< rows.size();i++) //make funtion for find line and find cross to see if a jump is possiable. 
        {
            
            //check up one
            temp= Integer.toString(rows.get(i)-1) + Integer.toString(cols.get(i));
            if(valid_index(temp))
            {
              valid_moves.add(temp);  
            }
            //check down one
            temp= Integer.toString(rows.get(i)+1) + Integer.toString(cols.get(i));
            if(valid_index(temp))
            {
              valid_moves.add(temp);  
            }
            //check left
            temp= Integer.toString(rows.get(i)) + Integer.toString(cols.get(i)-1);
            if(valid_index(temp))
            {
              valid_moves.add(temp);  
            }
            // check right
            temp= Integer.toString(rows.get(i)) + Integer.toString(cols.get(i)+1);
            if(valid_index(temp))
            {
              valid_moves.add(temp);  
            }
            
        }

       
       return valid_moves; //passes back indexs of spots that are valid moves ex. [0][0] 
    }
    
    private boolean jump_row(){return false;} //to be done
    private boolean jump_col(){return false;} //to do 
    private boolean jump_diag(){return false;} // to do
    
    
    public boolean move(String n) //interface for actully moving
    {
        String m =n; //Avoiding reference mods 
        
        boolean valid = validate_move(m);
        if(valid == false)
        {
            System.out.println("\t Invalid move Selection");
            return false;
        }
        
       int row = move_to_index(m.charAt(0));   
       int col = move_to_index(m.charAt(1));
       
       previous_moves.add(m);
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
    
    public void display_board(Socket socket) throws IOException
    {
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		
        out.println("\t  - - - - - - - - ");
        for(int i =0; i<8;i++)
        {
        out.println("\t"+(i+1)+"|"+board[i][0]+"|"+board[i][1]+"|"+board[i][2]+"|"+board[i][3]
                +"|"+board[i][4]+"|"+board[i][5]+"|"+board[i][6] +"|"+ board[i][7]+"|");
        out.println("\t  - - - - - - - - ");
        }
        out.println("\t  a b c d e f g h");       
    }
    
        
     //validates index locations, different than validate internal use
    public boolean valid_index(String m)
    {
    
        //</editor-fold>
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
    
    
    
    public boolean ramdom_ai()
    {
        //char c = game.color; //Getting color for this instance
        Random generator = new Random(); //Random instance
        
        ArrayList<String> move_list = get_avaliable_indexs(); //Gets all indexs on current board for instance color
        int random_move = generator.nextInt(move_list.size()); //In the future it will select from avaliable index positions      
        String AIMove = move_list.get(random_move);
        
        
        //System.out.println("AI Move: "+ AIMove);
        String final_move = index_to_move(AIMove);
        System.out.println("AI Move: "+ final_move);
        
        if(move(final_move))
            return true;
        
        return false;
    }
    
    public String get_ASCII_board()
    {
        
        String ascii_board ="";
        ascii_board +="  - - - - - - - - ";
        for(int i =0; i<8;i++)
        {
        ascii_board += ""+(i+1)+"|"+board[i][0]+"|"+board[i][1]+"|"+board[i][2]+"|"+board[i][3]
                +"|"+board[i][4]+"|"+board[i][5]+"|"+board[i][6] +"|"+ board[i][7]+"|";
        ascii_board += "  - - - - - - - - ";
        }
        ascii_board += "  a b c d e f g h";
                
     
        return ascii_board;    
    }
    
    
    
    
}//end of class
