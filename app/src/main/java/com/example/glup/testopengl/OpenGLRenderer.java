package com.example.glup.testopengl;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import com.example.glup.testopengl.mesh.Cube;
import com.example.glup.testopengl.mesh.Group;
import com.example.glup.testopengl.mesh.Mesh;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 19/12/15.
 */
public class OpenGLRenderer implements Renderer{
    private Square square;
    private float angle;
    private Cube cube;
    private Group root;
    public OpenGLRenderer(){
        //square=new Square();
        Group group = new Group();
        /*cube= new Cube(1,1,1);
        cube.rx=45;
        cube.ry=45;
        group.add(cube);*/
        root = group;
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //set background color to balck rgba
        gl.glClearColor(1.0f, 0.0f, 0.0f, 0.5f);
        //enable smooth shading, default not really needed
        gl.glShadeModel(GL10.GL_SMOOTH);
        //depth buffer setup
        gl.glClearDepthf(1.0f);
        //enables depth testing
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //the type of depth testing to do
        gl.glDepthFunc(GL10.GL_LEQUAL);
        //Really nice perspectiva calculations
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT,GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //sets the current view port to the new size
        gl.glViewport(0, 0, width, height);
        //select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //reset the projection matrix
        gl.glLoadIdentity();
        //calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 1000.0f);
        //select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        //reset the modelview matrix
        gl.glLoadIdentity();
    }
    @Override
    public void onDrawFrame(GL10 gl){
        // Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        // Translates 4 units into the screen.
        gl.glTranslatef(0, 0, -4);
        // Draw our scene.
        root.draw(gl);
    }
    public void addMesh(Mesh mesh) {
        root.add(mesh);
    }
    /*
    @Override
    public void onDrawFrame(GL10 gl) {
// Clears the screen and depth buffer.
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // Replace the current matrix with the identity matrix
        gl.glLoadIdentity();
        // Translates 10 units into the screen.
        gl.glTranslatef(0, 0, -10);

        // SQUARE A
        // Save the current matrix.
        gl.glPushMatrix();
        // Rotate square A counter-clockwise.
        gl.glRotatef(angle, 0, 0, 1);
        // Draw square A.
        square.draw(gl);
        // Restore the last matrix.
        gl.glPopMatrix();

        // SQUARE B
        // Save the current matrix
        gl.glPushMatrix();
        // Rotate square B before moving it, making it rotate around A.
        gl.glRotatef(-angle, 0, 0, 1);
        // Move square B.
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square A
        gl.glScalef(.5f, .5f, .5f);
        // Draw square B.
        square.draw(gl);

        // SQUARE C
        // Save the current matrix
        gl.glPushMatrix();
        // Make the rotation around B
        gl.glRotatef(-angle, 0, 0, 1);
        gl.glTranslatef(2, 0, 0);
        // Scale it to 50% of square B
        gl.glScalef(.5f, .5f, .5f);
        // Rotate around it's own center.
        gl.glRotatef(angle*10, 0, 0, 1);
        // Draw square C.
        square.draw(gl);

        // Restore to the matrix as it was before C.
        gl.glPopMatrix();
        // Restore to the matrix as it was before B.
        gl.glPopMatrix();

        // Increse the angle.
        angle++;
    }*/
}
