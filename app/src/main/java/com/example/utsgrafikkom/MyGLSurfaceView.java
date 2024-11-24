package com.example.utsgrafikkom;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer Renderer;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Set OpenGL ES version to 2.0
        setEGLContextClientVersion(2);

        // Create a new Renderer instance
        Renderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(Renderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}

