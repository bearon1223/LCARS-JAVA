package com.libgdx.lcars;

import com.badlogic.gdx.utils.Array;

public class ImprovedReadout {
    Array<Vector5> coordinates;

    public ImprovedReadout() {
    }

    public void rect(int x, int y, int w, int h, int r) {
        coordinates.add(new Vector5(x, y, w, h, r));
    }

    public void batchRenderer(MyShapeRenderer renderer) {
        for (Vector5 coordinates : coordinates) {
            renderer.roundedRect(coordinates.x, coordinates.y, coordinates.z, coordinates.w, coordinates.h);
        }
    }
}

class Vector5 {
    float x, y, z, w, h;

    Vector5(float x, float y, float z, float w, float h) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
    }
}
