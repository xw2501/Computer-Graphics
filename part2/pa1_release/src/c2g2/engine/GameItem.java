package c2g2.engine;

import org.joml.Vector3f;
import c2g2.engine.graph.Mesh;

public class GameItem {

    private final Mesh mesh;
    
    private final Vector3f position;
    
    private float scale;

    private final Vector3f rotation;
    
    private final Vector3f speed;
    
    private float angle;
    
    private float lookAngle;

    public GameItem(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 50);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
        speed = new Vector3f(0, 0, 0);
        angle = 0;
        lookAngle = 0;
    }
    
    public float getlookAngle() {
    	return lookAngle;
    }
    
    public void setlookAngle(float theta) {
    	this.lookAngle = theta;
    }
    
    public Vector3f getSpeed() {
    	return speed;
    }
    
    public void setSpeed(float x, float y, float z) {
    	this.speed.x = x;
    	this.speed.y = y;
    	this.speed.z = z;
    }
    
    public float getAngle() {
    	return angle;
    }
    
    public void setAngle(float theta) {
    	this.angle = theta;
    }
    
    public void reset(float TOP_BOUND, float BOTTOM_BOUND, float RIGHT_BOUND, float LEFT_BOUND, float SPEED, float DEPTH) {
    	int start_dir = (int) Math.floor(Math.random()*4);
    	if(start_dir==0) {
    		float start_pos = (float) Math.random()*2*TOP_BOUND - TOP_BOUND;
    		this.setPosition(RIGHT_BOUND, start_pos, DEPTH);
    		this.setSpeed(SPEED, 0f, 0f);
    	}
    	else if(start_dir==1) {
    		float start_pos = (float) Math.random()*2*TOP_BOUND - TOP_BOUND;
    		this.setPosition(LEFT_BOUND, start_pos, DEPTH);
    		this.setSpeed(-SPEED, 0f, 0f);
    	}
    	else if(start_dir==2) {
    		float start_pos = (float) Math.random()*2*LEFT_BOUND - LEFT_BOUND;
    		this.setPosition(start_pos, BOTTOM_BOUND, DEPTH);
    		this.setSpeed(0f, SPEED, 0f);
    	}
    	else {
    		float start_pos = (float) Math.random()*2*LEFT_BOUND - LEFT_BOUND;
    		this.setPosition(start_pos, TOP_BOUND, DEPTH);
    		this.setSpeed(0f, -SPEED, 0f);
    	}
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public Mesh getMesh() {
        return mesh;
    }
}