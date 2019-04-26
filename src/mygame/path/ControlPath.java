/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.main.Player;

/**
 *
 * @author anton
 */
public class ControlPath extends Node{
    public Tree<IPathPoint> next;
    public Tree<IPathPoint> prev;
    public Player player;
    
    public ControlPath(Player player, Tree<IPathPoint> path){
        this.next = path;
        PathPoint pp = new PathPoint(new Transform());
        this.prev = new Tree(null, pp);
        this.player = player;
    }
    
    public void update(float tpf, float shift){
        
        for(Tree<IPathPoint> tpp: next.getAllTree()){
            tpp.getContent().shiftX((shift - 0.5f) * 10);
        }
        player.shiftX((shift - 0.5f) * 10);
        prev.getContent().shiftX(shift);
        
        Vector3f vec_player_local = player.player.getWorldTranslation();
        Vector3f vec_path_next_local = next.getContent().getVectorLocal();
        Vector3f vec_path_prev_local = prev.getContent().getVectorLocal();
        
        float len_local = vec_path_prev_local.distance(vec_player_local);
        float len_all = vec_path_prev_local.distance(vec_path_next_local);
        float len = len_all - len_local;
        
        Vector3f direction = vec_path_next_local.add(vec_player_local.negate());
        direction.normalizeLocal();
        direction.multLocal(tpf);
        
        player.w_pos.move(direction);
        
        if(len <= 0){
            Quaternion quat = next.getContent().getQuaternion();
            quat.negate();
            
            int children_next_size = next.getChildren().size();
            Tree<IPathPoint> new_next = next.getChildren().get(getID(children_next_size, shift));
            prev = next;
            next = new_next;
            
            player.w_rot.setLocalRotation(quat);
            player.w_pos.setLocalTranslation(prev.getContent().getVectorWorld());
        }
        player.update(tpf);
    }
    
    public Vector3f getCurrent(){
        return next.getContent().getVectorLocal();
    }
    
    public Vector3f getNext(float shift){
        if(next.getChildren().isEmpty()) return Vector3f.ZERO;
        return next.getChildren().get(getID(next.getChildren().size(), shift)).getContent().getVectorLocal();
    }
    
    private int getID(int count, float shift){
        return (int)(count * (shift+0.0000001f));
    }
}
