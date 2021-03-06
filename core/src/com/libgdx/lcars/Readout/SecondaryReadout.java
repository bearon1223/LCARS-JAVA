package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SecondaryReadout extends Readout {
    private Texture schematic;

    public SecondaryReadout(float x, float y, float w, float h) {
        super(x, y, w, h);
        schematic = new Texture(Gdx.files.internal("schematic.png"));
    }

    public void render(SpriteBatch batch) {
        image(schematic, x, y, w, h);
        batchRenderer(batch, null, false);
    }

    @Override
    public void dispose() {
        super.dispose();
        schematic.dispose();
    }
}
