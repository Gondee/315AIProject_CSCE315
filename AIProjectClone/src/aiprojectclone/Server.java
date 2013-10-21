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
				out.println("OK\n");
			} else if (input.equals("BLACK") || input.equals("black") || input.equals("Black")) {
				valid = true;
				client_color = 'b';
				server_color = 'w';	
				out.println("OK\n");
			} else if (!valid)
				out.println("INVALID CHOICE\n");
		} while (!valid);
		
		AdvancedAI AI = new AdvancedAI("hard");
		board = new GameBoard(client_color);
		board.display_board(socket);
		out.println("MAKE FIRST MOVE\n");
		
		while (true) {
			input = in.readLine();
		    if (input.equals("EXIT\n"))
			    return 0;
			else if(!board.move(input))
				out.println("ILLEGAL\n");
                else if(board.check_state())
                	out.println("GAME OVER\n");
			out.print(AI.ai_move(board));
			if(board.check_state())
				out.println("GAME OVER\n");		 
			out.println("OK");
			board.display_board(socket);	
		}
		               
	}

    
    
}
