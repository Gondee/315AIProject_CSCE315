/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author joshkruger
 */
public class AIProjectClone {

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	
        
        //int Port = 5888;
        //Reversi newGame = new Reversi(Port);
        //newGame.s.listen();
        
    //	Reversi newGame = new Reversi(IP,Port,AIFlag,S_CFlag,Difficulty);  
    	
    //	newGame.server();
    	
       
        
        
        //Reversi newGame = new Reversi(IP,Port,AIFlag,S_CFlag,Difficulty);     
        //newGame.controller();
        
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
        //game.random_ai();
        //game.display_board();
       
        
        }
        
        // TODO code application logic here
    }
}
