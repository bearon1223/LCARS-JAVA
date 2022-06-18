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
    private Ship s;

    public MiningSystem(Ship s, Cargo c) {
        this.c = c;
        this.s = s;
        mainControlPanel = new Panel(1000 - 150 - 10, 30, 160, 369 - 30, new Vector2(1, 4));
        mainControlPanel.rename("Toggle Miner", 0, 0);
    }

    public void minePlanet(Planet p) {
        if (p.hasResources() && c.getCurrentStorage() < c.getMaxStorage() && active) {
            if (p.getAlloys().getItemCount() > 0) {
                c.addItemsContinuous(c.getRepairAlloys(), mineSpeed);
                p.getAlloys().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / 2);
            }
            if (p.getWarpFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getWarpFuel(), mineSpeed);
                p.getWarpFuel().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / 2);
            }
            if (p.getImpulseFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getImpulseFuel(), mineSpeed);
                p.getImpulseFuel().removeItemsContinuous(mineSpeed);
                c.addItemsContinuous(c.getWaste(), mineSpeed / 2);
            }
        }
        if (s.isTravelingImpulse || s.isTravelingWarp) {
            active = false;
        }
    }

    public void renderMiningSystem(Readout r, Sound click, boolean pMousePressed) {
        r.displayText(String.valueOf(active), 10, 10);
        mainControlPanel.render(r);
        if (active)
            mainControlPanel.rename("Miner Status: \nACTIVE", 0, 1);
        else
            mainControlPanel.rename("Miner Status: \nINACTIVE", 0, 1);
        mainControlPanel.rename("Mine Speed: " + String.valueOf(mineSpeed) + " items/sec", 0, 2);
        mainControlPanel.textRenderer(r, 1f);

        if (mainControlPanel.Button(click, new Vector2(0, 3), pMousePressed)) {
            toggleActive();
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
