package com.example.androidtest;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.opengl.GLSurfaceView;
import android.opengl.GLES20;
import android.content.Context;
import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.EGLConfig;

public class MyGLRenderer implements GLSurfaceView.Renderer {

	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		// Set the background frame color
		GLES20.glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
	}

	public void onDrawFrame(GL10 unused) {
		// Redraw background color
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	}

	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
	}
}