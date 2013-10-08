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
        int ExampleAIFlag = 1;
        int ExampleS_CFlag = 1; 
        int Example_Difficulty = 1;
        
        Reverse newGame = new Reverse(ExampleIP,ExamplePort,ExampleAIFlag,ExampleS_CFlag,Example_Difficulty); 
            //Constructor that adds the values
        newGame.controller();
        
       
        
        
        
        
        // TODO code application logic here
    }
}
