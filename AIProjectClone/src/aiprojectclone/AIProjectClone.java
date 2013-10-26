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
        System.out.println("For test purpoes, start server 's' or client 'c' or 'l' for local play");
        String c = scanner.next();
        
        //I will create a seperate branch later for client code
        //for now we can just use this swtich below 
        
        
        if("s".equals(c)){
            int Port = 5888;
            Server s = new Server(Port);
            s.listen();
              
            
        }
        else if ("c".equals(c)){
            
    	SetupGUI test = new SetupGUI();
        test.start();
       
        }
        else if("l".equals(c)){
        //Local testing below
         
        GameBoard game = new GameBoard('w');// initial color

            game.display_board();

            System.out.print("Enter your move: ");  
            String m = scanner.next();

            game.set_color('w');
            game.move(m);
            game.display_board();

            ArrayList<String> s = game.get_avaliable_AIindexs();
            for(int i=0;i<s.size();i++)
            {
                System.out.println("Move: "+s.get(i));

            }
            game.ai_move("d3");
            game.display_board();
        
        }


    }
}


