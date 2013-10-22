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
import java.util.Collections;

/**
 *
 * @author joshkruger
 */
public class GameTree {
    
    char player_color; //'w' or 'b'
    GameBoard game;
    int depth;
    Node start_node;
    
    static int easydepth = 2;
    static int mediumdepth = 3;
    static int harddepth = 4;
    
    public GameTree(GameBoard g, int d, char color){
        game = g;
        depth = d;
        start_node = new Node(); //Parent Node
        player_color = color;
        
        
    }
   
    public String get_move(){
       
        assign_scores(start_node,1,player_color);
        minimax(start_node,1);
        //System.out.println(start_node.children.get(0).get_score());
        
        
        return " ";
    }
    
    
    
    public int score_board(GameBoard g){
        
        char[][] temp = g.get_board();
        char c = g.get_color();
        
        boolean flip = false;
        if(player_color != c)
            flip = true;
        
        if(c == 'w')
            c = 'O';
        if(c == 'b')
            c = '@';
        
        int[] line1and8 = {10,1,5,5,5,5,1,10};
        int[] line2and7 = {1,1,-1,-1,-1,-1,1,1};
        int[] line3to6 = {5,-1,2,2,2,2,-1,5};
        
        
        int score=0;
        
        for(int x=0; x<8;x++){
            for(int y=0; y<8;y++){
                
                if(x ==0 || x == 7){
                 
                    if(temp[x][y] == c)
                        score += line1and8[y];
                    
                }
                if(x == 1 || x==6){
                    
                    if(temp[x][y] == c)
                        score += line2and7[y];
                    
                }
                if(x>1 && x<6){
                    
                    if(temp[x][y] == c)
                        score += line3to6[y];
                    
                }
                
            }
            
        }
        
        if(flip)
            score = score *-1; //AI board
        
        return score;
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
        
        add_depth(start_node, player_color); //depth of one
        
        if(depth == 2)
            easy_depth();
        else if(depth ==3)
            medium_depth();
        else if(depth == 4)
            hard_depth();
        
        return false;
    }
    
    public int assign_scores(Node n, int d, char maxplayer){
        
         if(n.children_size() ==0){
            //System.out.println("Returning: " + score_board(n.get_gameboard()));
            //n.set_score(score_board(n.get_gameboard()));
            return score_board(n.get_gameboard());
        }
        
        if(d==0)
            return 0;
        if(n.children_size() >0)
        {
            ArrayList<Node> child = n.get_children();
            for(int i =0; i< n.children_size(); i++)
            {
            n.set_score(assign_scores(child.get(i), d++,maxplayer));
            System.out.println("node: " + n.get_score());
            }
            
        }
       
        return 0;
        
        
        
        
    }
    
    public int minimax(Node n, int d){
        
        if(depth ==0)
            return 0;
        
        if(n.children_size() ==0)
        {
            System.out.println("leaf score: "+ n.get_score());
            return n.get_score();
        }
       if(n.children_size() > 0){
        ArrayList<Integer> s = new ArrayList<Integer>();
        ArrayList<Node> t = n.get_children();
        for(int i=0; i<n.children_size();i++){
            
            s.add(minimax(t.get(i),d++));
            
        }
        int largest = Collections.max(s);
        n.set_score(largest);
        //System.out.println("Scores: " + s.get(0));
       }
        
        return 0;
        
    }
    
    private boolean easy_depth(){
        char op_color = ' ';
        if(player_color == 'w')
            op_color = 'b';
        else if(player_color == 'b')
            op_color = 'w';
        
        
        
        ArrayList<Node> chi = start_node.get_children();
        
        
        for(int i =0; i<start_node.children_size();i++){
            
            add_depth(chi.get(i),op_color);
            
        }
        
        return true;
    }
    private boolean medium_depth(){
        
        char op_color = ' ';
        if(player_color == 'w')
            op_color = 'b';
        else if(player_color == 'b')
            op_color = 'w';
        
        
        
        ArrayList<Node> chi = start_node.get_children();
        
        
        for(int i =0; i<start_node.children_size();i++){
            
            add_depth(chi.get(i),op_color); //Adding second level of depth
            
        }
        
        for(int i =0; i<start_node.children_size();i++){
            
            ArrayList<Node> t = chi.get(i).get_children();
            
            for(int x=0; x<t.size();x++){
                
                add_depth(t.get(x),player_color);
            }
            
        }
        
        
        
        return true;
    }
    private boolean hard_depth(){
        
        char op_color = ' ';
        if(player_color == 'w')
            op_color = 'b';
        else if(player_color == 'b')
            op_color = 'w';
        
        
        
        ArrayList<Node> chi = start_node.get_children();
        
        
        for(int i =0; i<start_node.children_size();i++){
            
            add_depth(chi.get(i),op_color); //Adding second level of depth
            
        }
        
        for(int i =0; i<start_node.children_size();i++){
            
            ArrayList<Node> t = chi.get(i).get_children();
            
            for(int x=0; x<t.size();x++){
                
                add_depth(t.get(x),player_color);
            }
            
        }
        
        for(int i =0; i<start_node.children_size();i++){
            
            ArrayList<Node> t = chi.get(i).get_children();
            
            for(int x=0; x<t.size();x++){
                
                ArrayList<Node> d = chi.get(i).get_children().get(x).get_children();
                
                for(int y =0; y < d.size(); y++)
                {
                    add_depth(d.get(y),op_color);
                }
                
            }
            
        }
        
        
        
        return true;
    }
    
    
    private void add_depth(Node n, char c){     //So far copying to depth one
        
        GameBoard  g = new GameBoard(n.get_gameboard());
        g.set_color(c);
        ArrayList<String> moves = g.get_avaliable_indexs();
        
        if(moves.isEmpty())
            return;
        
        for(int i=0; i<moves.size();i++){
            

            GameBoard  temp =(GameBoard)deepClone(g);
            temp.set_color(c);
            //System.out.println("Potential Move: "+ g.index_to_move(moves.get(i)));
            String m = g.index_to_move(moves.get(i));
            if(temp.move(m)){
            //temp.display_board();
            Node newNode = new Node(n,temp);
            n.add_children(newNode);
            }
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
