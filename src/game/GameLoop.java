package game;


import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import static org.lwjgl.glfw.GLFW.glfwGetCursorPos;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL;


import Models.BaseModel;
import rendering.OBJReader;

import rendering.DisplayWindow;
import rendering.MasterRenderer;
import rendering.ModelLoader;
import textures.Texture;
import Models.TexturedModel;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import maths.Vector3;

public class GameLoop {
	
	public static DisplayWindow window;
	static ModelLoader loader;
	static MasterRenderer renderer;
	static BaseModel Greg;
	static TexturedModel texGreg;
	static Entity entity;
	static Entity entity2;
	static Entity entity3;
	static Entity entity4;
	static Camera camera;
	static Light sun;
	static boolean mouselock = false;
	static double newx = 0;
	static double newy = 0;
	static DoubleBuffer xy;
	static Player player;
	static List<Entity> entities;
	
	
	public static void initialize() {
		window = new DisplayWindow();
		window.createWindow();
		GL.createCapabilities();
		loader = new ModelLoader();
		renderer = new MasterRenderer();
	
		Greg = OBJReader.readOBJ("Stall", loader);
		texGreg = new TexturedModel(Greg,new Texture(loader.loadTexture("stallTexture")));
		entity = new Entity(texGreg, new Vector3(-15f,0,0), 0, (float) (Math.PI/2), 0, 1);
		entity2 = new Entity(texGreg, new Vector3(0,0,-15f), 0, (float)(Math.PI), 0, 1);
		entity3 = new Entity(texGreg, new Vector3(15f,0,0), 0, (float) (3*Math.PI/2), 0, 1);
		entity4 = new Entity(texGreg, new Vector3(0,0,15f), 0, 0, 0, 1);
		entities = new ArrayList<Entity>();
		entities.add(entity);
		entities.add(entity2);
		entities.add(entity3);
		entities.add(entity4);
		
		camera = new Camera();
		player = new Player(camera);
		sun = new Light(1,new Vector3(1,0.925f,0.761f),new Vector3(0,5f,-10f));
		xy = BufferUtils.createDoubleBuffer(2);
	}
	
	public static void main(String[] args) {
		
		initialize();
		
		while(!window.close()) {
			render();
			game();
		}
		
		renderer.close();
		GLFW.glfwTerminate();
	}
	
	public static void render() {
		for(Entity entity:entities) {
			renderer.batchEntities(entity);
		}
		renderer.render(camera,sun);
		
		window.swapBuffers();
		window.updateDisplay();
		
	}
	
	public static void game() {
		//entity2.increaseRotation(0, 0.02f, 0);
		mouseCamera();
		movementKeys();
		player.inputDelta(window.returnFrameTime());
		player.move();
		player.checkGround();
		
		
		camera.clampYaw();
		
		
	}
	
	public static void mouseCamera() {
		if(mouselock == false) {
			mouselock = window.mouselock();
		}else {
			mouselock = window.mouseUnlock();
			
			xy = window.pollmouse();
			newx = xy.get();
			newy = xy.get();
			double deltax = newx - 640;
			double deltay = newy - 360;
			
			deltax = deltax/640 *35;
			deltay = deltay/360 *35;
			
			camera.increaseYaw(deltax);
			if(!(camera.getPitch() == 90 && deltay > 0)) {
				camera.increasePitch(deltay);
			}else if(!(camera.getPitch() == -90 && deltay < 0)) {
				camera.increasePitch(deltay);
			}
			if(camera.getPitch() > 90) {
				camera.setPitch(90);
			}else if(camera.getPitch() < -90) {
				camera.setPitch(-90);
			}
			window.resetmouse();
			
		}
	}
	
	public static void movementKeys() {
		List<Integer> keys = new ArrayList<Integer>();
		keys = window.pollMovementKeys();
		player.movementKeys(keys.get(0), keys.get(1), keys.get(2), keys.get(3), keys.get(4));
	}
	
}
