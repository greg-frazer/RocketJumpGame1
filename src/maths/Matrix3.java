package maths;

public class Matrix3 {

	public float m11;
	public float m12;
	public float m13;
	
	public float m21;
	public float m22;
	public float m23;
	
	public float m31;
	public float m32;
	public float m33;
	
	public Matrix3() {
		
		this.m11 = 0;
		this.m12 = 0;
		this.m13 = 0;
		
		this.m21 = 0;
		this.m22 = 0;
		this.m23 = 0;
		
		this.m31 = 0;
		this.m32 = 0;
		this.m33 = 0;
		
	}
	
	public void setIdentity() {
		this.m11 = 1;
		this.m22 = 1;
		this.m33 = 1;
	}
	
	public Matrix3 multiply(Matrix3 matrix) {
		Matrix3 endMatrix = new Matrix3();
		float newm11 = this.m11*matrix.m11 + this.m12*matrix.m21 + this.m13*matrix.m31;
		float newm12 = this.m11*matrix.m12 + this.m12*matrix.m22 + this.m13*matrix.m32;
		float newm13 = this.m11*matrix.m13 + this.m12*matrix.m23 + this.m13*matrix.m33;
		
		float newm21 = this.m21*matrix.m11 + this.m22*matrix.m21 + this.m23*matrix.m31;
		float newm22 = this.m21*matrix.m12 + this.m22*matrix.m22 + this.m23*matrix.m32;
		float newm23 = this.m21*matrix.m13 + this.m22*matrix.m23 + this.m23*matrix.m33;
		
		float newm31 = this.m31*matrix.m11 + this.m32*matrix.m21 + this.m33*matrix.m31;
		float newm32 = this.m31*matrix.m12 + this.m32*matrix.m22 + this.m33*matrix.m32;
		float newm33 = this.m31*matrix.m13 + this.m32*matrix.m23 + this.m33*matrix.m33;
		
		endMatrix.m11 = newm11;
		endMatrix.m12 = newm12;
		endMatrix.m13 = newm13;
		endMatrix.m21 = newm21;
		endMatrix.m22 = newm22;
		endMatrix.m23 = newm23;
		endMatrix.m31 = newm31;
		endMatrix.m32 = newm32;
		endMatrix.m33 = newm33;
		
		return endMatrix;
	}
}
