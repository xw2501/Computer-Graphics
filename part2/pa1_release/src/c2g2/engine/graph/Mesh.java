package c2g2.engine.graph;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {

    private int vaoId;

    private List<Integer> vboIdList;

    private int vertexCount;

    private Material material;
    
    private float[] pos;
    private float[] textco;
    private float[] norms;
    private int[] inds;
    
    
    public Mesh(){
       this(new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.5f,0.0f,0.5f,0.0f,0.0f,0.5f,0.5f,0.5f,0.0f,0.0f,0.5f,0.0f,0.5f,0.5f,0.5f,0.0f,0.5f,0.5f,0.5f}, 
    			new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f}, 
    			new float[]{0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f}, 
    			new int[]{0,6,4,0,2,6,0,3,2,0,1,3,2,7,6,2,3,7,4,6,7,4,7,5,0,4,5,0,5,1,1,5,7,1,7,3});
    }
    
    public void setMesh(float[] positions, float[] textCoords, float[] normals, int[] indices){
    	pos = positions;
    	textco = textCoords;
    	norms = normals;
    	inds = indices;
    	FloatBuffer posBuffer = null;
        FloatBuffer textCoordsBuffer = null;
        FloatBuffer vecNormalsBuffer = null;
        IntBuffer indicesBuffer = null;
        System.out.println("create mesh:");
        System.out.println("v: "+positions.length+" t: "+textCoords.length+" n: "+normals.length+" idx: "+indices.length);
        try {
            vertexCount = indices.length;
            vboIdList = new ArrayList<Integer>();

            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            // Position VBO
            int vboId = glGenBuffers();
            vboIdList.add(vboId);
            posBuffer = MemoryUtil.memAllocFloat(positions.length);
            posBuffer.put(positions).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

            // Texture coordinates VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length);
            textCoordsBuffer.put(textCoords).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

            // Vertex normals VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length);
            vecNormalsBuffer.put(normals).flip();
            glBindBuffer(GL_ARRAY_BUFFER, vboId);
            glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW);
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);

            // Index VBO
            vboId = glGenBuffers();
            vboIdList.add(vboId);
            indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

            glBindBuffer(GL_ARRAY_BUFFER, 0);
            glBindVertexArray(0);
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer);
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer);
            }
            if (vecNormalsBuffer != null) {
                MemoryUtil.memFree(vecNormalsBuffer);
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer);
            }
        }
    }

    public Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
    	setMesh(positions, textCoords, normals, indices);        
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void render() {
    		// Draw the mesh
        glBindVertexArray(getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glBindVertexArray(0);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void cleanUp() {
        glDisableVertexAttribArray(0);

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId);
        }

        // Delete the VAO
        glBindVertexArray(0);
        glDeleteVertexArrays(vaoId);
    }
    
    public void scaleMesh(float sx, float sy, float sz){
    	cleanUp(); //clean up buffer
    	//Reset position of each point
    	//Do not change textco, norms, inds
    	//student code 
    	for (int i = 0; i < pos.length/3; i++) {
    		pos[3*i] *= sx;
    		pos[3*i+1] *= sy;
    		pos[3*i+2] *= sz;
		}   	
    	setMesh(pos, textco, norms, inds);
    }
    
    public void translateMesh(Vector3f trans){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	for(int i=0; i< pos.length/3; i++){
    		pos[3*i] += trans.x;
    		pos[3*i+1] += trans.y;
    		pos[3*i+2] += trans.z;
    	}
    	setMesh(pos, textco, norms, inds);
    }
    
    public void rotateMesh(Vector3f axis, float angle){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	float axis_x = axis.x;
    	float axis_y = axis.y;
    	float axis_z = axis.z;
    	float axis_length = (float)Math.sqrt(axis_x*axis_x+axis_y*axis_y+axis_z*axis_z);
    	axis_x /= axis_length;
    	axis_y /= axis_length;
    	axis_z /= axis_length;
    	for(int i=0; i< pos.length/3; i++){
        	float pre_x = pos[3*i];
        	float pre_y = pos[3*i+1];
        	float pre_z = pos[3*i+2];
        		
        	pos[3*i] = 0;
        	pos[3*i+1] = 0;
        	pos[3*i+2] = 0;
        	pos[3*i] += (float) (Math.cos((double)angle) + axis_x*axis_x*(1-Math.cos((double)angle)))*pre_x;
        	pos[3*i] += (float) (axis_x*axis_y*(1-Math.cos((double)angle))-axis_z*Math.sin((double)angle))*pre_y;
        	pos[3*i] += (float) (axis_x*axis_z*(1-Math.cos((double)angle))+axis_y*Math.sin((double)angle))*pre_z;
        	pos[3*i+1] += (float) (axis_x*axis_y*(1-Math.cos((double)angle))+axis_z*Math.sin((double)angle))*pre_x;
        	pos[3*i+1] += (float) (Math.cos((double)angle) + axis_y*axis_y*(1-Math.cos((double)angle)))*pre_y;
        	pos[3*i+1] += (float) (axis_y*axis_z*(1-Math.cos((double)angle))-axis_x*Math.sin((double)angle))*pre_z;
        	pos[3*i+2] += (float) (axis_x*axis_z*(1-Math.cos((double)angle))-axis_y*Math.sin((double)angle))*pre_x;
        	pos[3*i+2] += (float) (axis_y*axis_z*(1-Math.cos((double)angle))+axis_x*Math.sin((double)angle))*pre_y;
        	pos[3*i+2] += (float) (Math.cos((double)angle) + axis_z*axis_z*(1-Math.cos((double)angle)))*pre_z;
    	}
    	setMesh(pos, textco, norms, inds);
    }
    
    public void reflectMesh(Vector3f p, Vector3f n){
    	cleanUp();
    	//reset position of each point
    	//Do not change textco, norms, inds
    	//student code
    	float a = n.x;
    	float b = n.y;
    	float c = n.z;
    	float d = (-1)*(p.x*n.x+p.y*n.y+p.z*n.z);
    	for(int i=0; i< pos.length/3; i++){
    		float pre_x = pos[3*i];
        	float pre_y = pos[3*i+1];
        	float pre_z = pos[3*i+2];
        	
        	pos[3*i] = (1-2*a*a)*pre_x + (-2)*a*b*pre_y + (-2)*a*c*pre_z + (-2)*a*d;
        	pos[3*i+1] = (-2)*a*b*pre_x + (1-2*b*b)*pre_y + (-2)*b*c*pre_z + (-2)*b*d;
        	pos[3*i+2] = (-2)*a*c*pre_x + (-2)*b*c*pre_y + (1-2*c*c)*pre_z + (-2)*c*d;
    	}
    	setMesh(pos, textco, norms, inds);
    }
}
