/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.util.ArrayList;

/**
 *
 * @author joshkruger
 */
public class SimpleGraph {
    
    protected SimpleGraph parent;
    protected ArrayList<SimpleGraph> children = new ArrayList();
    protected int min_score;
    protected int max_score;
    protected GameBoard game;
    
    
    public SimpleGraph(){
        parent = null;
    }    
    public SimpleGraph(SimpleGraph sg, GameBoard g){
        parent = sg;
        game = g;
    }  
    public SimpleGraph get_parent(){
        return parent;
    }
    public ArrayList<SimpleGraph> get_children(){
        return children;
    }
    public void add_children(SimpleGraph g){
        children.add(g);
    } 
    public void set_board(GameBoard g){
        game = g;
    } 
    public void set_min(int m){
        min_score = m;
    }
    public void set_max(int m){
        max_score = m;
    }
    
    
    
    
}
