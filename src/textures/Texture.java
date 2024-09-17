package textures;

public class Texture {
	
	private int textureID;
	private float reflectivity = 1;
	private float specular = 10;
	
	public Texture(int ID) {
		textureID = ID;
	}

	public int getTextureID() {
		return textureID;
	}

	public void setReflectivity(float reflectivity) {
		this.reflectivity = reflectivity;
	}

	public void setSpecular(float specular) {
		this.specular = specular;
	}

	public float getReflectivity() {
		return reflectivity;
	}

	public float getSpecular() {
		return specular;
	}
	
}
