package aiprojectclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

public class Server {
	
    private GameBoard board;
    private int port; // Port number of the server	
    private int remote_port; // Hostname for remote server for AI vs AI
    private String remote_hostname; // Port number of the remote server for AI vs AI
    
    char client_color;
    char server_color;
    
    
    private boolean gui_flag;
    private boolean human_ai; //activate this copy of AI?
    private String remote_ai_diff; //Difficulty of AI of remote AI
    private String local_ai_diff; //Difficulty of AI of local AI

    
    public Server(int p) {
    	port = p;
    }
    
    public void listen() throws IOException, InterruptedException {
 	   
 	   ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
                	System.out.println("SUCCESS\n");
                    connection_handler(socket);
                } finally {
                    socket.close();
                }
            }
        }
        finally {
            listener.close();
        }
    }
    
    public int connection_handler(Socket socket) throws IOException, InterruptedException {
 	  
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));      
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		String input;
		
		out.println("WELCOME");
                
                
		in = beginning_sequence(in, out);
		
		
		if(human_ai) {
			
			AdvancedAI AI = new AdvancedAI(local_ai_diff);
	
			board = new GameBoard(client_color);
	        
		//	board.display_board(socket);
	                
			        
			out.println("MAKE FIRST MOVE");
	        boolean display = false;    
			
			while (true) {
	            input = in.readLine();
			    if (input.equalsIgnoreCase("EXIT"))
				    return 0;
	                    else if (input.equalsIgnoreCase("DISPLAY"))
	                        display = !display;
	                    else if (input.equalsIgnoreCase("UNDO"))
	                        board.undo();
	                    else if(board.check_state()) {
	                    	out.println("GAME OVER"); 
	                    	TimeUnit.SECONDS.sleep(8);
	                    	return 0;
	                    }
	                    else if("".equals(input))
                    	{}
	                    else if (input.charAt(0)==';')
	                        out.println(input.substring(1));
	                    else if(!board.move(input))
	                    	out.println("ILLEGAL");
	                    else if(board.check_state()) {
                                //System.out.println("GameREALLYOVER");
	                    	out.println("GAME OVER"); 
	                    	TimeUnit.SECONDS.sleep(10);
	                    	return 0;
	                    }
	                    else
	                    	System.out.println(input);
	                    	out.println(AI.ai_move(board));
	                    if(board.check_state()) {
                                //System.out.println("GameREALLYOVER");
	                    	out.println("GAME OVER"); 
	                    	TimeUnit.SECONDS.sleep(10);
	                    	return 0;
	                    }	 
	                    out.println("OK");
	                    if (display)
	                    {
	                       // System.out.println("displayed");
	                        board.display_board(socket);
	                    }
	                        	
			}
		}
		else {
			Socket opponent = new Socket(remote_hostname, remote_port);
			BufferedReader in_opp = new BufferedReader(new InputStreamReader(opponent.getInputStream()));      
			PrintWriter out_opp = new PrintWriter(opponent.getOutputStream(), true);
			
			AdvancedAI AI = new AdvancedAI(local_ai_diff);			
			board = new GameBoard('b'); // Set as black so that server AI moves white pieces
			
			String local_ai_move = "";
			String remote_ai_move = "";
			
			input = in_opp.readLine();
			out.println("CONNECTED TO OTHER SERVER");
			
			out_opp.println("junk"); // test for gui
			
			input = in_opp.readLine(); // Take in CHOOSE WHITE OR BLACK
			//out.println(input);
			out_opp.println("WHITE");
			
			input = in_opp.readLine(); // Take in OK
			//out.println(input);
			
			input = in_opp.readLine(); // Take in HUMAN-AI or AI-AI
			//out.println(input);
			out_opp.println("HUMAN-AI");
			
			input = in_opp.readLine(); // Take in CHOOSE SERVER DIFFICULTY
			//out.println(input);
			out_opp.println(remote_ai_diff);
			
			input = in_opp.readLine(); // Take in MAKE FIRST MOVE
			//out.println(input);
			
			local_ai_move = AI.ai_move(board);
			System.out.println(local_ai_move);
			out.println(local_ai_move);
			out_opp.println(local_ai_move);
			
			while (true) {
                remote_ai_move = in_opp.readLine();
            	if("GAME OVER".equals(remote_ai_move)) {
                    //System.out.println("GameREALLYOVER");
            		out.println(remote_ai_move);
            		TimeUnit.SECONDS.sleep(10);
            		return 0;
            	}
            	
                //System.out.println("remote"+remote_ai_move);
                if(!"".equals(remote_ai_move)) 
                	board.move(remote_ai_move);
                
                out.println(remote_ai_move);               
            	input = in_opp.readLine(); // Take in OK
            	if("GAME OVER".equals(input)) {
                    //System.out.println("GameREALLYOVER");
            		out.println(input);
            		TimeUnit.SECONDS.sleep(8);
            		return 0;
            	}
            	
            	local_ai_move = AI.ai_move(board);
            	//System.out.println("local"+local_ai_move);
            	out_opp.println(local_ai_move);
            	out.println(local_ai_move);                    
			}			
		}
		               
	}

 BufferedReader beginning_sequence(BufferedReader in, PrintWriter out) throws IOException{
                String input;
                BufferedReader newin;
      		boolean valid = false; 
		do {	
                    //   out.println("Press any key and enter");
                        input = in.readLine();
                        

                        if (input.equals("gui")) {
                        	gui_flag = true;
                        	return gui_handler(in);
                        }
                                           
                   
                        
			out.println("CHOOSE SIDE (WHITE OR BLACK)");
            
			input = in.readLine();
			//System.out.println(input);

			if (input.equalsIgnoreCase("WHITE")) {
				client_color = 'w';
				server_color = 'b';
				out.println("OK");
			} else if (input.equalsIgnoreCase("BLACK")) {
				client_color = 'b';
				server_color = 'w';	
				out.println("OK");
			} else{
                            out.println("INVALID CHOICE");
                            newin = beginning_sequence(in, out);
                            return newin;
                        }
                        out.println("CHOOSE GAME TYPE (HUMAN-AI OR AI-AI)");
                        input = in.readLine();
                        //System.out.println(input);
                        
                        if (input.equalsIgnoreCase("HUMAN-AI")) {
                        	human_ai = true;
                            newin = in;
                        }
                        else if ((input.substring(0,5)).equalsIgnoreCase("AI-AI"))
                        {
                                remote_hostname = input.substring(input.indexOf("<")+1,input.indexOf(">"));
                                String afterhost = input.substring(input.indexOf(">"));

                                String portstring = afterhost.substring(afterhost.indexOf("<")+1,afterhost.indexOf(">"));
                                remote_port = Integer.parseInt(portstring);

                                Socket opponent = new Socket(remote_hostname, remote_port);
                                
                                // Need to add options for AI difficulty settings

                                newin = new BufferedReader(new InputStreamReader(opponent.getInputStream()));
                                
                                local_ai_diff = "hard";
                                
                                return newin;
                        }
                        else{
                            out.println("INVALID CHOICE");
                            newin = beginning_sequence(in, out);
                            return newin;
                        }
                        
                        out.println("CHOOSE GAME DIFFICULTY (EASY, MEDIUM, HARD)");
                        input = in.readLine();
                        //System.out.println(input);
                        
                        if (input.equalsIgnoreCase("EASY")||input.equalsIgnoreCase("MEDIUM")||input.equalsIgnoreCase("HARD"))
                            local_ai_diff = input;
                       
                        else{
                            out.println("INVALID CHOICE");
                            newin = beginning_sequence(in, out);
                            return newin;
                        }
                        
                      valid = true;
                        
		} while (!valid);
                
                return newin;
      
  }
 
    private BufferedReader gui_handler(BufferedReader in) throws IOException
    {
        String input;
        input = in.readLine();
        
        if (input.equalsIgnoreCase("w")) {
            client_color = 'w';
            server_color = 'b';
        } 
        else if (input.equalsIgnoreCase("b")) {
                client_color = 'b';
                server_color = 'w';	}
        
        input = in.readLine();
        local_ai_diff = input.substring(3);
        
        input = in.readLine();
        
        if (input.equalsIgnoreCase("HUMAN-AI"))
            human_ai = true;
        else {
        		human_ai = false;
        		input = in.readLine();
                remote_hostname = input;
                input = in.readLine();
                remote_port = Integer.parseInt(input);
                input = in.readLine();
                remote_ai_diff = input;
        }
        return in;
        
    }
    
    private void kill_server() {
    	System.exit(0);
    }
  }  
    
