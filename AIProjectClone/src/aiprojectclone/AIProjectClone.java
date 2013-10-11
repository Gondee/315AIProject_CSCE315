/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.util.Scanner;

/**
 *
 * @author joshkruger
 */
public class AIProjectClone {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String IP = "192.168.1.90";
        int Port = 22;
        boolean AIFlag = true;
        int S_CFlag = 2; 
        int Difficulty = 1;
        
        Reversi newGame = new Reversi(IP,Port,AIFlag,S_CFlag,Difficulty); 
            //Constructor that adds the values
        newGame.controller();
        
        
        Scanner scanner = new Scanner (System.in);
        System.out.print("Enter your move: ");  
        String m = scanner.next();
      
        
        
        
        // TODO code application logic here
    }
}
