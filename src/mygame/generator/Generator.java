/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame.generator;

import mygame.path.IPathPoint;
import mygame.path.Cell;
import mygame.path.Tree;
import mygame.path.CellFactory;
import mygame.path.PathPointEnd;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mygame.enemy.Enemy;

/**
 *
 * @author anton
 */
public class Generator {
    private Node player;
    private final List<CellFactory> cells;
    private List<Tree<Cell>> all_cells;
    private List<Tree<Cell>> end_cells;
    public Tree<Cell> current;
    public Random rnd;
    public Node node;
    
    private GeneratorId gci;
    private static final String TAG = "CellFactory: ";
    
    public Generator(Node player, List<CellFactory> cells){
        this.player = player;
        this.cells = cells;
        current = new Tree<>(null, cells.get(0).createCell(new Node()));
        rnd = new Random();
        node = new Node();
        gci = new GeneratorId();
        LineTypeChance ltc1 = new LineTypeChance();
        //ltc1.add(new TypeChance(0, 10));
        ltc1.add(new TypeChance(1, 1));
        ltc1.add(new TypeChance(2, 10));
        LineTypeChance ltc2 = new LineTypeChance();
        ltc2.add(new TypeChance(2, 10));
        LineTypeChance ltc3 = new LineTypeChance();
        ltc3.add(new TypeChance(0, 10));
        ltc1.create();
        ltc2.create();
        ltc3.create();
        gci.add(ltc1);
        gci.add(ltc2);
        gci.add(ltc3);
        
    }
    
    public void update(){
        
        node.detachAllChildren();
        end_cells = new ArrayList<>();
        all_cells = current.getAllTree();
        for(Tree<Cell> cell: all_cells){
            if(cell.isEnd())end_cells.add(cell);
        }
        
        if(current.getlength() < 3){
            //System.out.println("length = " + end_cells.size());
            for(Tree<Cell> cell: end_cells){
                //System.out.println("length = " + cell.getContent().getEnds().size());
                for(Tree<IPathPoint> ppe :cell.getContent().getEnds()){
                    //System.out.println(TAG + this + " " + "Create New Cell");
                    PathPointEnd ppe2 = (PathPointEnd)ppe.getContent();
                    gci.set(cell.getContent().type);
                    Cell new_cell = cells.get(gci.get()).createCell(ppe2.getEndPosition());
                    Tree<Cell> new_tree = new Tree<>(cell, new_cell);//TODO: Strange
                    ppe.setChildren(new_cell.path.getRoot().getChildren());
                }
            }
        }
        
        for(Tree<Cell> cell: all_cells){
            node.attachChild(cell.getContent().getCell());
            if(cell.getContent().isCurrent(player.getLocalTranslation())){
                if(cell.getParent()!= null){
                    current = cell.getParent();
                    
                    
                    if(current.getParent() != null){
                        Tree<Cell> pc = current.getParent();
                        if(pc.getChildren() != null){
                            for(Tree<Cell> c: pc.getChildren()){
                                if(c != current){
                                    for(Enemy e: c.getContent().enemes){
                                        e.isAlive = false;
                                    }
                                }
                            }
                        }
                        if(pc.getParent() != null){
                            if(pc.getParent().getContent() != null){
                                for(Enemy e: pc.getParent().getContent().enemes){
                                        e.isAlive = false;
                                }
                            }
                        }
                        if(pc.getParent() != null)pc.getParent().remove(pc);
                    }
                    
                    
                    //current.removeFromParent();
                }
            }
        }
        //System.out.println("==================================================================================");
    }
    
    public Node getWorld(){
        return node;
    }
    
    public Tree<IPathPoint> getPath(){
        return current.getContent().path.getRoot();
    }
}
