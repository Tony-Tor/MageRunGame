/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.enemy;

import com.jme3.math.Transform;
import java.util.ArrayList;
import java.util.List;
import mygame.activezone.ActiveZone;
import mygame.activezone.Sector;

/**
 *
 * @author anton
 */
public abstract class EnemyFactory {
    
    public List<Sector> sectors;
    public static List<EnemyFactory> enemyFactores = new ArrayList<>();
    
    public EnemyFactory(){
        enemyFactores.add(this);
        sectors = new ArrayList<>();
        sectors.add(new Sector(0, 0, 0.785f));
        sectors.add(new Sector(1, 0.785f, 5.495f));
        sectors.add(new Sector(0, 5.495f, Sector.PI_2));
    }
    
    public Enemy createEnemy(Transform t){
        ActiveZone az = new ActiveZone(t, 3);
        az.sectors = sectors;
        final EnemyFactory ef = this;
        Enemy enemy = new Enemy(t) {
            @Override
            public void update(float tpf) {
                ef.update(tpf, this);
            }
        };
        enemy.addActivZone(az);
        return enemy;
    }
    
    public abstract void update(float tpf, Enemy e);
}
