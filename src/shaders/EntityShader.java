package shaders;

import maths.Matrix4;
import maths.Vector3;

public class EntityShader extends AbstractShader {
	
	private static final String VertexShader = "src/shaders/entityVertexShader.txt";
	private static final String FragmentShader = "src/shaders/entityFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrixT;
	private int location_viewMatrixR;
	private int location_lightColour;
	private int location_lightPosition;
	private int location_lightIntensity;
	private int location_cameraPosition;
	private int location_reflectivity;
	private int location_specular;

	public EntityShader() {
		super(VertexShader, FragmentShader);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribLocation(0, "position");
		super.bindAttribLocation(1,"textureCoords");
		super.bindAttribLocation(2, "normal");
		
	}

	@Override
	protected void getUniformVariableLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrixT = super.getUniformLocation("viewMatrixT");
		location_viewMatrixR = super.getUniformLocation("viewMatrixR");
		location_lightColour = super.getUniformLocation("lightColour");
		location_lightPosition = super.getUniformLocation("lightPosition");
		location_lightIntensity = super.getUniformLocation("lightIntensity");
		location_cameraPosition = super.getUniformLocation("cameraPosition");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_specular = super.getUniformLocation("specular");
		
	}

	public void loadProjectionMatrix(Matrix4 projectionMatrix) {
		super.loadMatrix4(location_projectionMatrix, projectionMatrix);
	}
	
	public void loadTransformationMatrix(Matrix4 transformationMatrix) {
		super.loadMatrix4(location_transformationMatrix, transformationMatrix);
	}

	public void loadViewMatrix(Matrix4 viewMatrix) {
		Matrix4 viewMatrixT = new Matrix4();
		viewMatrixT.setIdentity();
		Matrix4 viewMatrixR = new Matrix4();
		viewMatrixR.setIdentity();
		
		viewMatrixT.m14 = viewMatrix.m14;
		viewMatrixT.m24 = viewMatrix.m24;
		viewMatrixT.m34 = viewMatrix.m34;
		
		viewMatrixR.m11 = viewMatrix.m11;
		viewMatrixR.m12 = viewMatrix.m12;
		viewMatrixR.m13 = viewMatrix.m13;
		
		viewMatrixR.m21 = viewMatrix.m21;
		viewMatrixR.m22 = viewMatrix.m22;
		viewMatrixR.m23 = viewMatrix.m23;
		
		viewMatrixR.m31 = viewMatrix.m31;
		viewMatrixR.m32 = viewMatrix.m32;
		viewMatrixR.m33 = viewMatrix.m33;
		super.loadMatrix4(location_viewMatrixT, viewMatrixT);
		super.loadMatrix4(location_viewMatrixR, viewMatrixR);
		
	}
	
	public void loadLightColour(Vector3 lightColour) {
		super.loadVector3(location_lightColour, lightColour);
	}
	
	public void loadLightPosition(Vector3 lightPos) {
		super.loadVector3(location_lightPosition, lightPos);
	}
	
	public void loadLightIntensity(float intensity) {
		super.loadFloat(location_lightIntensity,intensity);
	}
	
	public void loadCameraPosition(Vector3 cameraPosition) {
		super.loadVector3(location_cameraPosition, cameraPosition);
	}
	
	public void loadReflectivity(float reflectivity) {
		super.loadFloat(location_reflectivity, reflectivity);
	}
	
	public void loadSpecular(float specular) {
		super.loadFloat(location_specular, specular);
	}

}
