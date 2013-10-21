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
public class Node {
    
    protected Node parent;
    protected ArrayList<Node> children = new ArrayList();
    protected int min_score;
    protected int max_score;
    protected GameBoard game;
    
    
    public Node(){
        parent = null;
    }    
    public Node(Node sg, GameBoard g){
        parent = sg;
        game = g;
    }  
    public Node get_parent(){
        return parent;
    }
    public ArrayList<Node> get_children(){
        return children;
    }
    public void add_children(Node g){
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
