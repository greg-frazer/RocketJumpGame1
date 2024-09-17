package maths;

public class Vector2 {

		public float x;
		public float y;
		
		public Vector2(float x, float y) {
			this.x = x;
			this.y = y;
		}
		
		public float length() {
			float length = (float) Math.sqrt(this.x*this.x + this.y*this.y);
			return length;
		}
		
		public float dot(Vector2 vector) {
			float dot = this.x*vector.x + this.y*vector.y;
			return dot;
		}
		
		public void normalize() {
			float length = length();
			this.x = this.x/length;
			this.y = this.y/length;
		}
}
