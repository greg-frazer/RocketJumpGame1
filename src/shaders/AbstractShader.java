package shaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;


import maths.Matrix4;
import maths.Vector3;

public abstract class AbstractShader {

	private int ProgramID;
	private int VertexShaderID;
	private int FragmentShaderID;
	public FloatBuffer Buffer = null;
	
	public AbstractShader(String VertexShader, String FragmentShader) {
		VertexShaderID = loadShader(VertexShader,GL20.GL_VERTEX_SHADER);
		FragmentShaderID = loadShader(FragmentShader,GL20.GL_FRAGMENT_SHADER);
		ProgramID = GL20.glCreateProgram();
		GL20.glAttachShader(ProgramID, VertexShaderID);
		GL20.glAttachShader(ProgramID, FragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(ProgramID);
		GL20.glValidateProgram(ProgramID);
		getUniformVariableLocations();
		
		ByteBuffer buf=ByteBuffer.allocateDirect(64);
		buf.order(ByteOrder.nativeOrder());
		this.Buffer=buf.asFloatBuffer();
		
	

		
	}
	
	protected abstract void bindAttributes();
	
	protected abstract void getUniformVariableLocations();
	
	public int loadShader(String shaderFile, int shaderType) {
		StringBuilder readShader = new StringBuilder();
		FileReader fileReader;
		try {
			fileReader = new FileReader(shaderFile);
		BufferedReader reader = new BufferedReader(fileReader);
		String line;
		line = reader.readLine();
		while(line != null) {
			readShader.append(line);
			readShader.append("\n");
			line = reader.readLine();
		}
		reader.close();
		
		}catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
		}
		
		int shaderID = GL20.glCreateShader(shaderType);
		GL20.glShaderSource(shaderID, readShader);
		GL20.glCompileShader(shaderID);
		if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS)==GL11.GL_FALSE) {
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader.");
			System.exit(-1);
		}
		return shaderID;
	}

	public void start() {
		GL20.glUseProgram(ProgramID);
	}
	
	public void stop() {
		GL20.glUseProgram(0);
	}
	
	public void closeShader() {
		stop();
		GL20.glDetachShader(ProgramID, VertexShaderID);
		GL20.glDetachShader(ProgramID, FragmentShaderID);
		GL20.glDeleteShader(VertexShaderID);
		GL20.glDeleteShader(FragmentShaderID);
		GL20.glDeleteProgram(ProgramID);
	}
	
	public void bindAttribLocation(int attribute, String name) {
		GL20.glBindAttribLocation(ProgramID, attribute, name);
	}
	
	public int getUniformLocation(String name) {
		return GL20.glGetUniformLocation(ProgramID, name);
	}
	
	public void loadInt(int location, int value) {
		GL20.glUniform1i(location, value);
	}
	
	public void loadFloat(int location, float value) {
		GL20.glUniform1f(location, value);
	}
	
	public void loadVector3(int location, Vector3 value) {
		GL20.glUniform3f(location, value.x,value.y,value.z);
	}
	
	public void loadMatrix4(int location, Matrix4 value) {
		Buffer = value.toBuffer(Buffer);
		GL20.glUniformMatrix4fv(location, false, Buffer);
	}
	
	public void loadBoolean(int location, boolean value) {
		int bool = 0;
		if(value == true) {
			bool = 1;
		}
		GL20.glUniform1f(location, bool);
	}
		
}
