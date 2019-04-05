package mygame.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tree<T> {
	private Tree<T> parent;
	private List<Tree<T>> children;
	private T content;
        private static final String TAG = "Tree: ";
	
	public Tree(Tree<T> parent, T content){
            //System.out.println(TAG + this + " " + "Create");
        //System.out.println("L");
		this.parent = parent;
		if(this.parent != null) this.parent.getChildren().add(this);
		this.children = new ArrayList<>();
		this.content = content;
	}

	public Tree<T> getParent() {
            //System.out.println(TAG + this + " " + "Get Parent");
		return parent;
	}

	public void setParent(Tree<T> parent) {
            //System.out.println(TAG + this + " " + "Set Parent");
		this.parent = parent;
	}

	public List<Tree<T>> getChildren() {
            //System.out.println(TAG + this + " " + "Get Children");
		return children;
	}

	public void setChildren(List<Tree<T>> children) {
            //System.out.println(TAG + this + " " + "Set Children");
		this.children = children;
	}

	public T getContent() {
            //System.out.println(TAG + this + " " + "Get Content");
		return content;
	}

	public void setContent(T content) {
            //System.out.println(TAG + this + " " + "Set Content " + content);
		this.content = content;
	}

	public boolean isRoot(){
            //System.out.println(TAG + this + " " + "Is Root? ");
        return parent == null;
    }
    
    public boolean isEnd(){
        //System.out.println(TAG + this + " " + "Is End? ");
        return children.isEmpty();
    }
    
    public boolean isCross(){
        //System.out.println(TAG + this + " " + "Is Cross? ");
        return children.size() > 1;
    }
    
    public int getlength(){
        //System.out.println(TAG + this + " " + "Get length");
        int max = 0;
        for(Tree<T> p: children){
            int k = p.getlength(0, this);
            if(k > max) max = k;
        }
        //System.out.println("Length tree = " + (max + 1));
        return max + 1;
    }
    
    private int getlength(int i, Tree<T> tree){
        if(this != tree){
            int max = 0;
            i++;
            for(Tree<T> p: children){
                int k = p.getlength(i, this);
                if(k > max) max = k;
            }
        
            return i + max;
        }
        return i;
    }
    
    public List<Tree<T>> getAllTree(){
        //System.out.println(TAG + this + " " + "Get All Tree");
    	List<Tree<T>> all = new ArrayList<>();
        all.add(this);
        for(Tree<T> p: children){
            p.getTree(all, this);
            
        }
        return all;
    }
    
    private void getTree(List<Tree<T>> all, Tree<T> objectTree){
        if(this != objectTree){
            all.add(this);
            for(Tree<T> p: children){
                p.getTree(all, objectTree);
                
            }
        }
    }
    
    public void removeFromParent() {
        //System.out.println(TAG + this + " " + "Remove from parent");
		//Objects.requireNonNull(parent);
		if(parent != null)parent.children.remove(this);
		parent = null;
    }
    
    public Tree clone(Tree parent){
        //System.out.println(TAG + this + " " + "Copy");
        Tree new_root = new Tree(parent, content);
        for(Tree child: children){
            child.clone(new_root);
        }
        return new_root;
    }
}
