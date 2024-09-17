package entities;

import maths.Vector3;

public class Camera {
	
	private Vector3 position = new Vector3(0,0,0);
	private double pitch = 0;
	private double yaw = 0;
	private double roll = 0;
	
	public double getPitch() {
		return pitch;
	}
	
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public void increasePitch(double pitch) {
		this.pitch += pitch;
	}
	public double getYaw() {
		return yaw;
	}
	public void increaseYaw(double yaw) {
		this.yaw += yaw;
	}
	public double getRoll() {
		return roll;
	}
	public void increaseRoll(double roll) {
		this.roll += roll;
	}
	public Vector3 getPosition() {
		return position;
	}
	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}
	public void clampYaw() {
		if(yaw > 360) {
			yaw -= 360;
		}else if(yaw < 0) {
			yaw +=360;
		}
	}
	

}
