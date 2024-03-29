/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 *
 * @author joshkruger
 * 
 * This class is the GameBoard and all of the ecopanying functions
 * it handels the board, moving on the board, and jumping on the board
 * it also assits with simulating moves 
 * 
 * 
 * if something is labled with move in the name, it means it accepts user style
 * move commands like d3
 * 
 * if something has the label index in the name, it means it accepts index locations
 * like 22 or 33
 * 
 * 
 * 
 */
public class GameBoard implements Serializable{ //Implements serialiazable so deep copys can be made
    
    private char [][] board;// actual board
    private char winner =' ';
    private char color; //working color
    
    boolean ai_active; //if AI will be needed
    ArrayList<char[][]> previous_boards;
    
    
    
    
    public GameBoard(char c){         //Default constructor
        super();
        board = new char [8][8]; //setting up board 
        previous_boards = new ArrayList(); //Initalizing new list of previous moves
        color = c; //
        setup_board();
    }//end of constructor
    

    public GameBoard(GameBoard g){ //Copy constructor
        this.board = g.board;
        this.color = g.color;
        this.previous_boards = g.previous_boards;
        this.ai_active = g.ai_active;
        this.winner = g.winner;
        
    }
    public char get_color(){
        return color;   
    }
        
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
    private void setup_board() //Sets up the inital board
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
    public char peice_count(){
        
        int bp=0;
        int wp=0;
        
        for(int i=0; i<8; i++)
        {
            for(int x=0; x<8;x++)
            {
                if(board[i][x]=='@')
                    bp++;
                if(board[i][x]=='O')
                    wp++;
                
            }
        }
        
        
        if(bp>wp){
            //System.out.println("BLACK WINS");
            return 'b';
        }
        if(wp>bp){
            //System.out.println("BLACK WINS");
            return 'w';
            
        }
            
        
        
        return 't';
    }    
    
    public String move_to_index(String m) //Converts the move charcter to int and usable index, internal use
    {
        int row = 0;
        int col = 0;
        //Valid length
        
        
        
        if(m.length() != 2)
        {
            System.out.println("Not a space on board");
            return "NO";
        }    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        //Valid character with converstion to int
        if(r == '1'||r =='2'|| r=='3'||r == '4'||r =='5'|| r=='6'||r=='7'||r=='8')
        {
             
           row = Character.getNumericValue(r); 
           row= row-1;//adjusting for offest.
           
        }
        else
        {
         System.out.println("Invalid row");
         return "NO";
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
            return "NO";
        }
        
        String ret = Integer.toString(col) + Integer.toString(row);
        
       return ret;
    }
    
    public String index_to_move(String m) //converts a board index to usuable move
    {
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        
        
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
   
     
      String mov = col + Integer.toString(row) ;
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
//        if(space_count >63)
//        {
//       //     System.out.println("Game Over...");
//            if(white_count > black_count)
//            {
//                winner = 'w';
//                return true;
//            }
//            else if(white_count < black_count)
//            {
//                winner = 'b';
//                return true;
//            }
//            else
//            {
//                winner = 't'; //tie
//                return true;
//            }
//        }
        
        
       
        if(color == 'b'){
            
            ArrayList<String> mov1 = get_avaliable_AIindexs();
            //set_color('w'); //Checking opp moves
            ArrayList<String> mov2 = get_avaliable_indexs();
            //set_color('b'); //Checking player moves
            
            if(mov1.isEmpty() && mov2.isEmpty()){
                winner = peice_count();
                return true; //game over, no one had moves
            }
            
        }
        if(color == 'w'){
            ArrayList<String> mov1 = get_avaliable_AIindexs();
            //set_color('b'); //Checking opp moves
            ArrayList<String> mov2 = get_avaliable_indexs();
            //set_color('w'); //Checking player moves
            
            if(mov1.isEmpty() && mov2.isEmpty()){
                winner = peice_count();
                return true; //game over, no one has moves
            }
            
        }
        
        
        
        //check for overtakes 
        
        //System.out.println("Not Game Over");    
        return false;
    }
    
    public boolean validate_move(String m) //Ensures new move is not bad, validates move structure ex. 'a2' or '11' Not index adjuested
    {
        
 
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
     
        if(board[row][col] != ' '){ //if already taken?
          System.out.println("BoardSpace already Selected in Validate");
          return false;
        }

        //find avaliable moves      
         ArrayList<String> pos_moves =get_avaliable_indexs(); //possible moveset
         
         
         //System.out.println("Number of avaliable moves: "+pos_moves.size());
         for(int i=0;i<pos_moves.size();i++){
             //System.out.println(i+". AV: " + pos_moves.get(i));
             
            if(pos_moves.get(i).equals(m)){
                return true; 
            }
             
             
             
             
         }
             
         
         
      return false;  
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
                  
              }
                
            }
        }
        
        
        
        String temp;
        for(int i =0; i< rows.size();i++) //make funtion for find line and find cross to see if a jump is possiable. 
        {
            
            //check up one
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i));
            if(valid_index(temp))
            {
              if(jump_row_test(temp) || jump_col_test(temp))  
                valid_moves.add(temp);  
            }
            //check down one
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i));
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            //check left
            temp= Integer.toString(cols.get(i)) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            // check right
            temp= Integer.toString(cols.get(i)) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            
            //Checking diagonal positions
            //up right
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            
            //up left
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            //down right
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            //down left
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
        }
        
        Set<String> mySet = new HashSet<String>();   //Removes potential duplicate moves
        for(int i =0; i < valid_moves.size(); i++){
            mySet.add(valid_moves.get(i));
            
        }
        ArrayList<String> t = new ArrayList<String>(mySet);

       
       return t; //passes back indexs of spots that are valid moves ex. [0][0] 
    }
    
    public ArrayList<String> get_avaliable_AIindexs()//returns all possiable AI moves on board
    {
        //Switch for checking
        
        if(color == 'w'){
            
             color= 'b';
             
        }
        else if(color == 'b'){
             color = 'w';
        }
        
        
        
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
                 
              }
                
            }
        }
        
        
        
        String temp;
        for(int i =0; i< rows.size();i++) //make funtion for find line and find cross to see if a jump is possiable. 
        {
            
            //check up one
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i));
            if(valid_index(temp))
            {
              if(jump_row_test(temp) || jump_col_test(temp))  
                valid_moves.add(temp);  
            }
            //check down one
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i));
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            //check left
            temp= Integer.toString(cols.get(i)) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            // check right
            temp= Integer.toString(cols.get(i)) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_row_test(temp) || jump_col_test(temp))
                    valid_moves.add(temp);  
            }
            
            //Checking diagonal positions
            //up right
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            
            //up left
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i)+1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            //down right
            temp= Integer.toString(cols.get(i)+1) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
            //down left
            temp= Integer.toString(cols.get(i)-1) + Integer.toString(rows.get(i)-1);
            if(valid_index(temp))
            {
                if(jump_diag_test(temp))
                    valid_moves.add(temp);  
            }
        }
        
        Set<String> mySet = new HashSet<String>();   //Removes potential duplicate moves
        for(int i =0; i < valid_moves.size(); i++){
            mySet.add(valid_moves.get(i));
            
        }
        ArrayList<String> t = new ArrayList<String>(mySet);

        if(color == 'b')
             color = 'w';
        else if(color == 'w')
             color = 'b';
       
       return t; //passes back indexs of spots that are valid moves ex. [0][0] 
    }
    
    public boolean jump_row_test(String m){ //Checks in the X direction left and right
        
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        
        int i =0;
        
        for(int x = col-1; x > -1; x--){
 
            if(board[row][x] == op_color){
               i++; 
            }
            
            if(board[row][x] == ' '){
                break;
            }
            
            if(board[row][x] == pl_color && i >0){
               return true;
            }
            //-----test
            if(board[row][x] == pl_color && i ==0)
                break;
            //-----test
            
        }
        
        i = 0;
        for(int x = col+1; x < 8; x++){

            if(board[row][x] == op_color){
               i++;
            }
            
            if(board[row][x] == ' '){    
                break;
            }
            
            if(board[row][x] == pl_color && i >0){
               return true;
            }
            //-----test
            if(board[row][x] == pl_color && i ==0)
                break;
            //-----test
            
        }
       
        
        
        return false;
    } //to be done
    
    public boolean jump_col_test(String m){ //Checks for jumps in vertical direction
        
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        

        int i =0; 
        for(int x = row-1; x > -1; x--){
 
            if(board[x][col] == op_color){
               i++; 
            }
            
            if(board[x][col] == ' '){
                break;
            }
            
            if(board[x][col] == pl_color && i >0){
               return true;
            }
            //-----test
            if(board[x][col] == pl_color && i ==0)
                break;
            //-----test
            
        }
        
        i = 0;
        for(int x = row+1; x < 8; x++){

            if(board[x][col] == op_color){
               i++;
            }
            
            if(board[x][col] == ' '){    
                break;
            }
            
            if(board[x][col] == pl_color && i >0){
               return true;
            }
            //-----test
            if(board[x][col] == pl_color && i ==0)
                break;
            //-----test
            
        }
       
        
        
        return false;
        
    } //to do 
    
    public boolean jump_diag_test(String m){ //Checks for diagonal jumps
        
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        
        int i =0;//Jump counter
        int y = 0;
        y = col-1;
        for(int x =row-1;x > 0;x--){  //up and to the left
            
            if(y <0)
                break;
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag up left test ");
                    return true;
                } 
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                y--;
                
             
        }
        i=0;
        y = col+1;
        for(int x =row-1;x > 0;x--){  //up and to the right
            
                if(y>7)
                    break;
                
            
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag up right");
                    return true;
                } 
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                
             y++;
             
        }
        i=0;
        y = col-1;
        for(int x =row+1;x < 8;x++){  //down to the left
            
                if(y <0)
                    break;
            
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag down left");
                    return true;
                }
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                
                y--;
                
             
        }
        i=0;
        y = col+1;
        for(int x =row+1;x < 8;x++){  //down to the right
            
                if(y >7)
                    break;
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag down right");
                    return true;
                }
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                
                
             y++;
        }
        
        
        
        return false;
    } // to do
    
    public boolean peform_row_jump(String m){//Peforms jumps in row direction if avaliable
        
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        
        int i =0;
        boolean left = false;
        boolean right = false;
        
        for(int x = col-1; x > -1; x--){
 
            if(board[row][x] == op_color){
               i++; 
            }
            
            if(board[row][x] == ' '){
                break;
            }
            
            if(board[row][x] == pl_color && i >0){
               left = true;
            }
            //-----test
            if(board[row][x] == pl_color && i ==0)
                break;
            //-----test
            
        }
        
        i = 0;
        for(int x = col+1; x < 8; x++){

            if(board[row][x] == op_color){
               i++;
            }
            
            if(board[row][x] == ' '){    
                break;
            }
            
            if(board[row][x] == pl_color && i >0){
               right = true;
            }
            //-----test
            if(board[row][x] == pl_color && i ==0)
                break;
            //-----test
            
        }
        
        
        i =0;
        //--------peforming jump on directions
        if(left)
        {
            for(int x = col-1; x > -1; x--){

                if(board[row][x] == pl_color && i >0){
                    break;
                }

                if(board[row][x] == op_color){
                    i++;
                board[row][x] = pl_color; 
                }

                if(board[row][x] == ' '){
                    break;
                }
                //-----test
                if(board[row][x] == pl_color && i ==0)
                    break;
                //-----test



            }
        }
        
        i = 0;
        if(right)
        {
            for(int x = col+1; x < 8; x++){

                if(board[row][x] == pl_color && i >0){
                    break;
                }

                if(board[row][x] == op_color){
                i++;
                board[row][x] = pl_color;
                }

                if(board[row][x] == ' '){    
                    break;
                }
                //-----test
                if(board[row][x] == pl_color && i ==0)
                    break;
                //-----test



            }
        }
 
        return false;
    }
    public boolean peform_col_jump(String m){ //Peforms jumps in vertical direction if avaliable
        
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        
        int i =0; 
        boolean up = false;
        boolean down = false;
         
        for(int x = row-1; x > -1; x--){
 
            if(board[x][col] == op_color){
               i++; 
            }
            
            if(board[x][col] == ' '){
                break;
            }
            
            if(board[x][col] == pl_color && i >0){
               up = true;
            }
            //-----test
            if(board[x][col] == pl_color && i ==0)
                break;
            //-----test
            
        }
        
        i = 0;
        for(int x = row+1; x < 8; x++){

            if(board[x][col] == op_color){
               i++;
            }
            
            if(board[x][col] == ' '){    
                break;
            }
            
            if(board[x][col] == pl_color && i >0){
               down = true;
            }
            //-----test
            if(board[x][col] == pl_color && i ==0)
                break;
            //-----test
            
        }

        i =0; 
        if(up)
        {
            for(int x = row-1; x > -1; x--){

                if(board[x][col] == pl_color && i >0){
                break;
                }

                if(board[x][col] == op_color){
                i++;
                board[x][col] = pl_color;

                }

                if(board[x][col] == ' '){
                    break;
                }
                //-----test
                if(board[x][col] == pl_color && i ==0)
                    break;
                //-----test

            }
        }
        
        i = 0;
        if(down)
        {
            for(int x = row+1; x < 8; x++){

                if(board[x][col] == pl_color && i >0){
                break;
                }

                if(board[x][col] == op_color){
                i++;
                board[x][col] = pl_color;
                }

                if(board[x][col] == ' '){    
                    break;
                }
                //-----test
                if(board[x][col] == pl_color && i ==0)
                    break;
                //-----test

            }
        }
       
        
        
        return true;
    }
    public boolean peform_diag_jump(String m){ //peforms jumps in diagonal direction if avaliable
    
        char op_color = '@';
        char pl_color = 'O';
        
        if(color == 'b'){
            op_color = 'O';
            pl_color = '@';
        }
        if(color == 'w'){
            op_color = '@';
            pl_color = 'O';
        }
                    
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        int col = Character.getNumericValue(c);
        int row = Character.getNumericValue(r);
        
        boolean upleft = false;
        boolean upright = false;
        boolean downleft = false;
        boolean downright = false;
        
        int i =0;//Jump counter
        
        int y = 0;
        y = col-1;
        for(int x =row-1;x > -1;x--){  //up and to the left
            
                if(y<0)
                    break;
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag up left test ");
                    upleft = true;
                }
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                
             y--;
        }
        i=0;
        y = col+1;
        for(int x =row-1;x > -1;x--){  //up and to the right
            
                 if(y >7)
                     break;
                
            
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag up right");
                    upright = true;
                } 
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
               
             y++;
             
        }
        i=0;
        y = col-1;
        for(int x =row+1;x < 8;x++){  //down to the left
            
                if(y <0)
                    break;
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag down left");
                    downleft=true;
                }
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                y--;
             
        }
        i=0;
        y = col+1;
        for(int x =row+1;x < 8;x++){  //down to the right
            
                if(y >7)
                    break;
               
                if(board[x][y] == op_color){
                    i++; 
                }

                if(board[x][y] == ' '){
                    break;
                }

                if(board[x][y] == pl_color && i >0){
                    //System.out.println("Jump diag down right");
                    downright = true;
                }
                //-----test
                if(board[x][y] == pl_color && i ==0)
                    break;
                //-----test
                
                y++;
             
        }
        
        
        //Peforming jumps----------------------------------------
        i=0;
        if(upleft){
            
            y = col-1;
            for(int x =row-1;x > -1;x--){  //up and to the left
                
                if(y <0)
                    break;
                
                if(board[x][y] == pl_color && i >0){
                    break;
                }
                
                if(board[x][y] == op_color){
                    i++;
                    board[x][y] = pl_color;
                }

                if(board[x][y] == ' '){
                    break;
                }

                y--;
             
            }
        }
        
        i=0;
        if(upright){
            
            y = col+1;
            for(int x =row-1;x > -1;x--){  //up and to the right
            
                if(y >7)
                    break;
             
                
                if(board[x][y] == pl_color && i >0){
                    break;
                }
                if(board[x][y] == op_color){
                    i++;
                    board[x][y] = pl_color;
                }

                if(board[x][y] == ' '){
                    break;
                }

                 
                y++;
             
            }
            
        }
        
        i=0;
        if(downleft){
            
            y = col-1;
            for(int x =row+1;x < 8;x++){  //down to the left

                if(y <0)
                    break;
                
                if(board[x][y] == pl_color && i >0){
                    break;
                }
                
                if(board[x][y] == op_color){
                    i++;
                    board[x][y] = pl_color;
                }

                if(board[x][y] == ' '){
                    break;
                }

                y--;
                
            }
            
        }
        
        i=0;
        if(downright){
            
              y = col+1;
            for(int x =row+1;x < 8;x++){  //down to the right
            
                
                if(y >7)
                    break;
                
                if(board[x][y] == pl_color && i >0){
                    break;
                }
               
                if(board[x][y] == op_color){
                    i++; 
                    board[x][y] = pl_color;
                            
                }

                if(board[x][y] == ' '){
                    break;
                }

                y++;
             
            }
            
        }
            
        
        
        return true;
        
    }
    
    public void undo(){     //Gets a previous copy of the baord from the player (saved in Move()
       int size = previous_boards.size();
       char[][] temp = previous_boards.get(size-1); 
       //display_board();
       board = temp;
       //display_board();
       
       
    }
    
    public boolean move(String n) //interface for actully moving //F4
    {
        String m = move_to_index(n);
        
       if("NO".equals(m)){
           System.out.println("Invalid move");
           return false;
       }

       if(!validate_move(m)){
            System.out.println("\t Invalid move Selection");
            return false;
       }
       
       
       
       
       char ca = m.charAt(0); 
       char ra = m.charAt(1); 
       int col = Character.getNumericValue(ca);  
       int row = Character.getNumericValue(ra); 
       //System.out.println("Move: "+m+":: "+col+","+row);
      
       if(color == 'w'){
           
           previous_boards.add((char[][])deepClone(board));
           board[row][col]='O';
           
           if(jump_row_test(m))
               peform_row_jump(m);
           if(jump_col_test(m))
               peform_col_jump(m);
           if(jump_diag_test(m))
               peform_diag_jump(m);
           
           ;
           
           return true;
       }
       
       if(color == 'b'){
           previous_boards.add((char[][])deepClone(board));
           board[row][col]='@';
           
           if(jump_row_test(m))
               peform_row_jump(m);
           if(jump_col_test(m))
               peform_col_jump(m);
           if(jump_diag_test(m))
               peform_diag_jump(m);
           
           
           
           return true;
       }
       
        
        
      return false;  
    }
    
    public boolean ai_move(String n) //interface for actully moving //F4
    {
        
        
        String m = move_to_index(n);
        
        if(color == 'w')
             color= 'b';
        else if(color == 'b')
             color = 'w';
        
       if("NO".equals(m)){
           System.out.println("Invalid move");
           return false;
       }

       if(!validate_move(m)){
            System.out.println("\t Invalid move Selection");
            return false;
       }
       
       
       
       
       char ca = m.charAt(0); 
       char ra = m.charAt(1); 
       int col = Character.getNumericValue(ca);  
       int row = Character.getNumericValue(ra); 
       //System.out.println("Move: "+m+":: "+col+","+row);
      
       if(color == 'w'){
           board[row][col]='O';

           
           if(jump_row_test(m))
               peform_row_jump(m);
           if(jump_col_test(m))
               peform_col_jump(m);
           if(jump_diag_test(m))
               peform_diag_jump(m);
            
           color = 'b'; //swtich back
           return true;
       }
       
       if(color == 'b'){
           board[row][col]='@';
           
           if(jump_row_test(m))
               peform_row_jump(m);
           if(jump_col_test(m))
               peform_col_jump(m);
           if(jump_diag_test(m))
               peform_diag_jump(m);

           color = 'w'; //swtich back
           return true;
       }
       
        
        
      return false;  
    }
     
    public char [][] get_board() //Returns copy of current raw board state char array
    {
        char [][] temp_board = board; //protection
        
        return temp_board;
    }
    
    public void display_board() //Displays board in terminal 
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
    
    public void display_board(Socket socket) throws IOException //Returns board to scoket for remote printing
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
    
        
     
    public boolean valid_index(String m)    //validates index locations, different than validate internal use
    {
    
        
        int row = 0;
        int col = 0;
        
        
        if(m.length() != 2)
            return false;  
        
        
        
        char c = m.charAt(0);
        char r = m.charAt(1);
        
        
        
        
        if(c == '0'||c =='1'|| c=='2'||c == '3'||c =='4'|| c=='5'||c=='6'||c=='7')    
           col = Character.getNumericValue(c); 
        else
            return false;
        
        
        if(r == '0'||r =='1'|| r=='2'||r == '3'||r =='4'|| r=='5'||r=='6'||r=='7')
            row = Character.getNumericValue(r); 
        else
            return false;
        
       
        
              
        if(board[row][col] != ' ')
            return false;
        
      return true; 
    }
    
    
    
    public boolean random_ai()  //Chooses and moves according to random avaliable move
    {
        //char c = game.color; //Getting color for this instance
        Random generator = new Random(); //Random instance
        
        if(color == 'b') //swtiching color becasuse of server structure, probably remove for client server
            color = 'w';
        else if(color == 'w')
            color = 'b';
        
        ArrayList<String> move_list = get_avaliable_indexs(); //Gets all indexs on current board for instance color
        if(move_list.isEmpty())
            return false;
        
        int random_move = generator.nextInt(move_list.size()); //In the future it will select from avaliable index positions      
        String AIMove = move_list.get(random_move);
        
        
        //System.out.println("AI Move: "+ AIMove);
        String final_move = index_to_move(AIMove);
        System.out.println("AI Move: "+ final_move);
        
        if(ai_move(final_move))
            return true;
        
        return false;
    }
    
    public String get_ASCII_board() //returns board formated for single string return;
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
    
   public static Object deepClone(Object object) {      //Deep Copys objects, only used in undo
   try {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     ObjectOutputStream oos = new ObjectOutputStream(baos);
     oos.writeObject(object);
     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
     ObjectInputStream ois = new ObjectInputStream(bais);
     return ois.readObject();
   }
   catch (Exception e) {
     e.printStackTrace();
     return null;
   }
 } 
    
    
}//end of class
