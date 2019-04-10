/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.generator;

import com.jme3.math.Transform;
import mygame.enemy.Enemy;
import mygame.enemy.EnemyFactory;

/**
 *
 * @author anton
 */
public class GeneratorEnemy {
    
    private GeneratorId generator;
    public static GeneratorEnemy generator_enemy = new GeneratorEnemy();
    
    public GeneratorEnemy(){
        this.generator = new GeneratorId();
        LineTypeChance ltc = new LineTypeChance();
        ltc.add(new TypeChance(0, 10));
        ltc.add(new TypeChance(1, 10));
        ltc.create();
        generator.add(ltc);
    }
    
    public Enemy get(int type, Transform t){
        generator.set(type);
        int i = generator.get();
        return EnemyFactory.enemyFactores.get(i).createEnemy(t);
    }
    
}
