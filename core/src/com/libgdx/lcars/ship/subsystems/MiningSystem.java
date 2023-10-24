package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.Readout.Readout;
import com.libgdx.lcars.SpaceTravel.Planet;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargosystem.Cargo;

public class MiningSystem {
    private Cargo c;
    private boolean active = false;
    private float mineSpeed = 2.5f;

    private Panel mainControlPanel;
    private Panel planetResources;
    private Ship s;

    private float[] items = { 0, 0, 0 };

    private int miningSpeedSelection = 0;
    private float efficiency = 1;

    public MiningSystem(Ship s, Cargo c) {
        this.c = c;
        this.s = s;
        mainControlPanel = new Panel(1000 - 150 - 10, 30, 160, 369 - 30, new Vector2(1, 4));
        mainControlPanel.rename("Toggle Miner", 0, 0);
        planetResources = new Panel(170 + 110 + 110, 5, 300, 75, new Vector2(1, 3));
    }

    public void setMiningViewer(Planet p) {
        // get the planet and set the items into the proper locations.
        items[0] = p.getAlloys().getItemCount();
        items[1] = p.getWarpFuel().getItemCount();
        items[2] = p.getImpulseFuel().getItemCount();
    }

    public void minePlanet(Planet p) {
        // Get the resources and remove the items from the planet while adding some to
        // the cargo bays
        items[0] = p.getAlloys().getItemCount();
        items[1] = p.getWarpFuel().getItemCount();
        items[2] = p.getImpulseFuel().getItemCount();

        if (p.hasResources() && c.getCurrentStorage() < c.getMaxStorage() && active) {
            if (p.getAlloys().getItemCount() > 0) {
                c.addItemsContinuous(c.getRepairAlloys(), mineSpeed*efficiency);
                p.getAlloys().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / 2);
            }
            if (p.getWarpFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getWarpFuel(), mineSpeed*efficiency);
                p.getWarpFuel().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / 2);
            }
            if (p.getImpulseFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getImpulseFuel(), mineSpeed*efficiency);
                p.getImpulseFuel().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / (2*efficiency));
            }
        }
        if (s.isTravelingImpulse || s.isTravelingWarp) {
            active = false;
        }
    }

    public void renderMiningSystem(Readout r, Sound click, boolean pMousePressed) {
        // Show status for the mining system
        // r.displayText(String.valueOf(active), 10, 10);
        mainControlPanel.render(r);

        String activeText = active ? "ACTIVE" : "INACTIVE";
        mainControlPanel.rename("Miner Status: " + activeText, 0, 1);
        mainControlPanel.rename("Mine Speed: \n" + String.valueOf(mineSpeed) + " items/sec", 0, 2);
        mainControlPanel.textRenderer(r, 1f);

        planetResources.render(r);
        String text = items[0] > 0 ? "  Alloys: " + Math.floor(items[0] * 100) / 100 + "L"
                : "  Alloys Deposits have been depleted";
        planetResources.rename(text, 0, 0);
        text = items[1] > 0 ? "  Dilithium: " + Math.floor(items[1] * 100) / 100 + "L"
                : "  Dilithium Deposits have been depleted";
        planetResources.rename(text, 0, 1);
        text = items[2] > 0 ? "  Deuterium: " + Math.floor(items[2] * 100) / 100 + "L"
                : "  Deuterium Deposits have been depleted";
        planetResources.rename(text, 0, 2);

        planetResources.textRenderer(r, 1f, -1);

        if(items[0] <= 0 && items[1] <= 0 && items[2] <= 0)
            setActive(false);

        if (mainControlPanel.Button(click, new Vector2(0, 3), pMousePressed)) {
            toggleActive();
        }

        if (mainControlPanel.Button(click, new Vector2(0, 1), pMousePressed)) {
            miningSpeedSelection++;
            switch (miningSpeedSelection) {
                case 0:
                    mineSpeed = 2.5f;
                    efficiency = 1;
                    break;
                case 1:
                    mineSpeed = 4f;
                    efficiency = 0.8f;
                    break;
                case 2:
                    mineSpeed = 5.5f;
                    efficiency = 0.5f;
                    break;
                default:
                    miningSpeedSelection = 0;
                    mineSpeed = 2.5f;
                    efficiency = 1;
                    break;
            }
        }
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void toggleActive() {
        setActive(!getActive());
    }
}
