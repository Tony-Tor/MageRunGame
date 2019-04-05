/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.math.Transform;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.List;
import mygame.enemy.PointEnemy;

/**
 *
 * @author anton
 */
public class CellFactory {
    private final Node cell;
    private final TreePath tree_path;
    private List<PointEnemy> points;
    private static final String TAG = "CellFactory: ";
    
    public int type;
    
    public CellFactory(Node cell, int type){ //TODO: remove type
        this.type = type;
        //System.out.println(TAG + this + " " + "Create");
        this.cell = new Node();
        List<WrapPathPointer> list_pp = new ArrayList<>();
        List<WrapEnd> list_end = new ArrayList<>();
        
        Node scene = (Node) cell.getChild("Scene");
        
        //Iteration scene spatial
        for(Spatial spatial: scene.getChildren()){
            
            //if spatial is path point - create path point and add to list
            if(spatial.getName().startsWith("PP")){
                
                String[] data_object = spatial.getName().split("/");
                //System.out.println(spatial.getLocalTransform());
                IPathPoint pp = new PathPoint(spatial.getLocalTransform());
                Tree<IPathPoint> tpp = new Tree<>(null, pp);
                WrapPathPointer wpp = new WrapPathPointer(tpp, data_object[1], data_object[2]);
                list_pp.add(wpp);
            }
            
            if(spatial.getName().startsWith("End")){
                
                String[] data_object = spatial.getName().split("/");
                
                WrapEnd we = new WrapEnd((Node)spatial, data_object[1]);
                list_end.add(we);
            }
            
            //if spatial is mesh 
            if(spatial.getName().startsWith("Mesh")){
                this.cell.attachChild(spatial);
            }
        }
        
        Tree<IPathPoint> path_root = null;
        
        for(WrapPathPointer wpp: list_pp){
            if(wpp.connect.equals("0") && wpp.id.equals("0")){ path_root = wpp.point; break;}
        }
        
        this.tree_path = new TreePath(path_root);
        
        for(int k = 0; k < list_pp.size(); k++){
            for(int j = 0; j < list_pp.size(); j++){
                if(j != k){
                    if(list_pp.get(k).connect.equals(list_pp.get(j).id)){
                        list_pp.get(j).point.getChildren().add(list_pp.get(k).point);
                        break;
                    }
                }
            }
        }
        
        for(WrapEnd we: list_end){
            for(WrapPathPointer wpp: list_pp){
                if(we.connect.equals(wpp.id)){
                    wpp.point.setContent(new PathPointEnd((PathPoint)wpp.point.getContent(), we.point));
                }
            }
        }
    }
    
    public Cell createCell(Node pos){
        //System.out.println(TAG + this + " " + "Create New Object Cell");
        Node new_cell = cell.clone(true);
        new_cell.setLocalTransform(pos.getLocalTransform());
        TreePath new_tp = tree_path.clone(pos);
        
        return new Cell(new_cell, new_tp, null, this.type);
    }
    
    private class WrapPathPointer{
        public Tree<IPathPoint> point;
        public String id;
        public String connect;
        
        public WrapPathPointer(Tree<IPathPoint>  point, String id, String connect){
            this.point = point;
            this.id = id;
            this.connect = connect;
        }
    }
    
    private class WrapEnd{
        public Node point;
        public String connect;
        
        public WrapEnd(Node point, String connect){
            this.point = point;
            this.connect = connect;
        }
    }
}
