package com.libgdx.lcars.SpaceTravel;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.longVector;
import com.libgdx.lcars.Readout.Starchart;

import static com.badlogic.gdx.math.MathUtils.map;

public class Starsystem {
    public Sector s;
    public Planet[] p;
    public Vector2 loc;
    public int id, r = 15, planetAmount;
    private longVector limits;

    public Starsystem(Sector s, Vector2 loc, int id, int planetAmount) {
        p = new Planet[planetAmount];
        limits = new longVector(30, 228 - 30, 30, 173 - 40);
        this.s = s;
        r = Math.round(MathUtils.random(10, 20));
        this.planetAmount = planetAmount;
        this.loc = new Vector2(loc.x + s.tacticalDisplayLoc.x, loc.y + s.tacticalDisplayLoc.y);
        this.id = id;
        for (int i = 0; i < planetAmount; i++) {
            p[i] = new Planet(i);
        }
    }

    public Starsystem(Sector s, Vector2 loc, int id) {
        this(s, loc, id, Math.round(MathUtils.random(2, 4)));
    }

    public void chooseColor(MyShapeRenderer renderer) {
        if (r < 14)
            renderer.setColor(255, 50, 50, 255);
        else if (r < 18)
            renderer.setColor(255, 255, 0, 255);
        else
            renderer.setColor(100, 100, 255, 255);
    }

    public Planet getPlanet(int id) {
        return p[MathUtils.clamp(id, 0, planetAmount - 1)];
    }

    public void renderSystem(MyShapeRenderer renderer) {
        chooseColor(renderer);
        renderer.ellipse(loc.x, loc.y, r, r);
    }

    public void renderSystemTiny(MyShapeRenderer renderer, Starchart t, Vector2 offset, Vector2 arrayID) {
        // random(10, w-10), random(30, h-40);w=228, h=173
        // if(arrayID.x==1 && arrayID.y == 1) println(loc.x);
        chooseColor(renderer);
        renderer.ellipse(t.x + ((offset.x) * (arrayID.x)) + map(0, limits.y, 3, 37, loc.x - t.x),
                t.y + ((offset.y) * (arrayID.y)) + map(0, limits.w, 3, 37, loc.y - t.y),
                map(0, 20, 0, 5, r), map(0, 20, 0, 5, r));
    }

    public void renderPlanets(MyShapeRenderer renderer, float yCoord) {
        chooseColor(renderer);
        renderer.arc(410, yCoord, r * 2.133f, -90, 180, 10);
        for (int i = 0; i < planetAmount; i++) {
            p[i].render(renderer, yCoord);
        }
        renderer.setColor(255, 255, 255, 255);
    }

    public void displayText(String text, float x, float y, float w, float h) {
        // text(text, ezMap(x, true), ezMap(y, false), ezMap(w, true), ezMap(h, false));
    }

    public float distanceSystem(Vector2 startingLoc) {
        return (float) Math.abs(Math.hypot(startingLoc.x - loc.x, startingLoc.y - loc.y));
    }

}
