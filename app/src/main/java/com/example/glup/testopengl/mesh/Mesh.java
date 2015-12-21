package com.example.glup.testopengl.mesh;

import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Glup on 21/12/15.
 */
public class Mesh {
    private FloatBuffer verticesBuffer=null;
    private ShortBuffer indicesBuffer=null;
    private int numOfIndices = -1;
    private float[] rgba=new float[]{1.0f,1.0f,1.0f,1.0f};
    private  FloatBuffer colorBuffer = null;//smooth color
    // Translate params.
    public float x = 0;
    public float y = 0;
    public float z = 0;
    // Rotate params.
    public float rx = 0;
    public float ry = 0;
    public float rz = 0;
    //la UV textura buffer
    private FloatBuffer textureBuffer;
    //la textura id
    private int textureId = -1;
    //el bitmap que se desea cargar como textura
    private Bitmap bitmap;
    //indicador si cargo la textura
    private boolean loadTexture=false;

    public void draw(GL10 gl){
        //fijar direccion de dibujo
        gl.glFrontFace(GL10.GL_CCW);
        //ocultar y mostrar solo las caras visibles
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        //señalar y dar espacio de memoria para vertices
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);
        //asignar color flat
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);
        //asignar un espacio adicional para smooth color
        if (colorBuffer!=null){
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4,GL10.GL_FLOAT,0,colorBuffer);
        }
        //begin new part
        //ver el indicador de carga de textura
        if (loadTexture){
            loadGLTexture(gl);
            loadTexture=false;
        }
        if (textureId != -1 && textureBuffer != null){
            gl.glEnable(GL10.GL_TEXTURE_2D);
            //habilitar el estado de la textura
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            //point to our buffers
            gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textureBuffer);
        }
        //end new part
        //transformacionrs geometricas
        gl.glTranslatef(x, y, z);
        gl.glRotatef(rx, 1, 0, 0);
        gl.glRotatef(ry, 0, 1, 0);
        gl.glRotatef(rz, 0, 0, 1);
        //señalar el espacio de memoria de indices y dibujar
        gl.glDrawElements(GL10.GL_TRIANGLES,numOfIndices,GL10.GL_UNSIGNED_SHORT,indicesBuffer);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // New part...
        if (textureId != -1 && textureBuffer != null) {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        }
        // ... end new part.
        gl.glDisable(GL10.GL_CULL_FACE);
    }
    //aun falta setear el espacio en memoria de vertices e indices
    protected void setVertices(float[] vertices){
        //un valor float tiene 4 bytes
        Log.e("lengthVer",vertices.length+"");
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        verticesBuffer = vbb.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
    }
    protected void setIndices(short[] indices){
        //una variable short son 2 bytes
        ByteBuffer ibb=ByteBuffer.allocateDirect(indices.length * 2);
        ibb.order(ByteOrder.nativeOrder());
        indicesBuffer = ibb.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        numOfIndices = indices.length;
    }
    protected void setColor(float red,float green, float blue,float alpha){
        rgba[0] = red;rgba[1]=green;rgba[2]=blue;rgba[3]=alpha;
    }
    protected void setColors(float[] colors){
        ByteBuffer byteBuffer=ByteBuffer.allocateDirect(colors.length*4);
        byteBuffer.order(ByteOrder.nativeOrder());
        colorBuffer = byteBuffer.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }


    public void loadBitmap(Bitmap bitmap) { // New function.
        this.bitmap = bitmap;
       loadTexture = true;
    }
    private void loadGLTexture(GL10 gl) { // New function
        // Generate one texture pointer...
        int[] textures = new int[1];
        gl.glGenTextures(1, textures, 0);
        textureId = textures[0];

        // ...and bind it to our array
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);

        // Create Nearest Filtered Texture
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                GL10.GL_LINEAR);

        // Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
                GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
                GL10.GL_REPEAT);

        // Use the Android GLUtils to specify a two-dimensional texture image
        // from our bitmap
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
    }
    protected void setTextureCoordinates(float[] textureCoords) { // New
        // function.
        // float is 4 bytes, therefore we multiply the number if
        // vertices with 4.
        ByteBuffer byteBuf = ByteBuffer
                .allocateDirect(textureCoords.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuf.asFloatBuffer();
        textureBuffer.put(textureCoords);
        textureBuffer.position(0);
    }

}
