/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.path;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author anton
 */
public class ControlPath extends Node{
    Tree<IPathPoint> next;
    Node controled;
    Node control_prev;
    
    public ControlPath(Node controled, Tree<IPathPoint> path){
        this.next = path;
        this.controled = controled;
        this.control_prev = new Node();
        forward_prev = new Vector3f(control_prev.getLocalTranslation())
                .add(new Vector3f(next.getContent().getVector()).negate());
        l_prev = forward_prev.length();
        forward_prev.negateLocal().normalizeLocal();
        
        forward = new Vector3f(controled.getLocalTranslation())
                .add(new Vector3f(control_prev.getLocalTranslation()).negate());
        l = forward.length() - 9;
        forward.negateLocal().normalizeLocal();
    }
    
    public Vector3f forward;
    public float l = 0;
    public Vector3f forward_prev;
    public float l_prev = 0;
    
    public void update(float tpf, float shift){
        if(l_prev <= 0){
            next = next.getChildren().get(getID(next.getChildren().size(), shift));
        }
        //System.out.println("prev = " + control_prev.getLocalTranslation() + 
        //" control = " + controled.getLocalTranslation() + " tpf = " + tpf);
        forward_prev = new Vector3f(control_prev.getLocalTranslation())
                .add(new Vector3f(next.getContent().getVector()).negate());
        l_prev = forward_prev.length() - tpf;
        forward_prev.negateLocal().normalizeLocal();
        if(l<l_prev)control_prev.move(forward_prev.mult(tpf - l));
        else{System.err.println("Yes"); control_prev.move(forward_prev.mult(tpf + l));}
        
        forward = new Vector3f(controled.getLocalTranslation())
                .add(new Vector3f(control_prev.getLocalTranslation()).negate());
        l = forward.length() - 9;
        //System.out.println(l + " lprev = " + l_prev);
        forward.negateLocal().normalizeLocal();
        controled.move(forward.mult(tpf));
        
        for(Tree<IPathPoint> p: next.getAllTree()){
            p.getContent().shiftX((shift - 0.5f) * 3);
        }
        
        Quaternion q = new Quaternion();
        q.lookAt(forward, Vector3f.UNIT_Y);
        controled.setLocalRotation(q);
    }
    
    public Vector3f getForward(){
        return forward;
    }
    
    public Vector3f getForwardPrev(){
        return forward_prev;
    }
    
    public Vector3f getCurrent(){
        return next.getContent().getVector();
    }
    
    public Vector3f getNext(float shift){
        if(next.getChildren().isEmpty()) return Vector3f.ZERO;
        return next.getChildren().get(getID(next.getChildren().size(), shift)).getContent().getVector();
    }
    
    private int getID(int count, float shift){
        return (int)(count * (shift+0.0000001f));
    }
}
