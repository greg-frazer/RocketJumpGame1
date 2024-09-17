package rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import Models.BaseModel;
import Models.TexturedModel;
import entities.Entity;
import maths.MathsFunctions;
import maths.Matrix4;
import shaders.EntityShader;
import textures.Texture;

public class Renderer {
	
	public EntityShader shader;
	public List<Entity> toRender = new ArrayList<Entity>();
	
	public Renderer(EntityShader shader, Matrix4 projectionMatrix) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
		
	public void render(Map<TexturedModel,List<Entity>> entityMap) {
		for(TexturedModel model:entityMap.keySet()) {
			BindModelVao(model.getModel());
			BindModelTexture(model);
			toRender = entityMap.get(model);
			for(Entity entity:toRender) {
				Matrix4 transformationMatrix = MathsFunctions.generateTransformationMatrix(entity);
				shader.loadTransformationMatrix(transformationMatrix);
				GL11.glDrawElements(GL11.GL_TRIANGLES,model.getModel().getVertexCount(),GL11.GL_UNSIGNED_INT,0);
			}
			UnbindModelVao();
		}
	}
	
	
	public void BindModelVao(BaseModel model) {
		int vaoID = model.getVaoID();
		GL30.glBindVertexArray(vaoID);
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
	}
	
	public void BindModelTexture(TexturedModel model) {
		Texture texture = model.getTexture();
		shader.loadReflectivity(model.getTexture().getReflectivity());
		shader.loadSpecular(model.getTexture().getSpecular());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	public void UnbindModelVao(){
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
}
