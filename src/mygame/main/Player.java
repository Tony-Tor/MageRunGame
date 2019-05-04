/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.main;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;

/**
 *
 * @author anton
 */
public class Player extends Node{
    public Node w_pos;
    public Node w_rot;
    public Node cam;
    public Node player;
    
    public Player(Node player, CameraNode cam){
        
        this.w_pos = new Node();
        this.w_rot = new Node();
        this.cam = new Node();
        this.player = player;
        
        this.attachChild(w_pos);
        this.attachChild(this.cam);
        
        this.cam.attachChild(cam);
        this.w_pos.attachChild(this.w_rot);
        
        this.w_rot.attachChild(this.player);
    }
    
    public void update(float tpf, Quaternion q1, Quaternion q2){
        System.out.println(tpf);
        Quaternion res = q1.slerp(q1, q2, tpf);
        cam.setLocalRotation(res);
        cam.setLocalTranslation(player.getWorldTranslation());
    }
    
    public void shiftX(float shift){
        //System.out.println(TAG + this + " " + "Shift X");
        player.setLocalTranslation(new Vector3f(shift, 0, 0));
    }
    
}
