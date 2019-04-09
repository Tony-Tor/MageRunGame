/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.math.Transform;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import mygame.activezone.ActiveZone;

/**
 *
 * @author anton
 */
public abstract class Enemy {
    public boolean isAlive;
    public List<ActiveZone> azs;
    public int state;
    
    public Node w_pos;
    public Node l_pos;
    
    public static List<Enemy> enemes = new ArrayList<>();
    
    public Enemy(Transform t){
        this.w_pos = new Node();
        this.l_pos = new Node();
        w_pos.attachChild(l_pos);
        w_pos.setLocalTransform(t);
        this.isAlive = true;
        this.azs = new ArrayList<>();
        this.state = 0;
        enemes.add(this);
    }
    
    public void delete(){
        enemes.remove(this);
    }
    
    public void addActivZone(ActiveZone az){
        azs.add(az);
    }
    
    public static void removeNotAlive(){
        List<Enemy> for_remove = new ArrayList<>();
        for(Enemy e: enemes){
            if(!e.isAlive) for_remove.add(e);
        }
        
        for(Enemy e: for_remove){
            enemes.remove(e);
        }
    }
    
    public void updateActiveZone(){
        for(ActiveZone az: azs){
            az.setPosition(l_pos.getWorldTransform());
        }
    }
    
    public abstract void update(float tpf);
}
