/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import mygame.enemy.Enemy;

/**
 *
 * @author anton
 */
public class Cell {
    public int type;
    public Node cell;
    public TreePath path;
    public List<Enemy> enemes;
    private static final String TAG = "Cell: ";
    
    private static final int HALF_WIDTH_CELL = 6;
    
    public Cell(Node cell, TreePath path, List<Enemy> enemes, int type){//TODO: remove type
        this.type = type;
        //System.out.println(TAG + this + " " + "Create");
        this.cell = cell;
        this.path = path;
        this.enemes = enemes;
        //System.out.println(this.path);
    }
    
    public Node getCell(){
        //System.out.println(TAG + this + " " + "Get Cell");
        return cell;
    }
    
    public List<Tree<IPathPoint>> getEnds(){
        //System.out.println(TAG + this + " " + "Get End");
        return path.getEnds();
    }
    
    public boolean isCurrent(Vector3f vec){
        boolean isCurrent = vec.distance(cell.getLocalTranslation()) < HALF_WIDTH_CELL;
        //System.out.println(TAG + this + " " + "Is Current? " + isCurrent);
        return isCurrent;
    }

    @Override
    protected void finalize() throws Throwable {
        //System.out.println(TAG + this + " " + "Final");
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*public void createNext(Node obj, Connection connect){
        if(this.isEnd()){
            Cell new_cell = new Cell(obj, connect.getEndCell());
            connect.setPath(new_cell.getPath());
            this.addNext(new_cell);
        }
    }*/
}
