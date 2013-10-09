/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 * 
 * Classes are Uppercase for each word in title
 * functions are lowercase if get_ or set_ or starts with name
 * 
 * 
 */
public class Reversi {
    
    private String ip;
    private int port;
    private boolean ai_flag; //activate this copy of AI?
    private int server_flag; //1 if server, 2 if client
    private int remote_ai_diff; //Difficulty of AI of remote AI
    private int local_ai_diff; //Difficulty of AI of local AI
    
    public char client_color = 'w';
    public char server_color = 'b';
    
    public GameBoard game; 
    public Server server;
    
    
            
public Reversi(String i, int p, boolean a, int s, int d) //Human Vs AI
{
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;
    remote_ai_diff = d; //Difficulty of AI on server
    System.out.println("Reverse Class Constructed with Human vs AI!\n");
    
}
public Reversi(String i, int p, boolean a, int s, int d, int dd)
{
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;
    remote_ai_diff = d;
    local_ai_diff = dd;//Difficulty of local AI
    System.out.println("Reverse Class Constructed with AI vs AI!\n");
    
}

public Reversi(){

} //Default if intended to use controller_setup()
    
    
    
    
    
    
  //----------------------------------------------- Reversi Functions Below  
    
   public void controller()
   {
       
       if(ip == null) //Class was never setup from either controller_setup or constructor
       {
        System.out.println("You must first setup the reverse game");
        return; //ends controller before starting everything
       }
       
       System.out.println("Being Controller...");
       
        game = new GameBoard(); 
        server = new Server();
       
       
       
       
   }//end of controller
   
   public void controller_setup(String i, int p, boolean a, int s) //If already running instance ie. client needs data
   {
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;      
   }//end of controller_setup

   public void set_localAI_Diff(int d)
   {
     local_ai_diff = d;    
   }
   boolean get_ai_flag()
   {
     boolean f = ai_flag;
     return f;
   }
   int get_server_flag()
   {
       int s = server_flag;
       return s;    
   }
   int get_port()
   {
     int p = port;
     return p;
   }
   String get_ip()
   {
       String i = ip;
       return i;   
   }
   int get_r_difficulty()
   {
     int d = remote_ai_diff;
     return d;
   }
   int get_l_difficulty()
   {
     int d = local_ai_diff;
     return d;
   }
           
           
   
   
   
}
