package rendering;

import java.nio.DoubleBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import maths.Vector3;

public class DisplayWindow {

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final String TITLE = "Rocket jump game";
	private long window;

	private static long lastFrameTime;
	private static float delta;

	public void createWindow() { // called once to create a window which the game lives in
		if(!GLFW.glfwInit()) {	
			System.err.println("failed to initialize");
			return;
		}
		window = GLFW.glfwCreateWindow(WIDTH, HEIGHT, TITLE, 0, 0);
		
		if (window == 0) {
			System.err.println("failed to create window");
			return;
		}
		GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
		GLFW.glfwSetWindowPos(window,(videoMode.width()-WIDTH)/2,(videoMode.height()-HEIGHT)/2);
		GLFW.glfwShowWindow(window);
		GLFW.glfwMakeContextCurrent(window);
		GLFW.glfwSetInputMode(window, GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		
		GLFW.glfwSwapInterval(1);
		
		lastFrameTime = getCurrentTime();
	}

	public void updateDisplay() {
		GLFW.glfwPollEvents();
		
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f; // delta is in seconds
		lastFrameTime = currentFrameTime;

	}
	
	public void swapBuffers() {
		GLFW.glfwSwapBuffers(window);
	}

	public boolean close() {
		return GLFW.glfwWindowShouldClose(window);
		
	}

	public static long getCurrentTime() {
		return System.currentTimeMillis();
	}

	public float returnFrameTime() { // called as a multiplier for things like movement, to make it real time						// based rather than framerate based
		return delta;
	}
	
	public boolean mouselock() {
		boolean mouselock = true;
		//if(GLFW.glfwGetMouseButton(window, GLFW.GLFW_MOUSE_BUTTON_1) == GLFW.GLFW_PRESS) {
		//	mouselock = true;
		//}
		return mouselock;
	}
	
	public boolean mouseUnlock() {
		boolean mouselock = true;
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == GLFW.GLFW_PRESS) {
			mouselock = false;
		}
		return mouselock;
	}
	
	public DoubleBuffer pollmouse() {
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, x, y);
		DoubleBuffer xy = BufferUtils.createDoubleBuffer(2);
		xy.put(x.get());
		xy.put(y.get());
		x.rewind();
		y.rewind();
		xy.flip();
		return xy;

	}
	
	public void resetmouse() {
		GLFW.glfwSetCursorPos(window, WIDTH/2, HEIGHT/2);
	}
	
	public List<Integer> pollMovementKeys() {
		List<Integer> keys = new ArrayList<Integer>();
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == GLFW.GLFW_PRESS) {
			keys.add(1);
		}else {
			keys.add(0);
		}
		
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == GLFW.GLFW_PRESS) {
			keys.add(1);
		}else {
			keys.add(0);
		}
		
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == GLFW.GLFW_PRESS) {
			keys.add(1);
		}else {
			keys.add(0);
		}
		
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == GLFW.GLFW_PRESS) {
			keys.add(1);
		}else {
			keys.add(0);
		}
		
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
			keys.add(1);
		}else {
			keys.add(0);
		}
		return keys;
	}

}
