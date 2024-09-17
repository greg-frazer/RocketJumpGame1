package entities;

import maths.Vector3;

public class Light {
	
	private float intensity;
	private Vector3 colour;
	private Vector3 position;
	
	public Light(float intensity, Vector3 colour, Vector3 position) {
		this.colour = colour;
		this.intensity = intensity;
		this.position = position;
	}

	public float getIntensity() {
		return intensity;
	}

	public Vector3 getColour() {
		return colour;
	}

	public Vector3 getPosition() {
		return position;
	}
	
	
}
