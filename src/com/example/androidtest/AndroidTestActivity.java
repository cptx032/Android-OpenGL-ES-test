package com.example.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.opengl.GLSurfaceView;
import android.content.Context;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;
import javax.microedition.khronos.egl.EGLConfig;
import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

class Triangle {

	public String vertexShaderCode =
		"attribute vec4 vPosition;" +
		"void main() {" +
		"  gl_Position = vPosition;" +
		"}";

	public String fragmentShaderCode =
		"precision mediump float;" +
		"uniform vec4 vColor;" +
		"void main() {" +
		"  gl_FragColor = vColor;" +
		"}";

	public GLShader shader;


	private FloatBuffer vertexBuffer;
	public int mProgram;

	// number of coordinates per vertex in this array
	static final int COORDS_PER_VERTEX = 3;
	static float triangleCoords[] = {   // in counterclockwise order:
		0.0f,  0.622008459f, 0.0f, // top
		-0.5f, -0.311004243f, 0.0f, // bottom left
		0.5f, -0.311004243f, 0.0f  // bottom right
	};

	private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
	private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

	private int mPositionHandle;
	private int mColorHandle;



	// Set color with red, green, blue and alpha (opacity) values
	float color[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };

	public void draw()
	{
		
		// Add program to OpenGL ES environment
		this.shader.use();


		// get handle to vertex shader's vPosition member
		mPositionHandle = this.shader.get_attribute("vPosition");

		// Prepare the triangle coordinate data
		GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
									 GLES20.GL_FLOAT, false,
									 vertexStride, vertexBuffer);

		mColorHandle = this.shader.get_uniform("vColor");

		// Set color for drawing the triangle
		GLES20.glUniform4fv(mColorHandle, 1, color, 0);

		// Draw the triangle
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

		// Disable vertex array
		GLES20.glDisableVertexAttribArray(mPositionHandle);

	}

	public Triangle() {
		shader = new GLShader(vertexShaderCode, fragmentShaderCode);
		// initialize vertex byte buffer for shape coordinates
		ByteBuffer bb = ByteBuffer.allocateDirect(
		// (number of coordinate values * 4 bytes per float)
		triangleCoords.length * 4);
		// use the device hardware's native byte order
		bb.order(ByteOrder.nativeOrder());

		// create a floating point buffer from the ByteBuffer
		vertexBuffer = bb.asFloatBuffer();
		// add the coordinates to the FloatBuffer
		vertexBuffer.put(triangleCoords);
		// set the buffer to read the first coordinate
		vertexBuffer.position(0);
	}
}

class MyGLRenderer implements GLSurfaceView.Renderer {

	private Triangle mTriangle;
	//private Square   mSquare;

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		mTriangle = new Triangle();
	}

	public void onDrawFrame(GL10 unused) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
		mTriangle.draw();
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}
}

class MyGLSurfaceView extends GLSurfaceView {

	private final MyGLRenderer mRenderer;

	public MyGLSurfaceView(Context context){
		super(context);

		// Create an OpenGL ES 2.0 context
		setEGLContextClientVersion(2);

		mRenderer = new MyGLRenderer();

		// Set the Renderer for drawing on the GLSurfaceView
		setRenderer(mRenderer);
	}
}

public class AndroidTestActivity extends Activity
{
	private GLSurfaceView mGLView;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.mGLView = new MyGLSurfaceView(this);
		setContentView(mGLView);
	}
}
