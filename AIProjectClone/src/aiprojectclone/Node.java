/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author joshkruger
 */
public class Node{
    
    protected Node parent;
    protected ArrayList<Node> children = new ArrayList();
    protected int score;
    protected GameBoard game;
    
    
    public Node(){
        parent = null;
    }    
    public Node(Node sg, GameBoard g){
        GameBoard temp = new GameBoard(g);
        parent = sg;
        game = temp;
    }
    public Node(Node sg){
        parent = sg;
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
    public void set_score(int m){
        score = m;
    }
    public GameBoard get_gameboard(){
        return game;
    }
    public int children_size(){
        return children.size();
    }
    public int get_score(){
        return score;
    }
    
    
    
    
}
