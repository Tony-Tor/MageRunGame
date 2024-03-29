package mygame.main;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Transform;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import java.util.ArrayList;
import java.util.List;
import mygame.activezone.ActiveZone;
import mygame.activezone.Sector;
import mygame.enemy.Enemy;
import mygame.enemy.EnemyFactory;
import mygame.path.CellFactory;
import mygame.path.ControlPath;
import mygame.generator.Generator;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    
    private Node mage_player;
    public Player player;
    public Generator gen;
    public ControlPath control_path;
    
    private Node test_path;
    private Node test_path1;
    private Node test_path2;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        loadAssets();
        init();
        setLight();
        initKey();
    }
    
    private void loadAssets(){
        test_path = (Node) assetManager.loadModel("Models/TestPath.j3o");
        test_path1 = test_path.clone(true);
        test_path2 = test_path.clone(true);
        test_path4 = test_path.clone(true);
        //mage_player = (Node) assetManager.loadModel("Models/MageAnim.j3o");
        //mage_player = (Node) mage_player.getChild("Mage");
        mage_player = new Node();
        Node test_path3 = test_path.clone(true);
        test_path3.setLocalScale(0.1f);
        mage_player.attachChild(test_path3);
        mage_player.setLocalTranslation(0, 0, 0);
        mage_player.scale(1f);
        /*mage_control = mage_player.getControl(AnimControl.class);
        mage_control.addListener(new AnimEventListener() {
            @Override
            public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
                
            }

            @Override
            public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
                
            }
        });
        mage_channel = mage_control.createChannel();
        mage_channel.setAnim("ArmatureAction");*/
        //rootNode.attachChild(mage_player);
        
        List<CellFactory> cells = new ArrayList<>();
        Node spatial = (Node) assetManager.loadModel("Models/Road.j3o");
        cells.add(new CellFactory(spatial, 0));
        Node spatial1 = (Node) assetManager.loadModel("Models/Road2.j3o");
        cells.add(new CellFactory(spatial1, 1));
        Node spatial2 = (Node) assetManager.loadModel("Models/Road3.j3o");
        cells.add(new CellFactory(spatial2, 2));
        //rootNode.attachChild(spatial);
        Node enemy = (Node) assetManager.loadModel("Models/Enemy.j3o");
        Node arrow = (Node) assetManager.loadModel("Models/Arrow.j3o");
        
        EnemyFactory ef1 = new EnemyFactory(enemy) {
            @Override
            public void update(float tpf, Enemy e) {
                if(e.state.floats.isEmpty())e.state.floats.add(1f);
                int r = e.azs.get(0).isCollision(mage_player.getLocalTranslation(), 2);
                if(r != -1) System.err.println("Type 1");
                if(r == 0) e.isAlive = false;
                if(r == 1) System.err.println("Game Over");
                float x = e.l_pos.getLocalTranslation().x;
                System.err.println(e.l_pos.getLocalTranslation());
                if(x <= 6 && x >= -6)e.l_pos.setLocalTranslation(x + tpf * e.state.floats.get(0) * 10, 0, 0);
                else {
                    if(x >= 6)e.l_pos.setLocalTranslation(6, 0, 0);
                    if(x <= -6)e.l_pos.setLocalTranslation(-6, 0, 0);
                    e.state.floats.set(0, e.state.floats.get(0) * -1);
                }
                e.updateActiveZone();
            }
        };
        ActiveZone az1 = new ActiveZone(Transform.IDENTITY, 2);
        az1.addSector(new Sector(1, 0, 0.7854f));
        az1.addSector(new Sector(0, 0.7854f, 5.4978f));
        az1.addSector(new Sector(1, 5.4978f, Sector.PI_2));
        ef1.addActiveZone(az1);
        
        EnemyFactory ef2 = new EnemyFactory(enemy) {
            @Override
            public void update(float tpf, Enemy e) {
                int r = e.azs.get(0).isCollision(mage_player.getLocalTranslation(), 2);
                if(r != -1) System.err.println("Type 2");
                if(r == 0) e.isAlive = false;
                e.updateActiveZone();
            }
        };
        ActiveZone az2 = new ActiveZone(Transform.IDENTITY, 2);
        az2.addSector(new Sector(0, 0, Sector.PI_2));
        ef2.addActiveZone(az2);
        
        EnemyFactory ef3 = new EnemyFactory(arrow) {
            @Override
            public void update(float tpf, Enemy e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        ActiveZone az3 = new ActiveZone(Transform.IDENTITY, 0.5f);
        az3.addSector(new Sector(1, 0, Sector.PI_2));
        ef3.addActiveZone(az3);
        
        rootNode.attachChild(test_path);
        rootNode.attachChild(test_path1);
        rootNode.attachChild(test_path2);
        rootNode.attachChild(test_path4);
        rootNode.attachChild(Enemy.enemes_node);
        
        
        CameraNode camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, 8));
        camNode.lookAt(new Vector3f(0, 0, -16).add(mage_player.getLocalTranslation()), Vector3f.UNIT_Y);
        
        player = new Player(mage_player, camNode);
        gen = new Generator(player.w_pos, cells);
        rootNode.attachChild(gen.getWorld());
        
        control_path = new ControlPath(player, gen.getPath());
        rootNode.attachChild(player);
    }
    
    private void setLight(){
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(1f, -1f, 1).normalizeLocal());
        rootNode.addLight(dl);
    }
    
    private void init(){
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(100);
        
        //mage_player.attachChild(camNode);
        viewPort.setBackgroundColor(new ColorRGBA(0.8f, 0.8f, 1f, 1));
    }
    
    private void initKey(){
        inputManager.addMapping("LeftStaff", new KeyTrigger(KeyInput.KEY_U));
        inputManager.addMapping("LeftPotion", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("RightStaff", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addMapping("RightPotion", new KeyTrigger(KeyInput.KEY_L));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Mouse_pos-X", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        inputManager.addMapping("Mouse_posX", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        
        inputManager.addListener(actionListener, "LeftStaff", "LeftPotion", "RightStaff", "RightPotion", "Right", "Left");
        inputManager.addListener(analogListener, "Mouse_pos-X", "Mouse_posX");
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if (name.equals("LeftStaff") && isPressed) {
                System.out.println("LeftStaff");
            }
            if (name.equals("LeftPotion") && isPressed) {
                System.out.println("LeftPotion");
            }
            if (name.equals("RightStaff") && isPressed) {
                System.out.println("RightStaff");
            }
            if (name.equals("RightPotion") && isPressed) {
                System.out.println("RightPotion");
            }
        }
    };
    float shift = 0;
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("Mouse_pos-X")) {
                shift -= value;
            }
            if (name.equals("Mouse_posX")) {
                shift += value;
            }
        }
    };
    float max = 0;
    float time = 0;
    float fps = 0;
    float count_collision = 0;
    Node test_path4;
    @Override
    public void simpleUpdate(float tpf) {
        //System.out.println("==================================================================================");
        if(max<1){max = max + tpf; fps++;}
        else {time++; System.err.println("Main: = " + time + " " + fps); max=0; fps=0;}
        
        gen.update();
        
        control_path.update(tpf*10, shift);
        
        //System.out.println(" Forward: = " + control_path.getForward() + " ForwardPrev: = " + control_path.getForwardPrev()+ " Current: = " + control_path.getCurrent());
        
        
        //test_path4.setLocalTranslation(control_path.getCurrent());
        
        for(Enemy e: Enemy.enemes){
            e.update(tpf);
        }
        Enemy.removeNotAlive();
        
        
        test_path2.setLocalScale(0.4f);
        
        /*Vector3f forward = mage_player.getLocalRotation().mult(Vector3f.UNIT_Z).mult(tpf*8).negateLocal();
        System.out.println("forward = " + forward.length());
        mage_player.lookAt(forward.mult(10), Vector3f.UNIT_Z);
        mage_player.move(forward);*/
        
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
