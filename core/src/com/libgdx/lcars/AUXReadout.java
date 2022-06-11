package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AUXReadout extends Readout {

    private Texture standbyTexture;
    private Texture surroundTexture;

    public AUXReadout(float x, float y, float w, float h) {
        super(x, y, w, h);
        standbyTexture = new Texture(Gdx.files.internal("Federation Logo.jpg"));
        surroundTexture = new Texture(Gdx.files.internal("Image Surroundings.png"));
    }

    public void render(SpriteBatch renderer) {
        renderer.draw(surroundTexture, x, y, w, h);
        renderer.draw(standbyTexture, x + 15, y + 15, w - 30, h - 30);
    }

    @Override
    public void dispose() {
        super.dispose();
        standbyTexture.dispose();
        surroundTexture.dispose();
    }
}
