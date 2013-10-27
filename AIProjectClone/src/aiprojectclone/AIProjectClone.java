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
import java.util.ArrayList;


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
    	
        
        
        
        
        
        Scanner scanner = new Scanner (System.in);
        System.out.println("For test purpoes, (start server 's') or (client 'c') or ('l' for local play)");
        String c = scanner.next();
        
        //I will create a seperate branch later for client code
        //for now we can just use this swtich below 
        
        
        if("s".equals(c)){
            int Port = 5888;
            Server s = new Server(Port);
            s.listen();
              
            
        }
        else if ("c".equals(c)){
            
    	SetupGUI test = new SetupGUI(); //Not functinoal yet
        test.start();
        
        
        
       
        }
        else if("l".equals(c)){
        //Local testing below
        ReversiGUI go = new ReversiGUI(); 
        GameBoard game = new GameBoard('w');// initial color

            game.display_board();
            AdvancedAI a = new AdvancedAI("hard");

            while(true)
            {
            System.out.print("Enter your move: ");  
            String m = scanner.next();
            game.move(m);
            game.display_board();
            go.update_board(game);
            a.ai_move(game);
            go.update_board(game);
            
            }
        
        }


    }
}


