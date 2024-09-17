package maths;



import entities.Camera;
import entities.Entity;

public class MathsFunctions {
	
	public static Matrix4 generateTransformationMatrix(Entity entity) {
		Matrix4 TransformationMatrix = new Matrix4();
		TransformationMatrix.setIdentity();
		Vector3 pos = entity.getPos();
		TransformationMatrix.translate(pos);
		TransformationMatrix.rotate(entity.getRotx(), "X");
		TransformationMatrix.rotate(entity.getRoty(), "Y");
		TransformationMatrix.rotate(entity.getRotz(), "Z");
		TransformationMatrix.scale(entity.getScale());
		return TransformationMatrix;
		
	}
	
	public static Matrix4 generateViewMatrix(Camera camera) {
		Matrix4 viewMatrix = new Matrix4();
		viewMatrix.setIdentity();
		Vector3 cameraPos = camera.getPosition();
		Vector3 negativeCameraPos = new Vector3(-cameraPos.x,-cameraPos.y,-cameraPos.z);
		viewMatrix.translate(negativeCameraPos);
		viewMatrix.rotate(-(float)Math.toRadians(camera.getPitch()), "X");
		viewMatrix.rotate(-(float)Math.toRadians(camera.getYaw()), "Y");
		return viewMatrix;
	}
}
