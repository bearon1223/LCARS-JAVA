package com.libgdx.lcars.ship.cargosystem;

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
    private Alloys repairAlloys;
    private Item latinum;
    private Item supplies;
    private Item emptyItem3;
    private Item emptyItem4;
    private Item emptyItem5;
    private Item waste;

    private float maxStorage;
    private float currentStorage;
    private int selectedItem = 1;

    private Panel cargoSelection;
    private CargoPanel itemSelection;

    private float[] itemValues = new float[9];

    private Item[] items = new Item[9];

    public Cargo(float maxStorage, boolean isPlayer) {
        this.maxStorage = maxStorage;
        warpFuel = new WarpFuel();
        impulseFuel = new ImpulseFuel();
        repairAlloys = new Alloys();
        latinum = new Item(Gdx.files.internal("icon.png"), "Latinum", 1, 5, 10, 1);
        supplies = new Item(Gdx.files.internal("icon.png"), "Supplies", 4, 2, 20, 0.5f);
        emptyItem3 = new Item(Gdx.files.internal("icon.png"), "Empty", 0, 0, 0, 0);
        emptyItem4 = new Item(Gdx.files.internal("icon.png"), "Empty", 0, 0, 0, 0);
        emptyItem5 = new Item(Gdx.files.internal("icon.png"), "Empty", 0, 0, 0, 0);
        waste = new Item(Gdx.files.internal("Used Fuel.png"), "Waste", 5, 1, 0, 0);
        // Volume in Liters, Mass in Kilograms, Starting Amount

        if (!isPlayer) {
            warpFuel.addItems(9999);
            impulseFuel.addItems(9999);
        }
        cargoSelection = new Panel(1000 - 150 - 10, 30, 160, 369 - 30, new Vector2(1, 4))
                .addNames(TextArrays.cargoSelectionNames);

        itemValues[0] = warpFuel.getItemCount();
        itemValues[1] = impulseFuel.getItemCount();
        itemValues[2] = repairAlloys.getItemCount();
        itemValues[3] = latinum.getItemCount();
        itemValues[4] = supplies.getItemCount();
        itemValues[5] = emptyItem3.getItemCount();
        itemValues[6] = emptyItem4.getItemCount();
        itemValues[7] = emptyItem5.getItemCount();
        itemValues[8] = waste.getItemCount();

        items[0] = warpFuel;
        items[1] = impulseFuel;
        items[2] = repairAlloys;
        items[3] = latinum;
        items[4] = supplies;
        items[5] = emptyItem3;
        items[6] = emptyItem4;
        items[7] = emptyItem5;
        items[8] = waste;

        Texture[] textures = {
                warpFuel.texture,
                impulseFuel.texture,
                repairAlloys.texture,
                latinum.texture,
                supplies.texture,
                emptyItem3.texture,
                emptyItem4.texture,
                emptyItem5.texture,
                waste.texture
        };

        String[][] names = {
                { warpFuel.getName() },
                { impulseFuel.getName() },
                { repairAlloys.getName() },
                { latinum.getName() },
                { supplies.getName() },
                { emptyItem3.getName() },
                { emptyItem4.getName() },
                { emptyItem5.getName() },
                { waste.getName() },
        };

        itemSelection = new CargoPanel(textures, 170 + 110 + 110, 60, 450, 369 - 60, 9).addNames(names);

        itemSelection.setItem(warpFuel, 0);
        itemSelection.setItem(impulseFuel, 1);
        itemSelection.setItem(repairAlloys, 2);
        itemSelection.setItem(latinum, 3);
        itemSelection.setItem(supplies, 4);
        itemSelection.setItem(emptyItem3, 5);
        itemSelection.setItem(emptyItem4, 6);
        itemSelection.setItem(emptyItem5, 7);
        itemSelection.setItem(waste, 8);
    }

    public WarpFuel getWarpFuel() {
        return warpFuel;
    }

    public ImpulseFuel getImpulseFuel() {
        return impulseFuel;
    }

    public Alloys getRepairAlloys() {
        return repairAlloys;
    }

    public Item getLatinum() {
        return latinum;
    }

    public Item getSupplies() {
        return supplies;
    }

    public Item getWaste() {
        return waste;
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
        itemValues[2] = repairAlloys.getItemCount();
        itemValues[3] = latinum.getItemCount();
        itemValues[4] = supplies.getItemCount();
        itemValues[5] = emptyItem3.getItemCount();
        itemValues[6] = emptyItem4.getItemCount();
        itemValues[7] = emptyItem5.getItemCount();
        itemValues[8] = waste.getItemCount();

        currentStorage = 0;
        for (int i = 0; i < itemValues.length; i++) {
            currentStorage += itemValues[i];
        }
    }

    public void addItem(Item item, float amount) {
        if (currentStorage + amount <= maxStorage)
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

    public void addItemsContinuous(Item item, float amount) {
        if (currentStorage + amount <= maxStorage)
            item.addItemsContinuous(amount);
        else
            System.out.println("Error: Not enough Storage");
    }

    public void removeItemsContinuous(Item item, float amount) {
        if (item.itemCount - amount >= 0)
            item.removeItemsContinuous(amount);
        else
            System.out.println("Error: Not enough Items");
    }

    public void render(MyShapeRenderer renderer, Sound click, boolean pMousePressed, Readout r) {
        cargoSelection.render(renderer, 0, false);
        cargoSelection.textRenderer(r, 1);
        itemSelection.render(r);
        itemSelection.textRenderer(r, items);
        itemSelection.setSelected(selectedItem);

        for (int i = 0; i < 9; i++) {
            if (itemSelection.Button(click, new Vector2(0, i), pMousePressed))
                selectedItem = 9 - i;
        }

        int amount = 50;
        if (cargoSelection.Button(click, new Vector2(0, 3), pMousePressed)
                && itemSelection.getItem(selectedItem - 1).itemCount - amount >= 0) {
            if (latinum.getItemCount() > 0.2f) {
                removeItem(itemSelection.getItem(selectedItem - 1), amount);
                removeItem(latinum, 0.1f);
            }
        }

        if (cargoSelection.Button(click, new Vector2(0, 2), pMousePressed)
                && itemSelection.getItem(selectedItem - 1).itemCount - amount >= 0) {
            if (latinum.getItemCount() > 0.2f) {
                removeItem(itemSelection.getItem(selectedItem - 1), amount);
                addItem(latinum, itemSelection.getItem(selectedItem - 1).perItemCost * amount);
            }
        }

        r.displayText("Total Used: " + (Math.round(currentStorage / maxStorage * 10000) / 100f) + "%, "
                + "Used Volume: " + Math.round(currentStorage) + "L, Remaining Volume: "
                + Math.round(maxStorage - currentStorage) + "L", 10, 40);
        r.rect(new Color(0.4f, 0.4f, 1f, 1f), 10, 0, r.w - 20, 20);
        r.rect(new Color(0.7f, 0.7f, 1f, 1f), 10, 0, map(0, maxStorage, 0, r.w - 20, currentStorage), 20);
    }

    public void dispose() {
        warpFuel.dispose();
        impulseFuel.dispose();
        repairAlloys.dispose();
        latinum.dispose();
        supplies.dispose();
        emptyItem3.dispose();
        emptyItem4.dispose();
        emptyItem5.dispose();
        waste.dispose();
    }
}
