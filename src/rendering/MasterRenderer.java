package rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import Models.BaseModel;
import Models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import maths.MathsFunctions;
import maths.Matrix4;
import shaders.EntityShader;

public class MasterRenderer {
	
	private final static float FOV = 70;
	private final static float NEAR_Z = 1f;
	private final static float FAR_Z = 500f;
	
	Map<TexturedModel,List<Entity>> entityMap = new HashMap<TexturedModel,List<Entity>>();
	Renderer renderer;
	Matrix4 projectionMatrix;
	EntityShader shader = new EntityShader();
	
	public MasterRenderer() {
		projectionMatrix = createProjectionMatrix();
		renderer = new Renderer(shader,projectionMatrix);
	}
	
	public void render(Camera camera, Light light) {
		initialize();
		shader.start();
		shader.loadViewMatrix(MathsFunctions.generateViewMatrix(camera));
		shader.loadLightPosition(light.getPosition());
		shader.loadLightColour(light.getColour());
		shader.loadLightIntensity(light.getIntensity());
		renderer.render(entityMap);
		shader.stop();
		entityMap.clear();
		
	}
	
	public void batchEntities(Entity entity) {
		TexturedModel model = entity.getModel();
		List<Entity> entitiesWithModel = entityMap.get(model);
		if (entitiesWithModel == null) {
			List<Entity> entityList = new ArrayList<Entity>();
			entityList.add(entity);
			entityMap.put(model, (List<Entity>) entityList);
		}else {
			entitiesWithModel.add(entity);
		}
	}

	
	private void initialize() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0.05f, 0.9f, 1f, 1);
				
	}
	
	private Matrix4 createProjectionMatrix() {
		Matrix4 mat4 = new Matrix4();
		mat4.setIdentity();
		float aspectRatio = 1280f/720; //using constants currently, should change
		
		mat4.m11 = (float) ((1/Math.tan(FOV/2))/aspectRatio);
		mat4.m22 = (float) (1/Math.tan(FOV/2));
		mat4.m33 = -(FAR_Z+NEAR_Z)/(FAR_Z-NEAR_Z);
		mat4.m43 = -1;
		mat4.m34 = -(2*FAR_Z*NEAR_Z)/(FAR_Z-NEAR_Z);
		mat4.m44 = 0;
		
		return mat4;
	}
	
	public void close() {
		shader.closeShader();
	}
}
