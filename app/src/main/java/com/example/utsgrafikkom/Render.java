package com.example.utsgrafikkom;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Render implements GLSurfaceView.Renderer {
    private FloatBuffer triangleBuffer;
    private FloatBuffer rectangleBuffer;
    private FloatBuffer axisBuffer;

    // Data vertikal untuk berbagai bentuk
    private final float[] triangleCoords = {
            0.1f,  0.3f, 0.0f,  // atas
            0.3f,  0.1f, 0.0f,  // kanan bawah
            0.1f,  0.1f, 0.0f   // kiri bawah
    };

    private final float[] rectangleCoords = {
            -0.1f,  0.3f, 0.0f,  // kanan atas
            -0.3f,  0.3f, 0.0f,  // kiri atas
            -0.1f,  0.1f, 0.0f,  // kanan bawah
            -0.3f,  0.1f, 0.0f   // kiri bawah
    };

    private final float[] axisCoords = {
            -1.0f, 0.0f, 0.0f,   // sumbu X
            1.0f, 0.0f, 0.0f,
            0.0f, -1.0f, 0.0f,   // sumbu Y
            0.0f, 1.0f, 0.0f
    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Atur warna latar belakang menjadi putih
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Inisialisasi buffer untuk bentuk
        triangleBuffer = createFloatBuffer(triangleCoords);
        rectangleBuffer = createFloatBuffer(rectangleCoords);
        axisBuffer = createFloatBuffer(axisCoords);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        float aspectRatio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-aspectRatio, aspectRatio, -1, 1, -1, 1);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();

        // Gambar sumbu koordinat
        drawAxis(gl);

        // Gambar bentuk asli
        renderShapes(gl, false);

        // Gambar bentuk yang sudah ditransformasi
        renderShapes(gl, true);
    }

    private FloatBuffer createFloatBuffer(float[] coords) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(coords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(coords);
        floatBuffer.position(0);
        return floatBuffer;
    }

    private void drawAxis(GL10 gl) {
        gl.glLineWidth(2.0f);
        gl.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, axisBuffer);
        gl.glDrawArrays(GL10.GL_LINES, 0, 4);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    private void renderShapes(GL10 gl, boolean applyTransform) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        if (applyTransform) {
            // Transformasi dan gambar segitiga
            gl.glPushMatrix();
            gl.glTranslatef(0.5f, -0.6f, 0.0f);
            gl.glRotatef(90, 0, 0, 1);
            gl.glScalef(1.5f, 1.5f, 1.0f);
        }

        // Gambar segitiga
        gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, triangleBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        if (applyTransform) {
            gl.glPopMatrix();

            // Transformasi dan gambar persegi panjang
            gl.glPushMatrix();
            gl.glTranslatef(0.2f, -0.3f, 0.0f);
            gl.glRotatef(45, 0, 0, 1);
            gl.glScalef(1.5f, 1.5f, 1.0f);
        }

        // Gambar persegi panjang
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, rectangleBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        if (applyTransform) {
            gl.glPopMatrix();
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
