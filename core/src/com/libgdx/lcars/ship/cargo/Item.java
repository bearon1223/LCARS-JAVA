package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Item {
    protected final int perItemVolume;
    protected final int perItemWeight;
    protected float itemCount;
    protected Texture texture;
    protected String name;

    public Item(FileHandle file, String name, int perItemVolume, int perItemWeight, int startingCount) {
        this.perItemVolume = perItemVolume;
        this.perItemWeight = perItemWeight;
        itemCount = startingCount;
        this.texture = new Texture(file);
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void dispose() {
        texture.dispose();
    }
}
