package com.example.androidtest;

import android.opengl.GLES20;

public class GLShader {
	public int v_shader;
	public int f_shader;
	public int program;

	public GLShader(String v_shader, String f_shader) {
		// creating indexes to each shader
		this.v_shader = GLShader.load_shader(
			GLES20.GL_VERTEX_SHADER,
			v_shader
		);
		this.f_shader = GLShader.load_shader(
			GLES20.GL_FRAGMENT_SHADER,
			f_shader
		);
		// compiling and linking each shader in a
		// GLSL program
		this.program = GLES20.glCreateProgram();
		GLES20.glAttachShader(this.program, this.v_shader);
		GLES20.glAttachShader(this.program, this.f_shader);
		GLES20.glLinkProgram(this.program);
	}

	private static int load_shader(int type, String code) {
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, code);
		GLES20.glCompileShader(shader);
		return shader;
	}

	public void use() {
		GLES20.glUseProgram(this.program);
	}

	public int get_attribute(String attr_name) {
		int location = GLES20.glGetAttribLocation(
			this.program, attr_name
		);
		GLES20.glEnableVertexAttribArray(location);
		return location;
	}

	public int get_uniform(String uniform_name) {
		return GLES20.glGetUniformLocation(this.program, uniform_name);
	}
}