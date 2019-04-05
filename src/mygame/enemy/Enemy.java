/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import mygame.activezone.ActiveZone;

/**
 *
 * @author anton
 */
public class Enemy {
    public boolean isAlive;
    public ActiveZone az;
    //public Node node;
    
    public static List<Enemy> enemes = new ArrayList<>();
    
    public Enemy(ActiveZone az/*, Node node*/){
        this.isAlive = true;
        this.az = az;
        enemes.add(this);
    }
    
    public void delete(){
        enemes.remove(this);
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
}
