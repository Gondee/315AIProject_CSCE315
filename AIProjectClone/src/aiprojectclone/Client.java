/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 *
 * @author joshkruger
 */
public class Client {
    
    GameBoard clientBoard; //Where ever client ends up, needs a copy of gameboard
                           //I will have the GUI updating copys of this gameboard
    String ip;
    int port;
    String human_ai;
    String client_difficulty;
    String server_difficulty;
    char color;
    
    public Client(String humanai,String server_diff,String ipp, String ppo,String col) throws UnknownHostException, IOException{
        human_ai = humanai;
        server_difficulty = server_diff;
        ip = ipp;
        port = Integer.parseInt(ppo);
        
        if("Black".equals(col))
            color = 'b';
        else if("White".equals(col))
            color = 'w';
        
        if(human_ai == "AI-Easy") {
        	human_ai = "AI-AI";
        	client_difficulty = "EASY";
        } else if(human_ai == "AI-Medium") {
        	human_ai = "AI-AI";
        	client_difficulty = "MEDIUM";
        } else if(human_ai == "AI-Hard") {
        	human_ai = "AI-AI";
        	client_difficulty = "HARD";
        } else if(human_ai == "Human")
        	human_ai = "HUMAN-AI";
        
        start_client();       
    }
    
    private void start_client() throws UnknownHostException, IOException{
        clientBoard = new GameBoard(color);
        ReversiGUI gui = new ReversiGUI(clientBoard);
        gui.update_board(clientBoard);
        
        Socket s = new Socket(ip, port);
        BufferedReader input =
            new BufferedReader(new InputStreamReader(s.getInputStream()));
		PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        String answer = input.readLine();        
        JOptionPane.showMessageDialog(null, answer);
        out.println(color);
        answer = input.readLine();
        JOptionPane.showMessageDialog(null, answer);
        
        if(human_ai == "HUMAN-AI") {
        	out.println(human_ai);
            answer = input.readLine();
            JOptionPane.showMessageDialog(null, answer);
        }
            
       
        
        
        System.exit(0);        
    }
    
    public void send_move(String m){
        
        
    }
            
    
    
    
}
