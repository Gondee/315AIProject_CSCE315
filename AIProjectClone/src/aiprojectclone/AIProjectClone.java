/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.net.ServerSocket;
import java.net.Socket;

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
    	
        
        int Port = 5888;
        Server s = new Server(Port);
    	  

        
        //Reversi newGame = new Reversi(IP,Port,AIFlag,S_CFlag,Difficulty);     
        //newGame.controller();
        
//        GameBoard game = new GameBoard('w');// initial color
//        
//        Scanner scanner = new Scanner (System.in);
//        
//
//        while(true)
//        {
//        System.out.print("Enter your move: ");  
//        String m = scanner.next();
//      
//        game.set_color('w');
//        game.move(m);
//        game.display_board();
//        //game.set_color('b');
//        game.random_ai();
//        game.display_board();
//       
//        
//        }
        
        // TODO code application logic here
    }
}
