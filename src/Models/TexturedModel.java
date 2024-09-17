package Models;

import textures.Texture;

public class TexturedModel {
	
	private BaseModel model;
	private Texture texture;
	
	public TexturedModel(BaseModel model, Texture texture) {
		this.model = model;
		this.texture = texture;
	}

	public BaseModel getModel() {
		return model;
	}

	public Texture getTexture() {
		return texture;
	}

}
