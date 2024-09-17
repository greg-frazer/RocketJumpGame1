package maths;

public class Plane {
	
	private Vector3 normal;
	private float constant;
	
	public Plane(Vector3 a, Vector3 b, Vector3 c) {
		Vector3 vec1 = new Vector3(0,0,0);
		Vector3 vec2 = new Vector3(0,0,0);
		
		vec1 = b.subtract(a);
		vec2 = b.subtract(c);
		normal = vec1.cross(vec2);
		constant = normal.dot(b);
	}
	
	public void recalcConstant(Vector3 vec) {
		constant = normal.dot(vec);
	}
	
	public Vector2 intersectionPoint(Plane plane) {
		if(plane.getNormal() != normal) {
			Matrix2 mat2 = new Matrix2(normal.x,normal.z,plane.getNormal().x,plane.getNormal().z);
			mat2.invert();
			Vector2 vec2 = new Vector2(constant,plane.getConstant());
			
			Vector2 intersection = new Vector2(0,0);
			intersection = mat2.multiplyVec2(vec2);
			return intersection;
		}else {
			return new Vector2(100000000,1000000000);
		}
	}
	
	public Vector3 getNormal() {
		return normal;
	}
	
	public float getConstant() {
		return constant;
	}
}
