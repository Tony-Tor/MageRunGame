/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.activezone;

import com.jme3.math.Quaternion;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class ActiveZone {
    public Vector3f pos;
    public Vector3f direction;
    public float size;
    public List<Sector> sectors;
    
    private static final String TAG = "ActiveZone: ";
    
    public ActiveZone(Transform t, float size){
        this.pos = t.getTranslation();
        this.direction = new Vector3f(Vector3f.UNIT_Z);
        t.getRotation().toRotationMatrix().mult(this.direction);
        this.sectors = new ArrayList<>();
    }
    
    public void addSector(Sector sector){
        this.sectors.add(sector);
    }
    
    public int isCollision(Vector3f pos, float size){
        if(this.pos.distance(pos) <= this.size + size){
            Vector3f vec = new Vector3f(pos);
            vec.addLocal(this.pos.negate());
            vec.normalizeLocal();
            Vector3f dir = new Vector3f(this.direction).normalizeLocal();
            float angle = vec.angleBetween(dir);
            //System.out.println(TAG + angle + " pos = " + vec + " dir = " + dir);
            for(Sector sector: sectors){
                if(sector.isActive(angle)) return sector.type;
            }
        }
        return -1;
    }
    
    public void move(Vector3f pos){
        this.pos.add(pos);
    }
    
    public void rotate(Quaternion quat){
        quat.toRotationMatrix().mult(this.direction);
    }
    
    public void setPosition(Transform t){
        this.pos = t.getTranslation();
        this.direction = new Vector3f(Vector3f.UNIT_Z);
        t.getRotation().toRotationMatrix().mult(this.direction);
    }
}
