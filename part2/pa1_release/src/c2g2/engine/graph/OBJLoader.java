package c2g2.engine.graph;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;


public class OBJLoader {
    public static Mesh loadMesh(String fileName) throws Exception {
    	//// --- student code ---
    	BufferedReader br = new BufferedReader(new FileReader(fileName));
    	ArrayList<Float> pos_list = new ArrayList<>();
    	ArrayList<Float> txt_list = new ArrayList<>();
    	ArrayList<Float> nrm_list = new ArrayList<>();
    	ArrayList<Integer> idx_list = new ArrayList<>();
    	
    	while(true){
    		String in_line = br.readLine();
    		if(in_line==null) {
    			break;
    		}
    		if(in_line.startsWith("vt")) {
    			int space_idx1 = in_line.indexOf(" ");
    			int space_idx2 = in_line.indexOf(" ", space_idx1+1);
    			int space_idx3 = in_line.indexOf(" ", space_idx2+1);
    			if(space_idx3==-1) {
    				space_idx3 = in_line.length();
    			}
    			
    			txt_list.add(Float.parseFloat(in_line.substring(space_idx1+1, space_idx2)));
    			txt_list.add(Float.parseFloat(in_line.substring(space_idx2+1, space_idx3)));
    			continue;
    		}
    		if(in_line.startsWith("vn")) {
    			int space_idx1 = in_line.indexOf(" ");
    			int space_idx2 = in_line.indexOf(" ", space_idx1+1);
    			int space_idx3 = in_line.indexOf(" ", space_idx2+1);
    			
    			nrm_list.add(Float.parseFloat(in_line.substring(space_idx1+1, space_idx2)));
    			nrm_list.add(Float.parseFloat(in_line.substring(space_idx2+1, space_idx3)));
    			nrm_list.add(Float.parseFloat(in_line.substring(space_idx3+1, in_line.length())));
    			continue;
    		}
    		if(in_line.startsWith("v")) {
    			int space_idx1 = in_line.indexOf(" ");
    			int space_idx2 = in_line.indexOf(" ", space_idx1+1);
    			int space_idx3 = in_line.indexOf(" ", space_idx2+1);
    			
    			pos_list.add(Float.parseFloat(in_line.substring(space_idx1+1, space_idx2)));
    			pos_list.add(Float.parseFloat(in_line.substring(space_idx2+1, space_idx3)));
    			pos_list.add(Float.parseFloat(in_line.substring(space_idx3+1, in_line.length())));
    			continue;
    		}
    		if(in_line.startsWith("f")) {
    			int space_idx1 = in_line.indexOf(" ");
    			int space_idx2 = in_line.indexOf(" ", space_idx1+1);
    			int space_idx3 = in_line.indexOf(" ", space_idx2+1);
    			
    			int idx = space_idx1+1;
    			while(Character.isDigit(in_line.charAt(idx))) {
    				idx += 1;
    			}
    			idx_list.add(Integer.parseInt(in_line.substring(space_idx1+1, idx)));
    			
    			idx = space_idx2+1;
    			while(Character.isDigit(in_line.charAt(idx))) {
    				idx += 1;
    			}
    			idx_list.add(Integer.parseInt(in_line.substring(space_idx2+1, idx)));
    			
    			idx = space_idx3+1;
    			while(Character.isDigit(in_line.charAt(idx))) {
    				idx += 1;
    			}
    			idx_list.add(Integer.parseInt(in_line.substring(space_idx3+1, idx)));
    			
    			//idx_list.add(Integer.parseInt(in_line.substring(space_idx2+1, in_line.indexOf("//", space_idx2+1))));
    			//idx_list.add(Integer.parseInt(in_line.substring(space_idx3+1, in_line.indexOf("//", space_idx3+1))));
    			continue;
    		}
    	}
    	
    	float[] positions = new float[pos_list.size()];
        float[] norms = new float[nrm_list.size()];
        int[] indices = new int[idx_list.size()];
        float[] textCoords;
        if(txt_list.size()==0) {
        	textCoords = new float[pos_list.size()/3*2];
        }
        else {
        	textCoords = new float[txt_list.size()];
        }
        
        for(int i=0; i<positions.length; i++) {
        	positions[i] = pos_list.get(i);
        }
        for(int i=0; i<norms.length; i++) {
        	norms[i] = nrm_list.get(i);
        }
        for(int i=0; i<indices.length; i++) {
        	indices[i] = idx_list.get(i)-1;
        }
        if(txt_list.size()==0) {
        	for(int i=0; i<textCoords.length; i++) {
        		textCoords[i] = 0;
        	}
        }
        else {
        	for(int i=0; i<textCoords.length; i++) {
            	textCoords[i] = txt_list.get(i);
            }
        }
        
        //your task is to read data from an .obj file and fill in those arrays.
        //the data in those arrays should use following format.
        //positions[0]=v[0].position.x positions[1]=v[0].position.y positions[2]=v[0].position.z positions[3]=v[1].position.x ...
        //textCoords[0]=v[0].texture_coordinates.x textCoords[1]=v[0].texture_coordinates.y textCoords[2]=v[1].texture_coordinates.x ...
        //norms[0]=v[0].normals.x norms[1]=v[0].normals.y norms[2]=v[0].normals.z norms[3]=v[1].normals.x...
        //indices[0]=face[0].ind[0] indices[1]=face[0].ind[1] indices[2]=face[0].ind[2] indices[3]=face[1].ind[0]...(assuming all the faces are triangle face)
        
        return new Mesh(positions, textCoords, norms, indices);
    }

}
