/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author joshkruger
 */
public class GameTree {
    
    char player_color; //'w' or 'b'
    GameBoard game;
    int depth;
    Node start_node;
    
    public GameTree(GameBoard g, int d, char color){
        game = g;
        depth = d;
        start_node = new Node(); //Parent Node
        player_color = color;
    }
    
    
    public boolean build_tree(){
        
        char op_color = ' ';
        
        if(player_color == 'w')
            op_color = 'b';
        else if(player_color == 'b')
            op_color = 'w';
        
        
        GameBoard newb = (GameBoard)deepClone(game);
        start_node.set_board(newb);
        newb.set_color(player_color);
        
        add_depth(start_node, player_color);
        
        
        
        
        
        
        
        return false;
    }
    
    
    private void add_depth(Node n, char c){     //So far copying to depth one
        
        GameBoard  g = new GameBoard(n.get_gameboard());
        g.set_color(c);
        ArrayList<String> moves = g.get_avaliable_indexs();
        
        if(moves.isEmpty())
            return;
        
        for(int i=0; i<moves.size();i++){
            

            GameBoard temp =(GameBoard)deepClone(g);
            temp.set_color(c);
            System.out.println("Potential Move: "+ g.index_to_move(moves.get(i)));
            String m = g.index_to_move(moves.get(i));
            temp.move(m);
            temp.display_board();
            Node newNode = new Node(n,temp);
            n.add_children(newNode);
        }
        
       
    }
    
    public static Object deepClone(Object object) {
   try {
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     ObjectOutputStream oos = new ObjectOutputStream(baos);
     oos.writeObject(object);
     ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
     ObjectInputStream ois = new ObjectInputStream(bais);
     return ois.readObject();
   }
   catch (Exception e) {
     e.printStackTrace();
     return null;
   }
 }
    
    
    
    
    
    
}
