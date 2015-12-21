package com.example.glup.testopengl.mesh;

/**
 * Created by Glup on 21/12/15.
 */
public class Plane extends Mesh {
    public Plane(){
        this(1,1,1,1);
    }
    public Plane(float width,float height){
        this(width,height,1,1);
    }
    public Plane(float width,float height,int widthSegments,int heightSegments){
        //por la primita GL_TRIANGLES para 2 triangulos dentro del cuadrado
        float[] vertices = new float[(widthSegments+1)*(heightSegments+1)*3];
        short[] indices = new short[(widthSegments+1)*(heightSegments+1)*6];

        float xOffset = width/-2;
        float yOffset = height/-2;
        float xWidth = width/(widthSegments);
        float yHeight = height/(heightSegments);
        int currentVertex=0;
        int currentIndex=0;
        short w = (short) (widthSegments+1);
        for (int y=0;y<heightSegments+1;y++){
            for (int x=0;x<widthSegments+1;y++){
                vertices[currentVertex] = xOffset + x * xWidth;
                vertices[currentVertex + 1] = yOffset + y * yHeight;
                vertices[currentVertex + 2] = 0;
                currentVertex += 3;

                int n = y * (widthSegments + 1) + x;

                if (y < heightSegments && x < widthSegments) {
                    // Face one
                    indices[currentIndex] = (short) n;
                    indices[currentIndex + 1] = (short) (n + 1);
                    indices[currentIndex + 2] = (short) (n + w);
                    // Face two
                    indices[currentIndex + 3] = (short) (n + 1);
                    indices[currentIndex + 4] = (short) (n + 1 + w);
                    indices[currentIndex + 5] = (short) (n + 1 + w - 1);

                    currentIndex += 6;
                }

            }
        }
        setIndices(indices);
        setVertices(vertices);
    }
    /*
    public Cone(float baseRadius, float topRadius, float height, int numberOfSides)

    public class Pyramid extends Cone {
        public Pyramid(float baseRadius, float height)  {
            super(baseRadius, 0, height, 4);
        }
    }

    public class Cylinder extends Cone {
        public Cylinder(float radius, float height)  {
            super(radius, radius, height, 16);
        }
    }*/
}
