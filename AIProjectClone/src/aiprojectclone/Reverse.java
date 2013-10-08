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
    private int serv_cli; //1 if server, 2 if client
    private String NULL;
    
            
public Reverse(String i, int p, int a, int s)
{
    ip = i;
    port = p;
    ai_flag = a;
    serv_cli = s;
    System.out.println("Reverse Class Constructed!\n");
}
    
    
    
    
    
    
  //----------------------------------------------- Reverse Functions Below  
    
   public void controller()
   {
       
       if(ip == null) //Class was never setup from either controller_setup or constructor
       {
        System.out.println("You must first setup the reverse game");
        return; //ends controller before starting everything
       }
       
       
       
       
       
       
       
   }//end of controller
   
   public void controller_setup(String i, int p, int a, int s) //If already running instance ie. client needs data
   {
    ip = i;
    port = p;
    ai_flag = a;
    serv_cli = s;      
   }//end of controller_setup
   
   
   
   
               
 //-------------------------------------------------Sub Classes below           
            
    public class GameBoard{
        
        
        
        
        
       public class AI{
           
           
           
           
       } 
        
        
    }
    
    public class Server{            //Server will handel all connections 
        
        
        
        
    }
    
    
    
   
    
    
    
}
