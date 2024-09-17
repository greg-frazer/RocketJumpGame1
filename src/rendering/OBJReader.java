package rendering;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import maths.Vector2;
import maths.Vector3;

import Models.BaseModel;

public class OBJReader {

	public static BaseModel readOBJ(String fileName, ModelLoader loader) {
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(new File("res/" + fileName + ".obj"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String currentline;
		List<Vector3> vertices = new ArrayList<Vector3>();
		List<Vector2> textures = new ArrayList<Vector2>();
		List<Vector3> normals = new ArrayList<Vector3>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] vertexArray = null;
		float[] texturesArray = null;
		float[] normalsArray = null;
		int[] indicesArray = null;

		try {
			while (true) {
				currentline = bufferedReader.readLine();
				String[] splitline = currentline.split(" ");
				if (currentline.startsWith("v ")) {
					Vector3 vertex = new Vector3(Float.parseFloat(splitline[1]), Float.parseFloat(splitline[2]),
							Float.parseFloat(splitline[3]));
					vertices.add(vertex);
				} else if (currentline.startsWith("vt ")) {
					Vector2 texturecoord = new Vector2(Float.parseFloat(splitline[1]),
							Float.parseFloat(splitline[2]));
					textures.add(texturecoord);
				} else if (currentline.startsWith("vn ")) {
					Vector3 normal = new Vector3(Float.parseFloat(splitline[1]), Float.parseFloat(splitline[2]),
							Float.parseFloat(splitline[3]));
					normals.add(normal);
				} else if (currentline.startsWith("f ")) { // when we reach lines starting with f (faces) we need to
					// start assembling the vertices, texture coords and normals
					break;
				}
			}

			texturesArray = new float[vertices.size() * 2];
			normalsArray = new float[vertices.size() * 3]; // the arrays will be passed to the ModelLoader to construct
															// a BaseModel
			vertexArray = new float[vertices.size() * 3];

			while (currentline != null) {
				if (!currentline.startsWith("f ")) {
					currentline = bufferedReader.readLine();
					continue;
				}
				String[] splitline = currentline.split(" ");
				for (int i = 1; i < 4; i++) {
					String[] faceVertex = splitline[i].split("/");
					int vertexNumber = Integer.parseInt(faceVertex[0]) - 1;
					indices.add(vertexNumber);
					Vector2 currentTexture = textures.get(Integer.parseInt(faceVertex[1]) - 1); // the texture coords
																									// and normals are
																									// put into the
																									// arrays in order
					texturesArray[2 * vertexNumber] = currentTexture.x;
					texturesArray[2 * vertexNumber + 1] = 1- currentTexture.y;
					Vector3 currentNormal = normals.get(Integer.parseInt(faceVertex[2]) - 1);
					normalsArray[3 * vertexNumber] = currentNormal.x;
					normalsArray[3 * vertexNumber + 1] = currentNormal.y;
					normalsArray[3 * vertexNumber + 2] = currentNormal.z;
				}
				currentline = bufferedReader.readLine();
			}
			indicesArray = new int[indices.size()];

			int currentVertex = 0;
			for (Vector3 vertex : vertices) {
				vertexArray[currentVertex] = vertex.x;
				vertexArray[currentVertex + 1] = vertex.y;
				vertexArray[currentVertex + 2] = vertex.z;
				currentVertex += 3;
			}

			for (int i = 0; i < indices.size(); i++) {
				indicesArray[i] = indices.get(i); // indices list transferred to an array
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		BaseModel model;
		model = loader.storeToVAO(vertexArray, texturesArray, normalsArray, indicesArray);
		return model; // a base model is returned

	}
	


}
