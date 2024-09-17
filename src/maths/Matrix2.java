package maths;

public class Matrix2 {
	
	public float m11,m12,m21,m22;
	
	public Matrix2(float m11, float m12, float m21, float m22) {
		this.m11 = m11;
		this.m12 = m12;
		this.m21 = m21;
		this.m22 = m22;
	}
	
	public void invert() {
		float temp = m11;
		m11 = m22;
		m22 = temp;
		m12 = -m12;
		m21 = - m21;
	}
	
	public Vector2 multiplyVec2(Vector2 vec2) {
		float nx = m11*vec2.x + m21*vec2.y;
		float ny = m21*vec2.x + m22*vec2.y;
		
		Vector2 newVec = new Vector2(nx,ny);
		return newVec;
	}
}
