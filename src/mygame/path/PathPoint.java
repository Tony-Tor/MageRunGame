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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class PathPoint implements IPathPoint{
    protected Node w_pos;
    private Node l_pos;
    private static final String TAG = "PathPoint: ";
    
    public PathPoint(Transform t){
        //System.out.println(TAG + this + " " + "Create");
        this.w_pos = new Node();
        this.l_pos = new Node();
        w_pos.attachChild(l_pos);
        w_pos.setLocalTransform(t);
        
        
    }
    
    @Override
    public Vector3f getVector(){
        //System.out.println(TAG + this + " " + "Get Vector");
        return l_pos.getWorldTranslation();
    }
    
    @Override
    public Quaternion getQuaternion(){
        //System.out.println(TAG + this + " " + "Get Quaternion");
        return w_pos.getLocalRotation();
    }
    
    @Override
    public void shiftX(float shift){
        //System.out.println(TAG + this + " " + "Shift X");
        l_pos.setLocalTranslation(new Vector3f(shift, 0, 0));
    }
    
    @Override
    public void shiftY(float shift) {
        //System.out.println(TAG + this + " " + "Shift Y");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public IPathPoint clone(Node pos){
        //System.out.println(TAG + this + " " + "Copy");
        return new PathPoint(
                w_pos.getLocalTransform().clone().combineWithParent(pos.getLocalTransform()));
    }

    
}
