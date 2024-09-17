package Models;

import java.util.ArrayList;
import java.util.List;

import maths.Matrix4;
import maths.Plane;
import maths.Vector3;

public class BoundingBox {
	private Vector3 point1,point2,point3,point4,point5,point6,point7,point8;
	
	private List<Plane> planes = new ArrayList<Plane>();
	private Plane p1,p2,p3,p4,p5,p6;
	
	public BoundingBox(Vector3 point1,Vector3 point2,Vector3 point3,Vector3 point4,Vector3 point5,Vector3 point6,Vector3 point7,Vector3 point8, Matrix4 transformation) {
		this.point1 = transformation.multiplyV3(point1);
		this.point2 = transformation.multiplyV3(point2);
		this.point3 = transformation.multiplyV3(point3);
		this.point4 = transformation.multiplyV3(point4);
		this.point5 = transformation.multiplyV3(point5);
		this.point6 = transformation.multiplyV3(point6);
		this.point7 = transformation.multiplyV3(point7);
		this.point8 = transformation.multiplyV3(point8);
		createPlanes();
		planes.add(p1);
		planes.add(p2);
		planes.add(p3);
		planes.add(p4);
		planes.add(p5);
		planes.add(p6);
	}
	
	private void createPlanes(){
		
		p1 = createPlane(point1,point2,point3);
		p2 = createPlane(point8,point2,point6);
		p3 = createPlane(point1,point3,point4);
		p4 = createPlane(point4,point6,point8);
		p5 = createPlane(point1,point2,point6);
		p6 = createPlane(point3,point7,point8);
	}
	
	private Plane createPlane(Vector3 vec1, Vector3 vec2, Vector3 vec3) {
		Plane plane = new Plane(vec1,vec2,vec3);
		return plane;
	}

	public Vector3 getPoint1() {
		return point1;
	}

	public Vector3 getPoint2() {
		return point2;
	}

	public Vector3 getPoint3() {
		return point3;
	}

	public Vector3 getPoint4() {
		return point4;
	}

	public Vector3 getPoint5() {
		return point5;
	}

	public Vector3 getPoint6() {
		return point6;
	}

	public Vector3 getPoint7() {
		return point7;
	}

	public Vector3 getPoint8() {
		return point8;
	}
	
}
