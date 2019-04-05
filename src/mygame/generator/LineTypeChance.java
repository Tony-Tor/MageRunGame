/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.generator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anton
 */
public class LineTypeChance {
    public int count;
    public List<TypeChance> current_possible_type;
    
    public LineTypeChance(){
        this.current_possible_type = new ArrayList<>();
        this.count = 0;
    }
    
    public void add(TypeChance tc){
        this.current_possible_type.add(tc);
    }
    
    public void create(){
        this.count = 0;
        for(TypeChance tc: this.current_possible_type){
            count = count + tc.priority;
        }
    }
}
