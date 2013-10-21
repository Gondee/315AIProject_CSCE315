/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aiprojectclone;
import java.util.*;
/**
 *
 * @author joshkruger
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
    
    
    
    public String ai_move(GameBoard board){
        //Just an idea, but take in the board, or a copy of the board
        game = board; // copy of gameboard
        TreeNode root = new TreeNode();
        int depth = 0;
        String move = "";
        
        if(difficulty == "hard")
        	depth = 6;   
        else if(difficulty == "medium")
            depth = 4;
        else if(difficulty == "easy")
            depth = 2;
        
        minmax(move, root, board, depth);
        return move;          
    }
    
    public int index_weight(String index){
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
    		weight = -2;  	
    	else if(((row == '1' || row == '6') && (col == '2' || col == '3' || col == '4' || col == '5'))) 
    		weight = -2;
    	else 
    		weight = 1;
    	
    	return weight;
    }
    
    public int minmax(String move, TreeNode node, GameBoard board, int depth) {  
    	
    	if(board.check_state())
    		return 0;
	
		
		List<String> moves = new ArrayList<String>();
		List<Integer> weight = new ArrayList<Integer>();
		List<String> potential_moves = new ArrayList<String>();
        Random generator = new Random();
 		int minmax_weight = 0;
 		int minmax_weight_temp = 0;
 		List<String> indexes = board.get_avaliable_indexs();
 		
 		if(indexes.size() == 0)
 			return node.get_Weight();
		
		for(int j = 0; j < indexes.size(); j++) {
			weight.add(index_weight(indexes.get(j)));
			moves.add(board.index_to_move(indexes.get(j)));
			System.out.print(moves.get(j));		
		}
		
		System.out.print("\n\n");
		
		node.add_Children(indexes, weight);
		List<TreeNode> children = node.get_Children();    		
		List<GameBoard> temp = new ArrayList<GameBoard>();
		
		
		for(int j = 0; j < children.size(); j++) {
			int temp_depth = depth;
			temp.add(new GameBoard(board));    			
		
			if(depth % 2 == 0) 
				temp.get(j).ai_move(moves.get(j)); // AI moves on even depth
			else
				temp.get(j).move(moves.get(j)); // Human moves on odd depth
			
			temp_depth--;
			
			if(node.is_root())    				
				minmax_weight = minmax(move, children.get(j), temp.get(j), temp_depth);
			
			if(depth % 2 == 0) {
				if(temp.get(j).check_state() || temp_depth == 0)
					return children.get(j).get_Weight();
				else 
					minmax_weight_temp = minmax(move, children.get(j), temp.get(j), temp_depth) + weight.get(j);
					if(minmax_weight <= minmax_weight_temp) {
						minmax_weight = minmax_weight_temp;
						if(node.is_root())
							potential_moves.add(children.get(j).get_Move());
					}
			}
			
			if(depth % 2 == 1) {
				if(temp.get(j).check_state() || temp_depth == 0)
					return children.get(j).get_Weight();
				else
					minmax_weight_temp = minmax(move, children.get(j), temp.get(j), temp_depth) - weight.get(j);
					if(minmax_weight <= minmax_weight_temp) 
						minmax_weight = minmax_weight_temp;		
    		}				
    	}
		
		int rand_num;
		if(!node.is_root())
			return minmax_weight;
		else 
		   	rand_num = generator.nextInt(potential_moves.size()); 		
			move = board.index_to_move(potential_moves.get(rand_num));
			board.ai_move(move);
			return 0;		
	}    
}


