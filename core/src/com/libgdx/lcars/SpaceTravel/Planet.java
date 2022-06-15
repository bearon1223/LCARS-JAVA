package com.libgdx.lcars.SpaceTravel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.ship.Ship;
import com.libgdx.lcars.ship.cargosystem.Alloys;
import com.libgdx.lcars.ship.cargosystem.ImpulseFuel;
import com.libgdx.lcars.ship.cargosystem.WarpFuel;

public class Planet {
    public int size;
    private int id;
    protected Ship ship;
    public String name;
    protected boolean hasShip = false;
    protected Alloys planetAlloys;
    protected WarpFuel dilithium;
    protected ImpulseFuel deuterium;

    public Planet(int id, int size, String name) {
        this.size = size;
        this.id = id;
        this.name = name;
        // if (Math.round(MathUtils.random(0, 15)) == 1) {
        //     hasShip = true;
        //     System.out.println(id + ", " + name);
        // }
        planetAlloys = new Alloys((int) (Math.floor(MathUtils.random(300, 400))));
        dilithium = new WarpFuel((int) (Math.floor(MathUtils.random(50, 150))));
        deuterium = new ImpulseFuel((int) (Math.floor(MathUtils.random(100, 300))));
    }

    public Planet(int id) {
        this(id, MathUtils.random(15, 23),
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

    public boolean hasResources(){
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

        // fill(255);
        // mapEllipse(map(shipTest.loc.y, 0, 100, tDloc.x, tDloc.x+tDsize.x),
        // map(shipTest.loc.z, 0, 100, tDloc.y, tDloc.y+tDsize.y), 5, 5);
        // mapEllipse(map(shipTest.targetCoords.y, 0, 100, tDloc.x, tDloc.x+tDsize.x),
        // map(shipTest.targetCoords.z, 0, 100, tDloc.y, tDloc.y+tDsize.y), 5, 5);

        // fill(50, 50, 255);
        // if (shipCoordinates.x == id) mapEllipse(map(shipCoordinates.y, 0, 100,
        // tDloc.x, tDloc.x+tDsize.x), map(shipCoordinates.z, 0, 100, tDloc.y,
        // tDloc.y+tDsize.y), 5, 5);
    }

    public int getID() {
        return id;
    }

    public void update() {
        // shipTest.update();
        // if (shipTest.isAtCoords(shipTest.targetCoords)) shipTest.targetCoords =
        // shipTest.pickPoint(id, false);
        // shipTest.goToCoords(shipTest.targetCoords);
    }
}
