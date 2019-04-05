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
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import java.util.ArrayList;
import java.util.List;
import mygame.enemy.Enemy;
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
    private AnimChannel mage_channel;
    private AnimControl mage_control;
    private List<Node> spatialsWorld;
    private CameraNode camNode;
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
        rootNode.attachChild(mage_player);
        
        List<CellFactory> cells = new ArrayList<>();
        Node spatial = (Node) assetManager.loadModel("Models/Road.j3o");
        cells.add(new CellFactory(spatial, 0));
        Node spatial1 = (Node) assetManager.loadModel("Models/Road2.j3o");
        cells.add(new CellFactory(spatial1, 1));
        Node spatial2 = (Node) assetManager.loadModel("Models/Road3.j3o");
        cells.add(new CellFactory(spatial2, 2));
        //rootNode.attachChild(spatial);
        
        
        
        rootNode.attachChild(test_path);
        rootNode.attachChild(test_path1);
        rootNode.attachChild(test_path2);
        rootNode.attachChild(test_path4);
        gen = new Generator(mage_player, cells);
        rootNode.attachChild(gen.getWorld());
        control_path = new ControlPath(mage_player, gen.getPath());
    }
    
    private void setLight(){
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(new Vector3f(1f, -1f, 1).normalizeLocal());
        rootNode.addLight(dl);
    }
    
    private void init(){
        flyCam.setEnabled(false);
        flyCam.setMoveSpeed(100);
        camNode = new CameraNode("CamNode", cam);
        camNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        camNode.setLocalTranslation(new Vector3f(0, 4, -6));
        camNode.lookAt(new Vector3f(0, 0, 16).add(mage_player.getLocalTranslation()), Vector3f.UNIT_Y);
        mage_player.attachChild(camNode);
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
        
        test_path.setLocalTranslation(new Vector3f(mage_player.getLocalTranslation()).add(new Vector3f(control_path.getForwardPrev()).add(control_path.getForward())));
        test_path.setLocalScale(0.1f);
        test_path1.setLocalTranslation(new Vector3f(mage_player.getLocalTranslation()).add(control_path.getForwardPrev()));
        test_path1.setLocalScale(0.1f);
        
        test_path4.setLocalTranslation(control_path.getNext(shift));
        
        for(Enemy e: Enemy.enemes){
            test_path2.setLocalTranslation(e.az.pos);
            int k = e.az.isCollision(mage_player.getLocalTranslation(), 3);
            //System.out.println(mage_player.getLocalTranslation());
            if(k != -1){
                count_collision++;
                System.out.println("Collision in Sector = " + k + " " + count_collision);
            }
        }
        
        
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
