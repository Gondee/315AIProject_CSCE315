/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 */
public class AIProjectClone {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String ExampleIP = "192.168.1.90";
        int ExamplePort = 22;
        boolean ExampleAIFlag = false;
        int ExampleS_CFlag = 1; 
        int Example_Difficulty = 1;
        
        Reversi newGame = new Reversi(ExampleIP,ExamplePort,ExampleAIFlag,ExampleS_CFlag,Example_Difficulty); 
            //Constructor that adds the values
        newGame.controller();
        
       
        boolean testmove = newGame.game.move("4c");
        
        
        
        // TODO code application logic here
    }
}
