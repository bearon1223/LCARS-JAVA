package com.libgdx.lcars.SpaceTravel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargosystem.Alloys;
import com.libgdx.lcars.ship.cargosystem.ImpulseFuel;
import com.libgdx.lcars.ship.cargosystem.WarpFuel;

import static com.badlogic.gdx.math.MathUtils.map;

public class Planet {
    public int size;
    private int id;
    public Ship ship;
    public Ship playerShip;
    public String name;
    public boolean hasShip = false;
    protected Alloys planetAlloys;
    protected WarpFuel dilithium;
    protected ImpulseFuel deuterium;

    public Planet(Ship s, int id, int size, String name) {
        this.size = size;
        this.id = id;
        this.name = name;
        if (Math.round(MathUtils.random(0, 3)) == 1) {
            hasShip = true;
            ship = new Ship(false);
        }
        playerShip = s;
        planetAlloys = new Alloys((int) (Math.floor(MathUtils.random(100, 300))));
        dilithium = new WarpFuel((int) (Math.floor(MathUtils.random(10, 50))));
        deuterium = new ImpulseFuel((int) (Math.floor(MathUtils.random(20, 80))));
    }

    public Planet(Ship s, int id) {
        this(s, id, MathUtils.random(15, 23),
                PlanetNames.randomName[(int) (MathUtils.random(0, PlanetNames.randomName.length - 1))]);
    }

    public Alloys getAlloys() {
        return planetAlloys;
    }

    public WarpFuel getWarpFuel() {
        return dilithium;
    }

    public ImpulseFuel getImpulseFuel() {
        return deuterium;
    }

    public boolean hasResources() {
        return (planetAlloys.getItemCount() > 0 || dilithium.getItemCount() > 0 || deuterium.getItemCount() > 0);
    }

    public void render(MyShapeRenderer renderer, float yCoord) {
        if (size < 20)
            renderer.setColor(Color.valueOf("#AFAFAFFF"));
        else if (size < 30)
            renderer.setColor(Color.valueOf("#35DCEDFF"));
        else
            renderer.setColor(Color.valueOf("#F5D18FFF"));
        // noStroke();
        if (hasShip) {
            if (id == 0)
                renderer.ellipse(450, yCoord - size / 2, size, size);
            if (id == 1)
                renderer.ellipse(490, yCoord - size / 2, size, size);
            if (id == 2)
                renderer.ellipse(540, yCoord - size / 2, size, size);
            if (id == 3)
                renderer.ellipse(590, yCoord - size / 2, size, size);
            renderer.setColor(Color.WHITE);
            if (id == 0)
                renderer.ellipse(450 + size / 2, yCoord - 3 / 2, 3, 3);
            if (id == 1)
                renderer.ellipse(490 + size / 2, yCoord - 3 / 2, 3, 3);
            if (id == 2)
                renderer.ellipse(540 + size / 2, yCoord - 3 / 2, 3, 3);
            if (id == 3)
                renderer.ellipse(590 + size / 2, yCoord - 3 / 2, 3, 3);
        } else {
            if (id == 0)
                renderer.ellipse(450, yCoord - size / 2, size, size);
            if (id == 1)
                renderer.ellipse(490, yCoord - size / 2, size, size);
            if (id == 2)
                renderer.ellipse(540, yCoord - size / 2, size, size);
            if (id == 3)
                renderer.ellipse(590, yCoord - size / 2, size, size);
        }

    }

    public void renderPlanetSystem(MyShapeRenderer renderer, Vector2 tDloc, Vector2 tDsize, float yCoord) {
        if (size < 20)
            renderer.setColor(Color.valueOf("#AFAFAFFF"));
        else if (size < 30)
            renderer.setColor(Color.valueOf("#35DCEDFF"));
        else
            renderer.setColor(Color.valueOf("#F5D18FFF"));

        renderer.ellipse((tDloc.x + tDsize.x / 2) - (size * 2), yCoord - (size * 2), size * 4, size * 4);

        if (ship != null) {
            renderer.setColor(Color.WHITE);
            renderer.circle(tDloc.x+map(0, 100, 0, tDsize.x, ship.coordinates.y), tDloc.y+map(0, 100, 0, tDsize.y, ship.coordinates.z), 5);
            ship.update();
        }

        if(playerShip.coordinates.x == id) {
            renderer.setColor(Color.WHITE);
            renderer.circle(tDloc.x+map(0, 100, 0, tDsize.x, playerShip.coordinates.y), tDloc.y+map(0, 100, 0, tDsize.y, playerShip.coordinates.z), 5);
        }
    }

    public int getID() {
        return id;
    }

    public void update() {
        if (ship != null) {
            ship.update();
        }
    }
}
