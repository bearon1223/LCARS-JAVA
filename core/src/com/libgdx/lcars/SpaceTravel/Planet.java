package com.libgdx.lcars.SpaceTravel;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.ship.Ship;

public class Planet {
    public int size;
    int id;
    Ship[] ship;

    public Planet(int id, int size) {
        this.size = size;
        this.id = id;
    }

    public Planet(int id) {
        this(id, MathUtils.random(15, 23));
    }

    public void render(MyShapeRenderer renderer, float yCoord) {
        if (size < 20) renderer.setColor(Color.valueOf("#AFAFAFFF"));
        else if (size < 30) renderer.setColor(Color.valueOf("#35DCEDFF"));
        else renderer.setColor(Color.valueOf("#F5D18FFF"));
        // noStroke();
        if (id == 0)
            renderer.ellipse(450, yCoord, size, size);
        if (id == 1)
            renderer.ellipse(490, yCoord, size, size);
        if (id == 2)
            renderer.ellipse(540, yCoord, size, size);
        if (id == 3)
            renderer.ellipse(590, yCoord, size, size);
    }

    public void renderPlanetSystem(MyShapeRenderer renderer, Vector2 tDloc, Vector2 tDsize, float yCoord) {
        if (size < 20) renderer.setColor(Color.valueOf("#AFAFAFFF"));
        else if (size < 30) renderer.setColor(Color.valueOf("#35DCEDFF"));
        else renderer.setColor(Color.valueOf("#F5D18FFF"));
        // noStroke();
        renderer.ellipse(tDloc.x + tDsize.x / 2, yCoord, size * 2, size * 2);

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

    public void update() {
        // shipTest.update();
        // if (shipTest.isAtCoords(shipTest.targetCoords)) shipTest.targetCoords =
        // shipTest.pickPoint(id, false);
        // shipTest.goToCoords(shipTest.targetCoords);
    }
}
