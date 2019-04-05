/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class TreePath {
    public Tree<IPathPoint> root;
    private static final String TAG = "TreePath: ";
    
    public TreePath(Tree<IPathPoint> root){
        //System.out.println(TAG + this + " " + "Create");
        this.root = root;
    }
    
    public TreePath clone(Node pos){
        //System.out.println(TAG + this + " " + "Copy");
        Tree<IPathPoint> new_root = root.clone(null);
        //
        for(Tree<IPathPoint> tpp: new_root.getAllTree()){
            //System.out.println(TAG + this + " " + "Flag 1");
            tpp.setContent(tpp.getContent().clone(pos));
        }
        return new TreePath(new_root);
    }
    
    public List<Tree<IPathPoint>> getEnds(){
        //System.out.println(TAG + this + " " + "Get Ends");
        List<Tree<IPathPoint>> ppe = new ArrayList<>();
        for(Tree<IPathPoint> tipp: this.root.getAllTree()){
            if(tipp.isEnd()){
                ppe.add(tipp);
            }
        }
        return ppe;
    }
    
    public void shiftX(float shift){
        //System.out.println(TAG + this + " " + "Shift X");
        for(Tree<IPathPoint> tpp: root.getAllTree()){
            tpp.getContent().shiftX((shift - 0.5f) * 3);
        }
    }
    
    public Tree<IPathPoint> getRoot(){
        //System.out.println(TAG + this + " " + "Get Root");
        return root;
    }
}
