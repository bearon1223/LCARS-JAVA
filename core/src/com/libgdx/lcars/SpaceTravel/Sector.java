package com.libgdx.lcars.SpaceTravel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Readout.Starchart;
import com.libgdx.lcars.ship.Ship;

public class Sector {
    private Starsystem[] s;
    public int id;
    public int systemAmount;
    public Vector2 tacticalDisplayLoc, arrayID;

    public Sector(Ship ship, Vector2 tacticalDisplayLoc, Vector2 arrayID, int id, int systemAmount, Vector2[] systemCoords) {
        this.id = id;
        this.arrayID = arrayID;
        this.systemAmount = systemAmount;
        this.tacticalDisplayLoc = tacticalDisplayLoc;
        s = new Starsystem[systemAmount];
        for (int i = 0; i < systemAmount; i++) {
            s[i] = new Starsystem(ship, this, systemCoords[i], i);
        }
    }
    public Sector(Ship ship, Vector2 tacticalDisplayLoc, Vector2 arrayID, int id, Vector2[] systemCoords) {
        this(ship, tacticalDisplayLoc, arrayID, id, Math.round(MathUtils.random(3, 7)), systemCoords);
    }

    public Starsystem getSystem(int i) {
        return s[i];
    }

    public void renderSector(MyShapeRenderer renderer) {
        for (int i = 0; i < systemAmount; i++) {
            s[i].renderSystem(renderer);
        }
    }

    public void renderTiny(MyShapeRenderer renderer, Starchart t, Vector2 offset) {
        for (int i = 0; i < systemAmount; i++) {
            s[i].renderSystemTiny(renderer, t, offset, arrayID);
        }
    }

    public float distanceSector(Vector2 fromID) {
        return (float) Math.abs(Math.hypot(fromID.x - arrayID.x, fromID.y - arrayID.y));

    }
}
