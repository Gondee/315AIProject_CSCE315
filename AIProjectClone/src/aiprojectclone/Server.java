package aiprojectclone;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
    private GameBoard board;
    
	private int port; // Port number of the server	
	private int remote_port; // Hostname for remote server for AI vs AI
	private String remote_hostname; // Port number of the remote server for AI vs AI
    
    char client_color;
    char server_color;
    
    static private boolean ai_flag; //activate this copy of AI?
    private int remote_ai_diff; //Difficulty of AI of remote AI
    private int local_ai_diff; //Difficulty of AI of local AI
    

    
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
        
		boolean valid = false; 
		do {			
			out.println("CHOOSE SIDE (WHITE OR BLACK)\n");
			input = in.readLine();
			if (input.equals("WHITE") || input.equals("white") || input.equals("White")) {
				valid = true;
				client_color = 'w';
				server_color = 'b';
			} else if(input.equals("BLACK") || input.equals("black") || input.equals("Black")) {
				valid = true;
				client_color = 'b';
				server_color = 'w';				
			} else if(!valid)
				out.println("INVALID CHOICE\n");
		} while (!valid);
			
		board = new GameBoard(client_color);
		
		while (true) {
			input = in.readLine();
			
			if (input.equals("DISPLAY"))
				board.display_board(socket);		   
			   	
		    if (input.equals("EXIT"))
			    return 0;
		    out.println(input+"\n");
		}
		               
	}

    
    
}
