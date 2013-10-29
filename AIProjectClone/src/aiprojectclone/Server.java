package aiprojectclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;

public class Server {
	
    private GameBoard board;
    private int port; // Port number of the server	
    private int remote_port; // Hostname for remote server for AI vs AI
    private String remote_hostname; // Port number of the remote server for AI vs AI
    
    char client_color;
    char server_color;
    
    static private boolean ai_flag; //activate this copy of AI?
    private String remote_ai_diff; //Difficulty of AI of remote AI
    private String local_ai_diff; //Difficulty of AI of local AI
    

    
    public Server(int p) {
    	port = p;
    }
    
    public void listen() throws IOException {
 	   
 	   ServerSocket listener = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = listener.accept();
                try {
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
    
    public int connection_handler(Socket socket) throws IOException {
 	   
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));      
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		String input;
		
		out.println("WELCOME\n");
                
                
		in = beginning_sequence(in, out);

		AdvancedAI AI = new AdvancedAI(local_ai_diff);

		board = new GameBoard(client_color);
                
		board.display_board(socket);
                
                
		out.println("MAKE FIRST MOVE\n");
                
		
                boolean display = true;
		while (true) {
                    input = in.readLine();
		    if (input.equalsIgnoreCase("EXIT"))
			    return 0;
                    else if (input.equalsIgnoreCase("DISPLAY"))
                        display = !display;
                    else if (input.equalsIgnoreCase("UNDO"))
                        board.undo();
                    else if (input.charAt(0)==';')
                        out.println(input.substring(1));
                    else if(!board.move(input))
			out.println("ILLEGAL\n");
                    else
                        out.println(AI.ai_move(board));
                    if(board.check_state())
                            out.println("GAME OVER\n");		 
                    out.println("OK");
                    if (display)
                        board.display_board(socket);	
		}
		               
	}

 BufferedReader beginning_sequence(BufferedReader in, PrintWriter out) throws IOException{
                String input;
                BufferedReader newin;
      		boolean valid = false; 
		do {	
                        out.println("Press any key and enter");
                        input = in.readLine();
                        
                        if (input.equals("gui"))
                            return gui_handler(in);
                            
                   
                        
			out.println("CHOOSE SIDE (WHITE OR BLACK)\n");
            
			input = in.readLine();
                        boolean found = false;
                        for (int i = 0; i < input.length()&&!found; i = i +1)
                        {
                            if ((int) input.charAt(i) >= 65 &&(int) input.charAt(i) <= 122){
                                input = input.substring(i);
                                found = true;
                            }
                        }
			if (input.equalsIgnoreCase("WHITE")) {
				client_color = 'w';
				server_color = 'b';
				out.println("OK\n");
			} else if (input.equalsIgnoreCase("BLACK")) {
				client_color = 'b';
				server_color = 'w';	
				out.println("OK\n");
			} else{
                            out.println("INVALID CHOICE\n");
                            newin = beginning_sequence(in, out);
                            return newin;
                        }
                        out.println("CHOOSE GAME TYPE (HUMAN-AI OR AI-AI)\n");
                        input = in.readLine();
                        
                        if (input.equalsIgnoreCase("HUMAN-AI"))
                            newin = in;
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
                            out.println("INVALID CHOICE\n");
                            newin = beginning_sequence(in, out);
                            return newin;
                        }
                        
                        out.println("CHOOSE GAME DIFFICULTY (EASY, MEDIUM, HARD)\n");
                        input = in.readLine();
                        
                        if (input.equalsIgnoreCase("EASY")||input.equalsIgnoreCase("MEDIUM")||input.equalsIgnoreCase("HARD"))
                            local_ai_diff = input;
                       
                        else{
                            out.println("INVALID CHOICE\n");
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
        return in;
        
    }
  }  
    
