package rendering;


import java.io.FileInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import de.matthiasmann.twl.utils.PNGDecoder;
import maths.Matrix4;
import maths.Vector3;
import Models.BaseModel;
import Models.BoundingBox;

public class ModelLoader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	public void closeVAOs() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for (int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}

	public BaseModel storeToVAO(float[] coordinates, float[] textures, float[] normals, int[] indices) {
		int vaoID = createVAO();
		createIndicesVBO(indices); // creates a vbo for the vertex indices of the model, defining the order to draw vertices
		storeDataInAttributeList(0, 3, coordinates); // stores x,y,z of each vertex
		storeDataInAttributeList(1, 2, textures);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		BoundingBox bBox = createBbox(coordinates);
		return new BaseModel(vaoID, indices.length, bBox);
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}

	private void createIndicesVBO(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);// bind VBO
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // add data to VBO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void storeDataInAttributeList(int attributeListNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); // bind the VBO
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); // add data to VBO
		GL20.glVertexAttribPointer(attributeListNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // unbind the VBO

	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;

	}

	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	public int loadTexture(String fileName) {
		
		PNGDecoder decoder;
		try {
			decoder = new PNGDecoder(new FileInputStream("res/"+fileName+".png"));
			ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());//create a byte buffer 4*width*height (RGBA for each pixel)
			decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buffer.flip();
			int id = GL11.glGenTextures();
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	    	GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
	    	GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	    	GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
	    	GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	    	textures.add(id);// texture ID is stored to be referenced later
	    	return id;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 0;


	}
	
	public BoundingBox createBbox(float[] coordinates) {
		float maxX = coordinates[0];
		float minX = coordinates[0];
		for(int i=0; i < coordinates.length; i += 3) {
			if(coordinates[i] > maxX) {
				maxX = coordinates[i];
			}else if(coordinates[i] < minX) {
				minX = coordinates[i];
			}
		}
		float maxY = coordinates[0];
		float minY = coordinates[0];
		for(int i=1; i < coordinates.length; i += 3) {
			if(coordinates[i] > maxY) {
				maxY = coordinates[i];
			}else if(coordinates[i] < minY) {
				minY = coordinates[i];
			}
		}
		float maxZ = coordinates[0];
		float minZ = coordinates[0];
		for(int i=2; i < coordinates.length; i += 3) {
			if(coordinates[i] > maxZ) {
				maxZ = coordinates[i];
			}else if(coordinates[i] < minZ) {
				minZ = coordinates[i];
			}
		}
		
		Vector3 vec1 = new Vector3(minX,minY,minZ);
		Vector3 vec2 = new Vector3(maxX,minY,minZ);
		Vector3 vec3 = new Vector3(minX,maxY,minZ);
		Vector3 vec4 = new Vector3(minX,minY,maxZ);
		Vector3 vec5 = new Vector3(minX,maxY,maxZ);
		Vector3 vec6 = new Vector3(maxX,minY,maxZ);
		Vector3 vec7 = new Vector3(maxX,maxY,minZ);
		Vector3 vec8 = new Vector3(maxX,maxY,maxZ);
		Matrix4 identity = new Matrix4();
		identity.setIdentity();
		BoundingBox bbox = new BoundingBox(vec1,vec2,vec3,vec4,vec5,vec6,vec7,vec8,identity);
		return bbox;
	}

}
