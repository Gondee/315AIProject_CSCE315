/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.CountDownLatch;
/**
 *
 * @author joshkruger
 */
public class Client {
    
    GameBoard clientBoard; //Where ever client ends up, needs a copy of gameboard
                           //I will have the GUI updating copys of this gameboard
    String ip;
    int port;
    String client;
    String server;
    char color;
    Object syncObj;
    
    public Client(String cli,String ser,String ipp, int ppo,String col) throws UnknownHostException, IOException, InterruptedException{
        client = cli;
        server = ser;
        ip = ipp;
        port = ppo;
        
        if("Black".equals(col))
            color = 'b';
        else if("White".equals(col))
            color = 'w';
        
        
        start_client();
        
        
    }
    
    private void start_client() throws UnknownHostException, IOException, InterruptedException{
        clientBoard = new GameBoard(color);
        ReversiGUI gui = new ReversiGUI(clientBoard, syncObj);
        gui.update_board(clientBoard);
        play_game(gui);
        
    }
    private void play_game(ReversiGUI gui) throws UnknownHostException, IOException, InterruptedException
    {
       Socket Server = new Socket(ip, port);
       BufferedReader in = new BufferedReader(new InputStreamReader(Server.getInputStream()));      
       PrintWriter out = new PrintWriter(Server.getOutputStream(), true);
       String input;
       
       input = in.readLine();
       gui.show_message(input);
       
       out.println("gui");
       out.println(color);
       out.println(server);
       out.println("display");
       
       while ((input = in.readLine()) != null) {
           gui.show_message(input);
           synchronized(syncObj){
                while (gui.get_move().equalsIgnoreCase("null"))
                    syncObj.wait();
                input = gui.get_move();
                out.println(input);
                gui.set_move_null();
       }
           clientBoard.ai_move(in.readLine());
           gui.update_board(clientBoard);
    }
    }
    
public void send_move(String m){
        
        
    }
            
}
    
    

