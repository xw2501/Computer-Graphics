package c2g2.engine.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import c2g2.engine.GameItem;

public class Transformation {

    private final Matrix4f projectionMatrix;
    
    private final Matrix4f viewMatrix;
    
    private final Matrix4f modelMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        projectionMatrix.identity();
        //// --- student code ---
        float aspect = width / height;
        float tan_fov = (float) Math.tan(fov/2);
        
        projectionMatrix.m00(1/aspect/tan_fov);
        projectionMatrix.m11(1/tan_fov);
        projectionMatrix.m22((zNear+zFar)/(zNear-zFar));
        projectionMatrix.m32(2*zFar*zNear/(zNear-zFar));
        projectionMatrix.m23(-1);
        
        return projectionMatrix;
    }
    
    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f cameraTarget = camera.getTarget();
        Vector3f up = camera.getUp();
        viewMatrix.identity();
        //// --- student code ---
        Vector3f x = new Vector3f();
        Vector3f y = new Vector3f();
        Vector3f z = new Vector3f();
        
        z.x = cameraTarget.x - cameraPos.x;
        z.y = cameraTarget.y - cameraPos.y;
        z.z = cameraTarget.z - cameraPos.z;
        float z_length = (float) Math.sqrt(z.x*z.x+z.y*z.y+z.z*z.z);
        z.x = z.x / z_length;
        z.y = z.y / z_length;
        z.z = z.z / z_length;
        x.x = up.y*z.z - up.z*z.y;
        x.y = up.z*z.x - up.x*z.z;
        x.z = up.x*z.y - up.y*z.x;
        float x_length = (float) Math.sqrt(x.x*x.x+x.y*x.y+x.z*x.z);
        x.x = x.x / x_length;
        x.y = x.y / x_length;
        x.z = x.z / x_length;
        y.x = z.y*x.z - z.z*x.y;
        y.y = z.z*x.x - z.x*x.z;
        y.z = z.x*x.y - z.y*x.x; 
        
        viewMatrix.m00(x.x);
        viewMatrix.m01(x.y);
        viewMatrix.m02(x.z);
        viewMatrix.m03((-1)*(x.x*cameraPos.x+x.y*cameraPos.y+x.z*cameraPos.z));
        
        viewMatrix.m10(y.x);
        viewMatrix.m11(y.y);
        viewMatrix.m12(y.z);
        viewMatrix.m13((-1)*(y.x*cameraPos.x+y.y*cameraPos.y+y.z*cameraPos.z));
        
        viewMatrix.m20(z.x);
        viewMatrix.m21(z.y);
        viewMatrix.m22(z.z);
        viewMatrix.m23((-1)*(z.x*cameraPos.x+z.y*cameraPos.y+z.z*cameraPos.z));

        return viewMatrix;
    }
    
    public Matrix4f getModelMatrix(GameItem gameItem){
        Vector3f rotation = gameItem.getRotation();
        Vector3f position = gameItem.getPosition();
        modelMatrix.identity();
        //// --- student code ---
        Matrix4f rotation_x = new Matrix4f();
        Matrix4f rotation_y = new Matrix4f();
        Matrix4f rotation_z = new Matrix4f();
        Matrix4f translation = new Matrix4f();
        
        rotation_x.m11((float) Math.cos(rotation.x));
        rotation_x.m21((float) -Math.sin(rotation.x));
        rotation_x.m12((float) Math.sin(rotation.x));
        rotation_x.m22((float) Math.cos(rotation.x));
        
        rotation_y.m00((float) Math.cos(rotation.y));
        rotation_y.m20((float) Math.sin(rotation.y));
        rotation_y.m02((float) -Math.sin(rotation.y));
        rotation_y.m22((float) Math.cos(rotation.y));
        
        rotation_z.m00((float) Math.cos(rotation.z));
        rotation_z.m10((float) -Math.sin(rotation.z));
        rotation_z.m01((float) Math.sin(rotation.z));
        rotation_z.m11((float) Math.cos(rotation.z));
        
        translation.m30(position.x);
        translation.m31(position.y);
        translation.m32(position.z);
        
        modelMatrix.mul(translation);
        modelMatrix.mul(rotation_z);
        modelMatrix.mul(rotation_y);
        modelMatrix.mul(rotation_x);
        
        return modelMatrix;
    }

    public Matrix4f getModelViewMatrix(GameItem gameItem, Matrix4f viewMatrix) {
        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(getModelMatrix(gameItem));
    }
}
