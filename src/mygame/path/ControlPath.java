/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import mygame.main.Player;

/**
 *
 * @author anton
 */
public class ControlPath extends Node{
    public Tree<IPathPoint> next;
    public Player player;
    public float length;
    
    public ControlPath(Player player, Tree<IPathPoint> path){
        this.next = path;
        this.player = player;
        this.length = 0;
    }
    
    public void update(float tpf, float shift){
        
        for(Tree<IPathPoint> tpp: next.getAllTree()){
            tpp.getContent().shiftX((shift - 0.5f) * 10);
        }
        player.shiftX((shift - 0.5f) * 10);
        
        Vector3f vec_player_local = player.player.getWorldTranslation();
        Vector3f vec_path_local = next.getContent().getVectorLocal();
        
        float len_local = vec_player_local.distance(vec_path_local);
        
        Vector3f vec_player_world = player.w_pos.getLocalTranslation();
        Vector3f vec_path_world = next.getContent().getVectorWorld();
        
        float len_world = vec_player_world.distance(vec_path_world);
        
        float tpf_r = 0;
        if(len_local != 0){
            tpf_r= (tpf * len_world)/len_local;
            length = length - tpf_r;
            
        }
        else length = 0;
        if(tpf-tpf_r == 0){
            System.out.println("Midle");
        }
        else {
            if(tpf - tpf_r > 0){
                System.out.println("Fast");
            }
            else {
                System.out.println("Slow");
            }
        }
        
        Vector3f direction = vec_path_world.add(vec_player_world.negate());
        direction.normalizeLocal();
        direction.multLocal(tpf_r);
        
        player.w_pos.move(direction);
        System.out.println(tpf + " " + tpf_r + " " + direction.length() + " " + len_local + " " + len_world);
        
        if(length <= 0){
            int children_next_size = next.getChildren().size();
            Tree<IPathPoint> new_next = next.getChildren().get(getID(children_next_size, shift));
            Vector3f new_vec_path_world = new_next.getContent().getVectorWorld();
            float new_length = vec_path_world.distance(new_vec_path_world);
            Quaternion quat = next.getContent().getQuaternion();
            length = new_length - length;
            next = new_next;
            
            /*Vector3f rotate_vec = new_vec_path_world.add(vec_path_world.negate());*/
            quat.negate();
            
            player.w_pos.setLocalRotation(quat);
            player.w_pos.setLocalTranslation(vec_path_world);
            System.err.println(player.w_pos.getLocalTranslation());
        }
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
