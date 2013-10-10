/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.util.Random;

/**
 *
 * @author joshkruger
 */
public class AI extends GameBoard{
    
    int difficulty_flag; 
    
    public AI(int flag)
    {
        difficulty_flag = flag;    
    }
    
    public boolean ramdom_ai()
    {
        char c = super.color; //Getting color for this instance
        Random generator = new Random();
        int testRan = generator.nextInt(8); //In the future it will select from avaliable index positions
        
        
        
       
        return false;
    }
    
    
    
    
}
