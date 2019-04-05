/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.activezone;

/**
 *
 * @author anton
 */
public class Sector {
    public float begin;
    public float end;
    public int type;
    
    public final static float PI_2 = 2 * (float)Math.PI;
    
    public Sector(int type, float begin, float end){
        if(begin >= 0 && begin <= PI_2 
                && end >= begin && end <= PI_2){
            this.begin = begin;
            this.end = end;
        } else {
            this.begin = 0;
            this.end = 0;
        }
        this.type = type;
    }
    
    public boolean isActive(float f){
        return f >= begin && f <= end;
    }
}
