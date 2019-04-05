/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author anton
 */
public class GeneratorId {
    public int current_type;
    public List<LineTypeChance> possible_type;
    private Random rnd;
    
    public GeneratorId(){
        this.current_type = 0;
        this.possible_type = new ArrayList<>();
        this.rnd = new Random();
    }
    
    public void add(LineTypeChance ltc){
        this.possible_type.add(ltc);
    }
    
    public void set(int type){
        this.current_type = type;
    }
    
    public int get(){
        int last_priority = 0;
        int current_value;
        LineTypeChance ltc = possible_type.get(current_type);
        int rnd_value = rnd.nextInt(ltc.count);
        
        for(int i = 0; i < ltc.current_possible_type.size(); i++){
            current_value = last_priority + ltc.current_possible_type.get(i).priority;
            if(rnd_value < current_value && rnd_value >= last_priority){
                return ltc.current_possible_type.get(i).type;
            }
            last_priority = current_value;
        }
        return -1;
    }
}
