package Models;

public class BaseModel {
	
	private int vaoID;
	private int vertexCount;
	private BoundingBox bBox;
	
	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	public BaseModel(int vaoID,int vertexCount, BoundingBox boundingBox) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
		this.bBox = boundingBox;
	}
	
	public BoundingBox getBbox() {
		return bBox;
	}
	
}
