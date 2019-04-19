/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.main;

import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author anton
 */
public class Player {
    public Node w_pos;
    public Node player;
    
    public Player(Node player){
        this.w_pos = new Node();
        this.player = player;
        w_pos.attachChild(this.player);
    }
    
    public void shiftX(float shift){
        //System.out.println(TAG + this + " " + "Shift X");
        player.setLocalTranslation(new Vector3f(shift, 0, 0));
    }
    
}
