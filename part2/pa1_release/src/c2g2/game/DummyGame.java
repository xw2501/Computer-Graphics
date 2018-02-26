package c2g2.game;

import org.joml.Vector2f;
import org.joml.Vector3f;


import static org.lwjgl.glfw.GLFW.*;

import javax.swing.JOptionPane;

import c2g2.engine.GameItem;
import c2g2.engine.IGameLogic;
import c2g2.engine.MouseInput;
import c2g2.engine.Window;
import c2g2.engine.graph.Camera;
import c2g2.engine.graph.DirectionalLight;
import c2g2.engine.graph.Material;
import c2g2.engine.graph.Mesh;
import c2g2.engine.graph.OBJLoader;
import c2g2.engine.graph.PointLight;

public class DummyGame implements IGameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;
    
    //private static final float SCALE_STEP = 0.01f;
    
    //private static final float TRANSLATE_STEP = 0.01f;
    
    //private static final float ROTATION_STEP = 0.3f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private GameItem[] gameItems;

    private Vector3f ambientLight;

    private PointLight pointLight;

    private DirectionalLight directionalLight;

    private float lightAngle;

    private static final float CAMERA_POS_STEP = 0.05f;
    
    private int currentObj;

    /////////////////////////////////////////////////////////////////////////////////////////
    private final float TOP_BOUND = 60f;
    private final float BOTTOM_BOUND = -60f;
    private final float LEFT_BOUND = 80f;
    private final float RIGHT_BOUND = -80f;
    private final float DEPTH = 5f;
    private final float MIN_DIST_SIDE = 3f;
    private final float MIN_DIST_ALONG = 7f;
    private final float SPEED = 0.25f;
    private final float ACC_SPEED = 0.1f;
    private final float MAX_SPEED = 1.1f;
    private final float SPEED_DECA = 0.95f;
    private final float TURN_SPEED = (float) 3.14/40;
    /////////////////////////////////////////////////////////////////////////////////////////
    
    public DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0.0f, 0.0f, 0.0f);
        lightAngle = -90;
        currentObj=0;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        float reflectance = 1f;        
        // NOTE: 
        //   game init
        /////////////////////////////////////////////////////////////////////////////////////////
        Mesh self_mesh = OBJLoader.loadMesh("src/resources/models/PickUp.obj");
        self_mesh.rotateMesh(new Vector3f(1.0f, 0.0f, 0.0f), (float)-3.14/2);
        self_mesh.scaleMesh(0.5f, 0.5f, 1f);
        Mesh other_mesh = OBJLoader.loadMesh("src/resources/models/cube.obj");
        other_mesh.scaleMesh(0.8f, 0.8f, 1f);
        Mesh bound_mesh = OBJLoader.loadMesh("src/resources/models/cube.obj"); 
        Material self_material = new Material(new Vector3f(0.2f, 1f, 0.2f), reflectance);
        Material other_material = new Material(new Vector3f(1f, 0.2f, 0.2f), reflectance);
        Material bound_material = new Material(new Vector3f(1f, 0.8f, 0f), reflectance);
        
        self_mesh.setMaterial(self_material);
        other_mesh.setMaterial(other_material);
        bound_mesh.setMaterial(bound_material);
        GameItem self_gameItem = new GameItem(self_mesh);
        GameItem other_gameItem_1 = new GameItem(other_mesh);
        GameItem other_gameItem_2 = new GameItem(other_mesh);
        GameItem other_gameItem_3 = new GameItem(other_mesh);
        GameItem other_gameItem_4 = new GameItem(other_mesh);
        GameItem other_gameItem_5 = new GameItem(other_mesh);
        GameItem other_gameItem_6 = new GameItem(other_mesh);
        GameItem other_gameItem_7 = new GameItem(other_mesh);
        GameItem other_gameItem_8 = new GameItem(other_mesh);
        GameItem other_gameItem_9 = new GameItem(other_mesh);
        GameItem other_gameItem_10 = new GameItem(other_mesh);
        GameItem other_gameItem_11 = new GameItem(other_mesh);
        GameItem other_gameItem_12 = new GameItem(other_mesh);
        GameItem bound_gameItem_1 = new GameItem(bound_mesh);
        GameItem bound_gameItem_2 = new GameItem(bound_mesh);
        GameItem bound_gameItem_3 = new GameItem(bound_mesh);
        GameItem bound_gameItem_4 = new GameItem(bound_mesh);
        GameItem bound_gameItem_5 = new GameItem(bound_mesh);
        GameItem bound_gameItem_6 = new GameItem(bound_mesh);
        GameItem bound_gameItem_7 = new GameItem(bound_mesh);
        GameItem bound_gameItem_8 = new GameItem(bound_mesh);
        
        self_gameItem.setPosition(0f, 0f, DEPTH);
        self_gameItem.setAngle((float)3.14/2);
        bound_gameItem_1.setPosition(LEFT_BOUND, TOP_BOUND, DEPTH);
        bound_gameItem_2.setPosition(LEFT_BOUND, BOTTOM_BOUND, DEPTH);
        bound_gameItem_3.setPosition(RIGHT_BOUND, TOP_BOUND, DEPTH);
        bound_gameItem_4.setPosition(RIGHT_BOUND, BOTTOM_BOUND, DEPTH);
        bound_gameItem_5.setPosition(LEFT_BOUND, 0, DEPTH);
        bound_gameItem_6.setPosition(0, BOTTOM_BOUND, DEPTH);
        bound_gameItem_7.setPosition(0, TOP_BOUND, DEPTH);
        bound_gameItem_8.setPosition(RIGHT_BOUND, 0, DEPTH);
        
        other_gameItem_1.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_2.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_3.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_4.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_5.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_6.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_7.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_8.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_9.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_10.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_11.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        other_gameItem_12.reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        
        
        gameItems = new GameItem[]{self_gameItem, bound_gameItem_1, bound_gameItem_2, bound_gameItem_3, bound_gameItem_4, 
        		bound_gameItem_5, bound_gameItem_6, bound_gameItem_7, bound_gameItem_8,
        		 other_gameItem_1, other_gameItem_2, other_gameItem_3, other_gameItem_4, 
        		 other_gameItem_5, other_gameItem_6, other_gameItem_7, other_gameItem_8, 
        		 other_gameItem_9, other_gameItem_10, other_gameItem_11, other_gameItem_12};

        /////////////////////////////////////////////////////////////////////////////////////////
        ambientLight = new Vector3f(0.3f, 0.3f, 0.3f);
        Vector3f lightColour = new Vector3f(1, 1, 1);
        Vector3f lightPosition = new Vector3f(0, 0, 1);
        float lightIntensity = 1.0f;
        pointLight = new PointLight(lightColour, lightPosition, lightIntensity);
        PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);

        lightPosition = new Vector3f(-1, 0, 0);
        lightColour = new Vector3f(1, 1, 1);
        directionalLight = new DirectionalLight(lightColour, lightPosition, lightIntensity);
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {

        if(window.isKeyPressed(GLFW_KEY_Q)){
            // zoom out camera
        	Vector3f cam_position = camera.getPosition();
        	camera.setPosition(cam_position.x, cam_position.y, cam_position.z+0.1f);
        	Vector3f cam_target = camera.getTarget();
        	camera.setTarget(new Vector3f(cam_target.x, cam_target.y, cam_target.z+0.1f));
        }
        else if(window.isKeyPressed(GLFW_KEY_W)){
            // move up camera
        	Vector3f cam_position = camera.getPosition();
        	camera.setPosition(cam_position.x, cam_position.y+0.1f, cam_position.z);
        	Vector3f cam_target = camera.getTarget();
        	camera.setTarget(new Vector3f(cam_target.x, cam_target.y+0.1f, cam_target.z));
        }
        else if(window.isKeyPressed(GLFW_KEY_E)){
        	// zoom in camera
        	Vector3f cam_position = camera.getPosition();
        	camera.setPosition(cam_position.x, cam_position.y, cam_position.z-0.1f);
        	Vector3f cam_target = camera.getTarget();
        	camera.setTarget(new Vector3f(cam_target.x, cam_target.y, cam_target.z-0.1f));
        }
        else if(window.isKeyPressed(GLFW_KEY_A)){
        	// move left camera
        	//Vector3f cam_position = camera.getPosition();
        	//camera.setPosition(cam_position.x+0.1f, cam_position.y, cam_position.z);
        	//Vector3f cam_target = camera.getTarget();
        	//camera.setTarget(new Vector3f(cam_target.x+0.1f, cam_target.y, cam_target.z));
        	float lookAngle = gameItems[0].getlookAngle();
        	gameItems[0].setlookAngle(lookAngle+0.1f);
        }
        else if(window.isKeyPressed(GLFW_KEY_S)){
        	// move down camera
        	Vector3f cam_position = camera.getPosition();
        	camera.setPosition(cam_position.x, cam_position.y-0.1f, cam_position.z);
        	Vector3f cam_target = camera.getTarget();
        	camera.setTarget(new Vector3f(cam_target.x, cam_target.y-0.1f, cam_target.z));
        }
        else if(window.isKeyPressed(GLFW_KEY_D)){
        	// move right camera
        	//Vector3f cam_position = camera.getPosition();
        	//camera.setPosition(cam_position.x-0.1f, cam_position.y, cam_position.z);
        	//Vector3f cam_target = camera.getTarget();
        	//camera.setTarget(new Vector3f(cam_target.x-0.1f, cam_target.y, cam_target.z));
        	float lookAngle = gameItems[0].getlookAngle();
        	gameItems[0].setlookAngle(lookAngle-0.1f);
        }
        else if(window.isKeyPressed(GLFW_KEY_UP)){
        	// move up object
        	Vector3f speed = gameItems[0].getSpeed();
        	gameItems[0].setSpeed(Math.min(speed.x+ACC_SPEED, MAX_SPEED), speed.y, speed.z);
        	float lookAngle = gameItems[0].getlookAngle();
        	gameItems[0].setlookAngle(lookAngle*0.8f);
        }
        else if(window.isKeyPressed(GLFW_KEY_DOWN)){
        	// move down object
        	Vector3f speed = gameItems[0].getSpeed();
        	gameItems[0].setSpeed(Math.max(speed.y-ACC_SPEED, -MAX_SPEED), speed.y, speed.z);
        	float lookAngle = gameItems[0].getlookAngle();
        	gameItems[0].setlookAngle(lookAngle*0.8f);
        }
        else if(window.isKeyPressed(GLFW_KEY_LEFT)){
        	// move left object
        	float angle = gameItems[0].getAngle();
        	gameItems[0].setAngle(angle-TURN_SPEED);
        }
        else if(window.isKeyPressed(GLFW_KEY_RIGHT)){
        	// move right object
        	float angle = gameItems[0].getAngle();
        	gameItems[0].setAngle(angle+TURN_SPEED);
        }
        else if(window.isKeyPressed(GLFW_KEY_0)){
            //rotation by manipulating mesh
            gameItems[currentObj].getMesh().translateMesh(new Vector3f(0.5f,0.5f,0.5f));
        }
        else if(window.isKeyPressed(GLFW_KEY_9)){
            //rotation by manipulating mesh
            gameItems[currentObj].getMesh().rotateMesh(new Vector3f(1,1,1), 30);
        }
        else if(window.isKeyPressed(GLFW_KEY_8)){
            //rotation by manipulating mesh
            gameItems[currentObj].getMesh().scaleMesh(0.5f,0.5f,0.5f);
        }
        else if(window.isKeyPressed(GLFW_KEY_7)){
            //rotation by manipulating mesh
            gameItems[currentObj].getMesh().reflectMesh(new Vector3f(0f,1f,0f), new Vector3f(0f, 0f, 1f));
        }
        else if(window.isKeyPressed(GLFW_KEY_1)){
            //get screenshot
            renderer.writePNG(window);
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput, Window window) throws Exception {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse            
        if (mouseInput.isLeftButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            System.out.println(rotVec);
            Vector3f curr = gameItems[0].getRotation();
            gameItems[0].setRotation(curr.x+ rotVec.x * MOUSE_SENSITIVITY, curr.y+rotVec.y * MOUSE_SENSITIVITY, 0);
        }

        // Update directional light direction, intensity and color
        lightAngle += 1.1f;
        
        if (lightAngle > 90) {
            directionalLight.setIntensity(0);
            if (lightAngle >= 90) {
                lightAngle = -90;
            }
        } else if (lightAngle <= -80 || lightAngle >= 80) {
            float factor = 1 - (float) (Math.abs(lightAngle) - 80) / 10.0f;
            directionalLight.setIntensity(factor);
            directionalLight.getColor().y = Math.max(factor, 0.9f);
            directionalLight.getColor().z = Math.max(factor, 0.5f);
        } else {
            directionalLight.setIntensity(1);
            directionalLight.getColor().x = 1;
            directionalLight.getColor().y = 1;
            directionalLight.getColor().z = 1;
        }
        double angRad = Math.toRadians(lightAngle);
        directionalLight.getDirection().x = (float) Math.sin(angRad);
        directionalLight.getDirection().y = (float) Math.cos(angRad);
        
        // update position and check if game over
        Vector3f self_position = gameItems[0].getPosition();
        Vector3f self_speed = gameItems[0].getSpeed();
        float angle = gameItems[0].getAngle();
        float x_position = self_position.x+self_speed.x*(float)Math.cos(angle);
        float y_position = self_position.y+self_speed.x*(float)Math.sin(angle);
        self_position.x = Math.min(Math.max(x_position, RIGHT_BOUND), LEFT_BOUND);
        self_position.y = Math.min(Math.max(y_position, BOTTOM_BOUND), TOP_BOUND);
        gameItems[0].setPosition(self_position.x, self_position.y, self_position.z);
        gameItems[0].setSpeed(self_speed.x*SPEED_DECA, self_speed.y*SPEED_DECA, self_speed.z*SPEED_DECA);
        gameItems[0].setRotation(0.0f, 0.0f, angle-(float)3.14/2);
        
        float lookAngle = gameItems[0].getlookAngle();
        camera.setPosition(self_position.x-(float)Math.cos(angle+lookAngle)*5, 
        		self_position.y-(float)Math.sin(angle+lookAngle)*5, 0.0f);
        camera.setTarget(new Vector3f(self_position.x, self_position.y, DEPTH/2));
        camera.setUp(new Vector3f(0.0f, 0.0f, -1.0f));
        
        
        for(int i=9; i<gameItems.length; i++) {
        	Vector3f speed = gameItems[i].getSpeed();
        	Vector3f object_position = gameItems[i].getPosition();
        	
        	object_position.x += speed.x;
        	object_position.y += speed.y;
        	object_position.z += speed.z;
        	
        	float dist_x = object_position.x - self_position.x;
        	float dist_y = object_position.y - self_position.y;
        	
        	double dist_along = Math.abs(dist_x*Math.cos(angle) + dist_y*Math.sin(angle));
        	double dist_side = Math.abs(dist_x*Math.cos(angle-3.14/2) + dist_y*Math.sin(angle-3.14/2));
        	
        	if(dist_along<MIN_DIST_ALONG && dist_side<MIN_DIST_SIDE) {
        		int response = JOptionPane.showConfirmDialog(null, "Start a new game?", "Game Over", 
        				JOptionPane.YES_NO_OPTION);
        		try {
					if(response==JOptionPane.YES_OPTION) {
						this.init(window);
						break;
					}
					else if(response==JOptionPane.NO_OPTION){
						System.exit(1);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	
        	if(object_position.x<=RIGHT_BOUND || object_position.x>=LEFT_BOUND ||
        			object_position.y>=TOP_BOUND || object_position.y<=BOTTOM_BOUND) {
        		gameItems[i].reset(TOP_BOUND, BOTTOM_BOUND, RIGHT_BOUND, LEFT_BOUND, SPEED, DEPTH);
        	}
        	else {
        		gameItems[i].setPosition(object_position.x, object_position.y, object_position.z);
        	}
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, gameItems, ambientLight, pointLight, directionalLight);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (GameItem gameItem : gameItems) {
            gameItem.getMesh().cleanUp();
        }
    }

}
