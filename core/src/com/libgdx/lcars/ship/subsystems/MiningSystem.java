package com.libgdx.lcars.ship.subsystems;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.Readout.Readout;
import com.libgdx.lcars.SpaceTravel.Planet;
import com.libgdx.lcars.ship.cargosystem.Cargo;

public class MiningSystem {
    private Cargo c;
    private boolean active = false;
    private float mineSpeed = 10f;

    private Panel mainControlPanel;

    public MiningSystem(Cargo c) {
        this.c = c;
        mainControlPanel = new Panel(1000 - 150 - 10, 30, 160, 369 - 30, new Vector2(1, 4));
    }

    public void minePlanet(Planet p) {
        if (p.hasResources() && c.getCurrentStorage() < c.getMaxStorage() && active) {
            if (p.getAlloys().getItemCount() > 0) {
                c.addItemsContinuous(c.getRepairAlloys(), mineSpeed);
                p.getAlloys().removeItemsContinuous(mineSpeed);
            }
            if (p.getWarpFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getWarpFuel(), mineSpeed);
                p.getWarpFuel().removeItemsContinuous(mineSpeed);
            }
            if (p.getImpulseFuel().getItemCount() > 0) {
                c.addItemsContinuous(c.getImpulseFuel(), mineSpeed);
                p.getImpulseFuel().removeItemsContinuous(mineSpeed);
            }
        }
    }

    public void renderMiningSystem(Readout r, Sound click, boolean pMousePressed) {
        r.displayText(String.valueOf(active), 10, 10);
        mainControlPanel.render(r);
        if (mainControlPanel.Button(click, new Vector2(0, 0), pMousePressed)) {
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
