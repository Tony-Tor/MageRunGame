/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.math.Transform;

/**
 *
 * @author anton
 */
public class PointEnemy {
    public Transform transform;
    public int type;
    
    public PointEnemy(Transform transform, int type){
        this.transform = transform;
        this.type = type;
    }
}
