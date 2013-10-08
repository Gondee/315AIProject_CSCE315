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
public class Reverse {
    
    private String ip;
    private int port;
    private int ai_flag; //1 if Human vs AI or 2 if AI vs AI
    private int server_flag; //1 if server, 2 if client
    private int ai_difficulty; //Difficulty of AI
    
    public GameBoard game; 
    public Server server;
    
    
            
public Reverse(String i, int p, int a, int s)
{
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;
    System.out.println("Reverse Class Constructed without AI!\n");
    
}
public Reverse(String i, int p, int a, int s, int d)
{
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;
    ai_difficulty = d;
    System.out.println("Reverse Class Constructed with AI!\n");
    
}

public Reverse(){

} //Default if intended to use controller_setup()
    
    
    
    
    
    
  //----------------------------------------------- Reverse Functions Below  
    
   public void controller()
   {
       
       if(ip == null) //Class was never setup from either controller_setup or constructor
       {
        System.out.println("You must first setup the reverse game");
        return; //ends controller before starting everything
       }
       
       System.out.println("Being Controller...");
       
       
       
       
       
   }//end of controller
   
   public void controller_setup(String i, int p, int a, int s) //If already running instance ie. client needs data
   {
    ip = i;
    port = p;
    ai_flag = a;
    server_flag = s;      
   }//end of controller_setup

   
   
   
   
   
   int get_ai_flag()
   {
     int f = ai_flag;
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
           
   
   
   
}
