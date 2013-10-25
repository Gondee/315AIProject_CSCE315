package aiprojectclone;

import java.util.ArrayList;
import java.util.List;



public class TreeNode {
	
    private String index;
    private int weight;
    private TreeNode parent;
    private List<TreeNode> children;
    
    public TreeNode() {
    	index = "root";
    	weight = 0;
        parent = null;
        children = new ArrayList<TreeNode>();
    }
        
    public TreeNode(String m, int w) {
    	index = m;
    	weight = w;
    	children = new ArrayList<TreeNode>();
    }
    
    public void add_Parent(TreeNode p) {
    	parent = p;
    }
    
    public void add_Child(TreeNode c) {
    		c.add_Parent(this);
    		children.add(c);
    }
    
    public boolean add_Children(List<String> m, List<Integer> w) {
    	if(m.size() != w.size())
    		return false;
    	
    	for( int i = 0; i < m.size(); i++) 
    		this.add_Child(new TreeNode(m.get(i), w.get(i)));
    	
    	return true;
    }
    
    public String get_Index() {
    	return index;
    }
    
    public int get_Weight() {
    	return weight;
    }
    
    public List<TreeNode> get_Children() {
    	return children;
    }
    public boolean is_root() {
    	if(parent == null)
    		return true;
    	else
    		return false;
    }
    public boolean is_leaf() {
    	if(children.size() == 0)
    		return true;
    	else
    		return false;
    }
    
    
}


    


