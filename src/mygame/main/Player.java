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
public class Player {
    public Node w_pos;
    public Node w_rot;
    public Node cam;
    public Node player;
    
    public Player(Node player, CameraNode cam){
        this.w_pos = new Node();
        this.w_rot = new Node();
        this.cam = new Node();
        this.cam.attachChild(cam);
        this.w_pos.attachChild(this.w_rot);
        this.w_pos.attachChild(this.cam);
        this.player = player;
        this.w_rot.attachChild(this.player);
    }
    
    public void update(float tpf){
        Quaternion q1 = cam.getLocalRotation();
        Quaternion q2 = w_rot.getLocalRotation();
        Quaternion res = q1.slerp(q1, q2, tpf/2);
        cam.setLocalRotation(res);
    }
    
    public void shiftX(float shift){
        //System.out.println(TAG + this + " " + "Shift X");
        player.setLocalTranslation(new Vector3f(shift, 0, 0));
    }
    
}
