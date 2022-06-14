package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Item {
    protected final int perItemVolume;
    protected final int perItemWeight;
    protected float itemCount;
    protected Texture texture;

    public Item(FileHandle file, int perItemVolume, int perItemWeight, int startingCount) {
        this.perItemVolume = perItemVolume;
        this.perItemWeight = perItemWeight;
        itemCount = startingCount;
        this.texture = new Texture(file);
    }

    public float getTotalWeight() {
        return itemCount * perItemWeight;
    }

    public float getTotalVolume() {
        return itemCount * perItemVolume;
    }

    public float getItemCount() {
        return itemCount;
    }

    public void addItems(float amount) {
        itemCount += (amount * Gdx.graphics.getDeltaTime());
    }

    public void removeItems(float amount) {
        itemCount -= (amount * Gdx.graphics.getDeltaTime());
    }

    public void render(SpriteBatch batch, float x, float y, float w, float h) {
        // TODO: add Render code
        batch.draw(texture, x, y, w, h);
    }
}
