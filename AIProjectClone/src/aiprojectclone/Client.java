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
    String remote_ip;
    int remote_port;
    String remote_diff;
    char color;
    final Object syncObj = new Object();
    
    public Client(String cli,String ser,String ipp, int ppo,String col, String r_ip, int r_p) throws UnknownHostException, IOException, InterruptedException{
        client = cli;
        server = ser;
        ip = ipp;
        port = ppo;
        remote_ip = r_ip;
        remote_port = r_p;
        
        if("Black".equals(col))
            color = 'b';
        else if("White".equals(col))
            color = 'w';
        
        if(client == "Human")
        	client = "HUMAN-AI";
        else if(client == "AI-Easy") {
        	client = "AI-AI";
        	remote_diff = "EASY";
        } else if(client == "AI-Medium") {
        	client = "AI-AI";
        	remote_diff = "MEDIUM";
        } else if(client == "AI-Hard") {
        	client = "AI-AI";
        	remote_diff = "HARD";
        }
        	
        	
                
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
       String client_move;
       
       input = in.readLine();
       gui.show_message(input);
      // input = in.readLine();
       
       out.println("gui");
       out.println(color);
       out.println(server);
       if(client == "HUMAN-AI") {
    	   out.println(client);
      // input = in.readLine();
    //   gui.show_message(input);
   //    out.println("display");
       
	       while ((input = in.readLine()) != null) {
	    	   if(input == "OK")
	    		   input = in.readLine();
	           //gui.show_message(input);
	           synchronized(syncObj){
	                while (gui.get_move().equalsIgnoreCase("NULL"))
                        {
	                    syncObj.wait();
                            
                            if(gui.get_move().equalsIgnoreCase("NULL") && gui.get_undo())
                            {
                                
                                //System.out.println("UNDO");
                                clientBoard.undo();
                                gui.reset_undo();
                                gui.update_board(clientBoard);
                                out.println("UNDO");
                                input = in.readLine();
                                
                            }
                            
                        }
                        //System.out.println("MOVE");
	                client_move = gui.get_move();
	                out.println(client_move);
	                gui.set_move_null();
	           }
	           input = in.readLine();
	           if("ILLEGAL".equals(input))
	        	   gui.show_message("ILLEGAL MOVE");          
	
	           else {
	        	   clientBoard.move(client_move);
		           gui.update_board(clientBoard);
		           clientBoard.ai_move(input);
		           gui.update_board(clientBoard);
	           }
	
	      }
      }
       else {
    	   out.println(client);
    	   out.println(remote_ip);
    	   out.println(remote_port);
    	   out.println(remote_diff);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   gui.show_message(input);
    	   
    	   input = in.readLine();
    	   System.out.println(input);
    	   clientBoard.move(input);
    	   gui.update_board(clientBoard);
    	   
	       while ((input = in.readLine()) != null) {
	    	   if(!"".equals(input)) {
                       //System.out.println("INPUT: "+input);
	    		   if("GAME OVER".equals(input)) {
	    			   String Win ="";
                                    if(clientBoard.peice_count() =='b')
                                        Win = "Black";
                                    else if(clientBoard.peice_count()=='w')
                                        Win = "White";
                                    else if(clientBoard.peice_count() == 't')
                                        Win = "Tie";
                                    
	    			   gui.show_message("GAME OVER: Winner: "+Win);
	    		   }
	    		   clientBoard.ai_move(input);
	    	   	   gui.update_board(clientBoard);
	    	   }
	    	   
	    	   input = in.readLine();
	    	   if(!"".equals(input)) {
                        //System.out.println("INPUT: "+input);
	    		   if("GAME OVER".equals(input)) {
                                    String Win ="";
                                    if(clientBoard.peice_count() =='b')
                                        Win = "Black";
                                    else if(clientBoard.peice_count()=='w')
                                        Win = "White";
                                    else if(clientBoard.peice_count() == 't')
                                        Win = "Tie";
                                    
	    			   gui.show_message("GAME OVER: Winner: "+Win);
	    			   break;
	    		   }
	    		   clientBoard.move(input);
	    		   gui.update_board(clientBoard); 
	    	   }
	       }   	   
       }
     
    }
  
    
public void send_move(String m){
        
        
    }
            
}
    
    

