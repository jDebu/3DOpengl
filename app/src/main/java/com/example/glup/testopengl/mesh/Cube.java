package com.example.glup.testopengl.mesh;


/**
 * Created by Glup on 21/12/15.
 */
public class Cube extends Mesh {
    public Cube(float width, float height, float depth) {
        width  /= 4;
        height /= 4;
        depth  /= 4;

        float vertices[] = { -width, -height, -depth, // 0
                width, -height, -depth, // 1
                width,  height, -depth, // 2
                -width,  height, -depth, // 3
                -width, -height,  depth, // 4
                width, -height,  depth, // 5
                width,  height,  depth, // 6
                -width,  height,  depth, // 7
        };

        short indices[] = { 0, 4, 5,
                0, 5, 1,
                1, 5, 6,
                1, 6, 2,
                2, 6, 7,
                2, 7, 3,
                3, 7, 4,
                3, 4, 0,
                4, 7, 6,
                4, 6, 5,
                3, 0, 1,
                3, 1, 2, };

        setIndices(indices);
        setVertices(vertices);
        setColor(0.5f, 0.5f, 1.0f, 1.0f);

    }
}
