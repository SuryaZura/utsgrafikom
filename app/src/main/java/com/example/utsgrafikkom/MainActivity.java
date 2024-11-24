package com.example.utsgrafikkom;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it as the ContentView
        glSurfaceView = new MyGLSurfaceView(this);
        glSurfaceView.setRenderer(new Render());
        setContentView(glSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Pause the GLSurfaceView
        glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Resume the GLSurfaceView
        glSurfaceView.onResume();
    }
}
