/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.math.Transform;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import mygame.activezone.ActiveZone;
import mygame.activezone.Sector;

/**
 *
 * @author anton
 */
public abstract class EnemyFactory {
    
    public List<ActiveZone> active_zone;
    public static List<EnemyFactory> enemyFactores = new ArrayList<>();
    public Node enemy_node;
    
    public EnemyFactory(Node enemy){
        enemyFactores.add(this);
        active_zone = new ArrayList<>();
        this.enemy_node = enemy;
    }
    
    public void addActiveZone(ActiveZone az){
        active_zone.add(az);
    }
    
    public Enemy createEnemy(Transform t){
        final EnemyFactory ef = this;
        Enemy enemy = new Enemy(t, enemy_node) {
            @Override
            public void update(float tpf) {
                ef.update(tpf, this);
            }
        };
        for(ActiveZone az: active_zone){
            enemy.addActivZone(az.copy());
        }
        return enemy;
    }
    
    public abstract void update(float tpf, Enemy e);
}
