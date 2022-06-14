package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.TextArrays;
import com.libgdx.lcars.Readout.Readout;
import static com.badlogic.gdx.math.MathUtils.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Cargo {
    private WarpFuel warpFuel;
    private ImpulseFuel impulseFuel;
    private float maxStorage;
    private float currentStorage;

    private Panel cargoSelection;
    private CargoPanel itemSelection;

    private float[] itemValues = new float[9];

    public Cargo(float maxStorage, boolean isPlayer) {
        this.maxStorage = maxStorage;
        warpFuel = new WarpFuel();
        impulseFuel = new ImpulseFuel();
        if (!isPlayer) {
            warpFuel.addItems(9999);
            impulseFuel.addItems(9999);
        }
        cargoSelection = new Panel(1000 - 150 - 10, 30, 160, 369 - 30, new Vector2(1, 4))
                .addNames(TextArrays.cargoSelectionNames);

        itemValues[0] = warpFuel.getItemCount();
        itemValues[1] = impulseFuel.getItemCount();
        itemValues[2] = 0;
        itemValues[3] = 0;
        itemValues[4] = 0;
        itemValues[5] = 0;
        itemValues[6] = 0;
        itemValues[7] = 0;
        itemValues[8] = 0;

        Texture[] textures = {
                warpFuel.texture,
                impulseFuel.texture,
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
                new Texture(Gdx.files.internal("icon.png")),
        };

        String[][] names = {
                { warpFuel.getName() },
                { impulseFuel.getName() },
                { "Empty" },
                { "Empty" },
                { "Empty" },
                { "Empty" },
                { "Empty" },
                { "Empty" },
                { "Empty" },
        };

        itemSelection = new CargoPanel(textures, 170 + 110 + 110, 60, 450, 369 - 60, 9).addNames(names);
    }

    public WarpFuel getWarpFuel() {
        return warpFuel;
    }

    public ImpulseFuel getImpulseFuel() {
        return impulseFuel;
    }

    public float getCurrentStorage() {
        return currentStorage;
    }

    public float getMaxStorage() {
        return maxStorage;
    }

    public void update() {
        itemValues[0] = warpFuel.getItemCount();
        itemValues[1] = impulseFuel.getItemCount();
        itemValues[2] = 0;
        itemValues[3] = 0;
        itemValues[4] = 0;
        itemValues[5] = 0;
        itemValues[6] = 0;
        itemValues[7] = 0;
        itemValues[8] = 0;

        currentStorage = 0;
        for (int i = 0; i < itemValues.length; i++) {
            currentStorage += itemValues[i];
        }
    }

    public void addItem(Item item, float amount) {
        if (currentStorage + amount < maxStorage)
            item.addItems(amount);
        else
            System.out.println("Error: Not enough Storage");
    }

    public void removeItem(Item item, float amount) {
        if (item.itemCount - amount >= 0)
            item.removeItems(amount);
        else
            System.out.println("Error: Not enough Items");
    }

    public void render(MyShapeRenderer renderer, Sound click, boolean pMousePressed, Readout r) {
        cargoSelection.render(renderer, 0, false);
        cargoSelection.textRenderer(r, 1);
        itemSelection.render(r);
        itemSelection.textRenderer(r, itemValues);
        r.displayText("Total Used: " + (Math.round(currentStorage / maxStorage * 10000) / 100f) + "%, "
                + "Used Volume: " + Math.round(currentStorage) + "L, Remaining Volume: "
                + Math.round(maxStorage - currentStorage)+"L", 10, 40);
        r.rect(new Color(0.4f, 0.4f, 1f, 1f), 10, 0, r.w - 20, 20);
        r.rect(new Color(0.7f, 0.7f, 1f, 1f), 10, 0, map(0, maxStorage, 0, r.w - 20, currentStorage), 20);
    }

    public void dispose() {
        warpFuel.dispose();
        impulseFuel.dispose();
    }
}
