package maths;

public class Vector3 {
	
	public float x;
	public float y;
	public float z;
	
	public Vector3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float length() {
		float length = (float) Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z);
		return length;
	}
	
	public float dot(Vector3 vector) {
		float dot = this.x*vector.x + this.y*vector.y + this.z*vector.z;
		return dot;
	}
	
	public Vector3 cross(Vector3 vector) {
		float x = this.y*vector.z - this.z*vector.y;
		float y = this.z*vector.x - this.x-vector.z;
		float z = this.x*vector.y - this.y*vector.x;
		Vector3 vector3 = new Vector3(x,y,z);
		return vector3;
	}
	
	public void normalize() {
		float length = length();
		this.x = this.x/length;
		this.y = this.y/length;
		this.z = this.z/length;
	}
	
	public Vector3 add(Vector3 vector) {
		float x = this.x + vector.x;
		float y = this.y + vector.y;
		float z = this.z + vector.z;
		Vector3 vector3 = new Vector3(x,y,z);
		return vector3;
	}
	
	public Vector3 subtract(Vector3 vector) {
		float x = this.x - vector.x;
		float y = this.y - vector.y;
		float z = this.z - vector.z;
		Vector3 vector3 = new Vector3(x,y,z);
		return vector3;
	}
	
}
