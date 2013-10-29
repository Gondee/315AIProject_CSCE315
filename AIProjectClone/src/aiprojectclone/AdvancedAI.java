/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author joshkruger
 * 
 * Advanced aI is the launching class for using the MinMax tree AI
 * It handels all functionality of the AI, makes the AI move, and returns the AI move
 * so that it can be sent over the server
 * 
 *      HOW TO INTERFACE WITH GAMEBOARD
 *      FUNCTIONS IN GAMEBOARD TAKE [A-F][1-8] (in index)
 *      IF YOU HAVE MOVE F3 CALL move_to_index() to change
 *      it into a form that every other function accepts!
 * 
 * 
 */
public class AdvancedAI {
    
    protected String difficulty;
    private GameBoard game;
    
    public AdvancedAI(String d){
        difficulty = d;
    }
    
    public static Object deepClone(Object object) { //Deep copy of board to avoid reference errors
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
    
    public String ai_move(GameBoard board){     //AI move interface, also builds MinMax Tree
        TreeNode root = new TreeNode();
        List<TreeNode> children = new ArrayList<TreeNode>();
        
        int minmax_weight = -100;
        int children_weight = 0;
        int height = 0;
        
        List<String> indexes = new ArrayList<String>();
        String index = "";
        String move = "";
        Random generator = new Random();
        
        if(difficulty.equalsIgnoreCase("hard"))
        	height = 6;   
        else if(difficulty.equalsIgnoreCase("medium"))
            height = 4;
        else if(difficulty.equalsIgnoreCase("easy"))
            height = 2;
        
        simulate_moves(board, root, height);
        if(root.is_leaf())
        	return "";
        
        children = root.get_Children();
        
        for(int i = 0; i < children.size(); i++) {
        	children_weight = minmax(children.get(i), height);
        	if(minmax_weight < children_weight) {
        		minmax_weight = children_weight;
        		indexes.clear();
        		indexes.add(children.get(i).get_Index());
        	}        		
        	if(minmax_weight == children_weight) 
        		indexes.add(children.get(i).get_Index());
        }
      
        index = indexes.get(generator.nextInt(indexes.size()));
        move = board.index_to_move(index);  
        board.ai_move(move);
        return move;
    }
    
    public int index_weight(String index){      //Assignes the worth of places on the board for evaluating by AI
    	int weight = 0;
    	char col = index.charAt(0);
    	char row = index.charAt(1);
    	
    	if(((col == '0' || col == '7') && (row == '0' || row == '7'))) // Corner weight
    		weight = 10;    	
    	else if(((col == '0' || col == '7') && (row == '1' || row == '6'))) // Moves that give up corner
    		weight = -5;
    	else if(((col == '1' || col == '6') && (row == '0' || row == '1' || row == '6' || row == '7')))
    		weight = -5;
    	else if(((col == '0' || col == '7') && (row == '2' || row == '3' || row == '4' || row == '5'))) // Edge weight
    		weight = 5;
    	else if(((row == '0' || row == '7') && (col == '2' || col == '3' || col == '4' || col == '5'))) 
    		weight = 5;
    	else if(((col == '1' || col == '6') && (row == '2' || row == '3' || row == '4' || row == '5'))) // Moves that give up edges
    		weight = -3;  	
    	else if(((row == '1' || row == '6') && (col == '2' || col == '3' || col == '4' || col == '5'))) 
    		weight = -3;
    	else 
    		weight = 1;
    	
    	return weight;
    }
    
    public void simulate_moves(GameBoard board, TreeNode node, int height)  //Simulates board moves and srotes them in tree
    {
    	List<String> indexes = new ArrayList<String>();
    	List<String> moves = new ArrayList<String>();
		List<Integer> weight = new ArrayList<Integer>();
		List<TreeNode> children = new ArrayList<TreeNode>();    		
		List<GameBoard> temp_boards = new ArrayList<GameBoard>();	
		
    	if(board.check_state() || height == 0)
    		return; 
    	
 		if(height % 2 == 0) // Even height represents AI moves
 			indexes = board.get_avaliable_AIindexs();
 		else  // Odd height represents human moves
 			indexes = board.get_avaliable_indexs();
 		
 		if(indexes.size() == 0)
 			return;
 		
		for(int i = 0; i < indexes.size(); i++) {
			weight.add(index_weight(indexes.get(i)));
			moves.add(board.index_to_move(indexes.get(i)));	
		}
		
		node.add_Children(indexes, weight);
		children = node.get_Children();
		

		for(int i = 0; i < children.size(); i++) {
			int temp_height = height;
			temp_height--;
			
			temp_boards.add((GameBoard)deepClone(board));    			
		
			if(height % 2 == 0) 
				temp_boards.get(i).ai_move(moves.get(i)); 
			else 
				temp_boards.get(i).move(moves.get(i)); 
			
			simulate_moves(temp_boards.get(i), children.get(i), temp_height);
		}
    }
    
    public int minmax(TreeNode node, int height) {          //Peforms minMax algorthem on tree
    	List<TreeNode> children = new ArrayList<TreeNode>();
    	int minmax_weight = -100;
    	int children_weight = 0;
    	
    	if(node.is_leaf()) {
    		if(height % 2 == 0)
    			return node.get_Weight();
    		else 
    			return -1*node.get_Weight();
    	}
    	
    	else {
    		int temp_height = height - 1;
    		children = node.get_Children();
    		
    		for(int i = 0; i < children.size(); i++) {
    			children_weight = minmax(children.get(i), temp_height);
    			if(children_weight > minmax_weight)
    				minmax_weight = children_weight;
    		}
    		
    		if(height % 2 == 0)
    			return minmax_weight + node.get_Weight();
    		else
    			return minmax_weight - node.get_Weight();
    	}
    }
}


