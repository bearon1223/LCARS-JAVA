package com.libgdx.lcars;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

class RectHolder {
    float x, y, z, w, h;
    Color c;
    
    RectHolder(Color c, float x, float y, float z, float w, float h) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        this.h = h;
        this.c = c;
    }
}

class TextureHolder {
    Texture texture;
    float x, y, w, h;
    TextureHolder(Texture texture, float x, float y, float w, float h){
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}