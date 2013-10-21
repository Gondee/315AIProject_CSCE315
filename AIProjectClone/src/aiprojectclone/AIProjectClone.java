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
        s.listen();
    	
       
 /*     
        //Local testing below
        /* 
        GameBoard game = new GameBoard('w');// initial color
        
        Scanner scanner = new Scanner (System.in);
        

        game.display_board();
        
        System.out.print("Enter your move: ");  
        String m = scanner.next();
      
        game.set_color('w');
        game.move(m);
        game.display_board();

        GameTree gt = new GameTree(game,2,'b');
       
        gt.build_tree();
*/       

        
        
        // TODO code application logic here
    

