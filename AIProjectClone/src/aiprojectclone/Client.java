/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

/**
 *
 * @author joshkruger
 */
public class Client {
    
    GameBoard clientBoard; //Where ever client ends up, needs a copy of gameboard
                           //I will have the GUI updating copys of this gameboard
    String ip;
    String port;
    String client;
    String server;
    String color;
    
    public Client(String cli,String ser,String ipp, String ppo,String col){
        client = cli;
        server = ser;
        ip = ipp;
        port = ppo;
        color = col;
        
    }
            
    
    
    
}
