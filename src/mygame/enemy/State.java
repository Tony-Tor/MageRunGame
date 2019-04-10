/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class State {
    public List<Float> floats;
    public List<Vector3f> vectors;
    public List<Quaternion> quaternions;
    public List<Transform> transforms;
    
    public State(){
        this.floats = new ArrayList<>();
        this.quaternions = new ArrayList<>();
        this.transforms = new ArrayList<>();
        this.vectors = new ArrayList<>();
    }
}
