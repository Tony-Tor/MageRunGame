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
public interface IPathPoint {
    public Vector3f getVector();
    public Quaternion getQuaternion();
    public void shiftX(float shift);
    public void shiftY(float shift);
    public IPathPoint clone(Node pos);
}
