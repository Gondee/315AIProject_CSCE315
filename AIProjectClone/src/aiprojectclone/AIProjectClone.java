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
        newGame.controller();
        GameBoard game = new GameBoard('w');// initial color
        
        Scanner scanner = new Scanner (System.in);
        
        
        while(true)
        {
        System.out.print("Enter your move: ");  
        String m = scanner.next();
      
        game.set_color('w');
        game.move(m);
        game.display_board();
        game.set_color('b');
        game.ramdom_ai();
        game.display_board();
       
        
        }
        
        // TODO code application logic here
    }
}
