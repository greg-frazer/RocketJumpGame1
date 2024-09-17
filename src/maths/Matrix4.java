package maths;

import java.nio.FloatBuffer;

public class Matrix4 {

		public float m11;
		public float m12;
		public float m13;
		public float m14;
		
		public float m21;
		public float m22;
		public float m23;
		public float m24;
		
		public float m31;
		public float m32;
		public float m33;
		public float m34;
		
		public float m41;
		public float m42;
		public float m43;
		public float m44;
		
		public Matrix4() {
			this.m11 = 0;
			this.m12 = 0;
			this.m13 = 0;
			this.m14 = 0;
			
			this.m21 = 0;
			this.m22 = 0;
			this.m23 = 0;
			this.m24 = 0;
			
			this.m31 = 0;
			this.m32 = 0;
			this.m33 = 0;
			this.m34 = 0;
			
			this.m41 = 0;
			this.m42 = 0;
			this.m43 = 0;
			this.m44 = 0;
		}
		
		public void setIdentity() {
			this.m11 = 1;
			this.m22 = 1;
			this.m33 = 1;
			this.m44 = 1;
		}
		
		public void translate(Vector3 pos) {
			this.m14 += pos.x;
			this.m24 += pos.y;
			this.m34 += pos.z;
		}
		
		public void rotate(float angle, String axis) {
			Matrix3 currentRot = new Matrix3();
			Matrix3 newRot = new Matrix3();
			newRot.setIdentity();
			currentRot = createMatrix3();
			
			if(axis == "Y") {
				newRot.m11 = (float) Math.cos(angle);
				newRot.m13 = (float) -Math.sin(angle);
				newRot.m31 = (float) Math.sin(angle);
				newRot.m33 = (float) Math.cos(angle);
			}else if(axis =="X") {
				newRot.m22 = (float) Math.cos(angle);
				newRot.m23 = (float) Math.sin(angle);
				newRot.m32 = (float) -Math.sin(angle);
				newRot.m33 = (float) Math.cos(angle);
			}else if(axis == "Z") {
				newRot.m11 = (float) Math.cos(angle);
				newRot.m12 = (float) Math.sin(angle);
				newRot.m21 = (float) -Math.sin(angle);
				newRot.m22 = (float) Math.cos(angle);
			}else {
				System.err.println("No axis for rotation");
			}
			Matrix3 endRot = new Matrix3();
			endRot = currentRot.multiply(newRot);
			this.m11 = endRot.m11;
			this.m12 = endRot.m12;
			this.m13 = endRot.m13;
			
			this.m21 = endRot.m21;
			this.m22 = endRot.m22;
			this.m23 = endRot.m23;
			
			this.m31 = endRot.m31;
			this.m32 = endRot.m32;
			this.m33 = endRot.m33;
		}
		
		public Matrix3 createMatrix3() {
			Matrix3 mat3 = new Matrix3();
			mat3.m11 = this.m11;
			mat3.m12 = this.m12;
			mat3.m13 = this.m13;
			
			mat3.m21 = this.m21;
			mat3.m22 = this.m22;
			mat3.m23 = this.m23;
			
			mat3.m31 = this.m31;
			mat3.m32 = this.m32;
			mat3.m33 = this.m33;
			return mat3;
		}
		
		public void scale(float scale) {
			this.m11 = this.m11 * scale;
			this.m22 = this.m22 * scale;
			this.m33 = this.m33 * scale;
		}
		
		public FloatBuffer toBuffer (FloatBuffer buffer) {
			buffer.put(m11);
			buffer.put(m21);
			buffer.put(m31);
			buffer.put(m41);
			
			buffer.put(m12);
			buffer.put(m22);
			buffer.put(m32);
			buffer.put(m42);
			
			buffer.put(m13);
			buffer.put(m23);
			buffer.put(m33);
			buffer.put(m43);
			
			buffer.put(m14);
			buffer.put(m24);
			buffer.put(m34);
			buffer.put(m44);
			
			buffer.flip();
			return buffer;
		}
		
		public Vector3 multiplyV3(Vector3 vector) {
			float nx = vector.x*this.m11 + vector.y*this.m12 + vector.z*this.m13 + this.m14;
			float ny = vector.x*this.m21 + vector.y*this.m22 + vector.z*this.m23 + this.m24;
			float nz = vector.x*this.m31 + vector.y*this.m32 + vector.z*this.m33 + this.m34;
			Vector3 vector3 = new Vector3(nx,ny,nz);
			return vector3;
		}
}
