package entities;

import maths.Plane;
import maths.Vector3;
import maths.Vector3d;

import java.util.ArrayList;
import java.util.List;

import entities.Camera;

public class Player {
	
	private static final float WIDTH = 1.28f;
	private static final float HEIGHT = 2.56f;
	
	private int movespeed = 64;
	private Camera linkedcam;
	
	private Vector3 pos;
	private boolean onGround = true;
	private Vector3d airVelocity;
	private Vector3d airAcceleration;
	private Vector3d groundVelocity;
	private Vector3d groundAcceleration;
	private float delta = 0;
	private float sumDelta = 0;
	
	private Plane p1,p2,p3,p4,p5,p6;
	public List<Plane> planes = new ArrayList<Plane>();
	
	public Player(Camera camera) {
		pos = new Vector3(0,0,0);
		airVelocity = new Vector3d(0,0,0);
		airAcceleration = new Vector3d(0,0,0);
		groundVelocity = new Vector3d(0,0,0);
		groundAcceleration = new Vector3d(0,0,0);
		linkedcam = camera;
		
		Vector3 point1 = new Vector3(-WIDTH/2,0,-WIDTH/2);
		Vector3 point2 = new Vector3(WIDTH/2,0,-WIDTH/2);
		Vector3 point3 = new Vector3(-WIDTH/2,0,WIDTH/2);
		Vector3 point4 = new Vector3(WIDTH/2,0,WIDTH/2);
		Vector3 point5 = new Vector3(-WIDTH/2,HEIGHT,-WIDTH/2);
		Vector3 point6 = new Vector3(WIDTH/2,HEIGHT,-WIDTH/2);
		Vector3 point7 = new Vector3(-WIDTH/2,HEIGHT,WIDTH/2);
		Vector3 point8 = new Vector3(WIDTH/2,HEIGHT,WIDTH/2);
		
		p1 = new Plane(point1,point2,point3);
		p2 = new Plane(point1,point3,point4);
		p3 = new Plane(point1,point2,point4);
		p4 = new Plane(point8,point6,point4);
		p5 = new Plane(point8,point6,point2);
		p6 = new Plane(point8,point5,point3);
		planes.add(p1);
		planes.add(p2);
		planes.add(p3);
		planes.add(p4);
		planes.add(p5);
		planes.add(p6);
		
	}
	
	public void movementKeys(int w, int a, int s, int d, int space) {
		if(onGround == true) {
			double rot = Math.toRadians(linkedcam.getYaw());
			if(w == 1) {
				groundAcceleration.x += (Math.sin(rot)*movespeed);
				groundAcceleration.z -= (Math.cos(rot)*movespeed);
			}
			if(s == 1) {
				groundAcceleration.x -= (Math.sin(rot)*movespeed);
				groundAcceleration.z += (Math.cos(rot)*movespeed);
			}
			if(a == 1) {
				groundAcceleration.x -= (Math.cos(rot)*movespeed);
				groundAcceleration.z -= (Math.sin(rot)*movespeed);
			}
			if(d == 1) {
				groundAcceleration.x += (Math.cos(rot)*movespeed);
				groundAcceleration.z += (Math.sin(rot)*movespeed);
			}
			if(space == 1) {
				jump();
			}
		}

		
	}
	
	public void increasePos() {
		if(onGround == true) {
			pos.x += groundVelocity.x*delta;
			pos.y += groundVelocity.y*delta;
			pos.z += groundVelocity.z*delta;
		}else if(onGround == false) {
			pos.x += airVelocity.x*delta;
			pos.y += airVelocity.y*delta;
			pos.z += airVelocity.z*delta;
		}
		linkedcam.setPosition(pos.x, pos.y+2.5f, pos.z);
	}
	
	public void resistGroundMotion() {
		
		groundVelocity.x = groundVelocity.x/1.3f;
		groundVelocity.z = groundVelocity.z/1.3f;
	}
	
	public void convertAcceleration() {
		if(onGround == true) {
			groundVelocity.x += groundAcceleration.x*delta;
			groundVelocity.y += groundAcceleration.y*delta;
			groundVelocity.z += groundAcceleration.z*delta;
		}else if(onGround == false) {
			airVelocity.x += airAcceleration.x*delta;
			airVelocity.y += airAcceleration.y*delta;
			airVelocity.z += airAcceleration.z*delta;
		}

	
	}
	
	public void inputDelta(float delta) {
		this.delta = delta;
		sumDelta += delta;
	}
	
	public void resetGroundAcceleration() {
		groundAcceleration.x = 0;
		groundAcceleration.z = 0;
	}
	
	public void walk() {
		if(sumDelta > 0.05) {
			sumDelta -= 0.05;
			resistGroundMotion();
		}
		convertAcceleration();
		increasePos();
		resetGroundAcceleration();
		
	}
	
	public void move() {
		if(onGround == true) {
			walk();
		}else if(onGround == false) {
			propel();
		}
		checkGround();
	}
	
	public void propel() {
		airAcceleration.y -= 25*delta;
		airStrafe();
		if(sumDelta > 0.05) {
			sumDelta -= 0.05;
			resistAirMotion();
		}
		convertAcceleration();
		increasePos();
	}
	
	public void jump() {
		if(onGround == true) {
			airAcceleration.y = 2;
			airVelocity.y = 5;
			airVelocity.x = groundVelocity.x;
			airVelocity.z = groundVelocity.z;
			onGround = false;
		}
	}
	
	public void checkGround() {
		if(pos.y > 0) {
			onGround = false;
		}else {
			land();
			onGround = true;
		}
	}
	
	public void airStrafe() {
		double DirMotion = -(180*Math.atan2(airVelocity.x, airVelocity.z)/Math.PI-180);
		if(Math.abs(linkedcam.getYaw() - DirMotion) < 360*delta) {
			double modSpeed = Math.sqrt(Math.pow(airVelocity.x,2) + Math.pow(airVelocity.z,2))+2*delta;
			double modAccel = Math.sqrt(Math.pow(airAcceleration.x,2) + Math.pow(airAcceleration.z,2));
			airAcceleration.x = modAccel * Math.sin(Math.toRadians(linkedcam.getYaw()));
			airAcceleration.z = -modAccel * Math.cos(Math.toRadians(linkedcam.getYaw()));
			airVelocity.x = modSpeed * Math.sin(Math.toRadians(linkedcam.getYaw()));
			airVelocity.z = -modSpeed * Math.cos(Math.toRadians(linkedcam.getYaw()));
		}
	}
	
	public void land() {
		if(onGround == false) {
			groundVelocity.x = airVelocity.x;
			groundVelocity.z = airVelocity.z;
			groundAcceleration.x = airAcceleration.x;
			groundAcceleration.z = airAcceleration.z;
			airAcceleration.y = 0;
			airVelocity.y = 0;
		}

	}
	
	public void resistAirMotion() {
		airVelocity.x = airVelocity.x/1.01f;
		airVelocity.z = airVelocity.z/1.01f;
	}
	
	public float checkDist(Entity entity) {
		Vector3 pCentre = new Vector3(0,0,0);
		Vector3 difference = new Vector3(0,0,0);
		pCentre.x = pos.x;
		pCentre.y = pos.y;
		pCentre.z = pos.z;
		pCentre.x += WIDTH/2;
		pCentre.z += WIDTH/2;
		pCentre.y += HEIGHT/2;
		//difference = pCentre.subtract(entity.getBbox().getMidPoint().add(entity.pos));
		//float Distance = (float) Math.sqrt(Math.pow(difference.x, 2) + Math.pow(difference.y, 2) + Math.pow(difference.y, 2));
		//return Distance;
		return 0;
	}
	
	public Vector3 getPos() {
		return pos;
	}
	
	public void recalcPlanes() {
		p1.recalcConstant(new Vector3(-WIDTH/2,0,-WIDTH/2));
		p2.recalcConstant(new Vector3(-WIDTH/2,0,-WIDTH/2));
		p3.recalcConstant(new Vector3(-WIDTH/2,0,-WIDTH/2));
		
		p4.recalcConstant(new Vector3(WIDTH/2,HEIGHT,WIDTH/2));
		p5.recalcConstant(new Vector3(WIDTH/2,HEIGHT,WIDTH/2));
		p6.recalcConstant(new Vector3(WIDTH/2,HEIGHT,WIDTH/2));
	}
}
