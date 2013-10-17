/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 * 
 *      HOW TO INTERFACE WITH GAMEBOARD
 *      FUNCTIONS IN GAMEBOARD TAKE [A-F][1-8] (in index)
 *      IF YOU HAVE MOVE F3 CALL move_to_index() to change
 *      it into a form that every other function accepts!
 * 
 * 
 */
public class AdvancedAI {
    
    protected int difficulty;
    protected GameBoard game;
    
    public AdvancedAI(int d){
        difficulty = d;
            // 0 = easy
            // 1 = medium 
            // 2 = hard
    }
    
    
    
    public String move(GameBoard g){
        //Just an idea, but take in the board, or a copy of the board
        game = g; // copy of gameboard
        
        if(difficulty == 0)
            return easy_ai();
        else if(difficulty == 1)
            return medium_ai();
        else if(difficulty == 2)
            return hard_ai();
        else
            return "NULL";
            
    }
    
    
    
    protected String easy_ai(){
      
        return " "; //returns move
    }
    
    protected String medium_ai(){
        
        return " ";//returns move
    }
    
    protected String hard_ai(){
        
        return " ";//returns move
    }
    
    
}
