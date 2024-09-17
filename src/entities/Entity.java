package entities;

import Models.BoundingBox;
import Models.TexturedModel;
import maths.MathsFunctions;
import maths.Matrix4;
import maths.Vector3;

public class Entity {
	
	TexturedModel model;
	Vector3 pos;
	float rotx,roty,rotz,scale;
	BoundingBox bBox;
	
	public Entity(TexturedModel model, Vector3 pos, float rotx, float roty, float rotz, float scale) {
		this.model = model;
		this.pos = pos;
		this.rotx = rotx;
		this.roty = roty;
		this.rotz = rotz;
		this.scale = scale;
		Matrix4 transformationMatrix = MathsFunctions.generateTransformationMatrix(this);
		//System.out.println(transformationMatrix.m11 + transformationMatrix.m12 + transformationMatrix.m13 + transformationMatrix.m14);
		this.bBox = new BoundingBox(model.getModel().getBbox().getPoint1(),model.getModel().getBbox().getPoint2(),model.getModel().getBbox().getPoint3(),model.getModel().getBbox().getPoint4(),model.getModel().getBbox().getPoint5(),model.getModel().getBbox().getPoint6(),model.getModel().getBbox().getPoint7(),model.getModel().getBbox().getPoint8(),MathsFunctions.generateTransformationMatrix(this));
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3 getPos() {
		return pos;
	}

	public void setPos(Vector3 pos) {
		this.pos = pos;
	}

	public float getRotx() {
		return rotx;
	}

	public float getRoty() {
		return roty;
	}

	public float getRotz() {
		return rotz;
	}

	public float getScale() {
		return scale;
	}
	
	public void increasePosition(float x, float y, float z) {
		this.pos.x += x;
		this.pos.y += y;
		this.pos.z += z;
	}
	
	public void increaseRotation(float rotX, float rotY, float rotZ) {
		this.rotx += rotX;
		this.roty += rotY;
		this.rotz += rotZ;
	}
	
	public BoundingBox getBbox() {
		return bBox;
	}
	
	
}
