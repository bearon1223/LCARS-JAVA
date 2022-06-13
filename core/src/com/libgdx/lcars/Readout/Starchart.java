package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.longVector;
import com.libgdx.lcars.SpaceTravel.Sector;

public class Starchart extends Readout {
    final Vector2 imageSize = new Vector2(228, 173);

    public Sector[][] s;

    public Vector2 targetingPointLoc;
    public Vector3 selected;
    public Vector2 currentSector = new Vector2(0, 0);
    public Vector2 selectedSector = new Vector2(0, 0);

    private Texture tacticalDisplaySurroundings;

    public longVector limits;

    public boolean isClicked = false;

    Starchart(float x, float y) {
        super(x, y, 228, 173);
        limits = new longVector(30, w - 30, 30, h - 40);
        s = new Sector[5][4];
        selected = new Vector3(0, 0, 1);
        int index = 0;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 5; i++) {
                targetingPointLoc = new Vector2(imageSize.x / 2, imageSize.y / 2);
                s[i][j] = new Sector(new Vector2(x, y), new Vector2(i, j), index, randomSysCoords(7));
                index++;
            }
        }
        tacticalDisplaySurroundings = new Texture(Gdx.files.internal("Tactical Display Surroundings.png"));
    }

    private boolean within(Readout r, Vector2 vector, float x, float y, float d) {
        return (Math.abs(Math.hypot((vector.x + r.x) - (x), (vector.y + r.y) - (y))) <= d / 2);
    }

    private boolean within(Readout r, Vector2 vector, float x, float y, float w, float h) {
        if (vector.x + r.x > x && vector.y + r.y > y && vector.x + r.x < x + w && vector.y + r.y < y + h)
            return true;
        return false;
    }

    private boolean within(Vector2 vector, float x, float y, float w, float h) {
        return (vector.x > x && vector.y > y && vector.x < x + w && vector.y < y + h);
    }

    Vector3 convertIndexToVector(float index) {
        Vector3 finalConversion = new Vector3(69, 69, 69);
        if (index <= 4)
            finalConversion = new Vector3(index, 0, 0);
        else if (index <= 9)
            finalConversion = new Vector3(index - 5, 1, 0);
        else if (index <= 14)
            finalConversion = new Vector3(index - 10, 2, 0);
        else if (index <= 19)
            finalConversion = new Vector3(index - 15, 3, 0);
        else
            System.out.println("ur dum");
        return finalConversion;
    }

    void target(float x, float y, float size) {
        // stroke(#FFAD29);
        // strokeWeight(3);
        // drawLine(x-size, y, x+size, y);
        // drawLine(x, y-size, x, y+size);
        // strokeWeight(1);
        // stroke(255);
    }

    void systemMap(MyShapeRenderer renderer) {
        float yCoord = y + y / 2;
        s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) Math.floor(selected.y))
                .renderPlanets(renderer, yCoord);

        if (isClicked && within(this, targetingPointLoc, 450, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 0;
        } else if (isClicked
                && within(this, targetingPointLoc, 490, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 1;
        } else if (isClicked
                && within(this, targetingPointLoc, 540, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 2;
        } else if (isClicked
                && within(this, targetingPointLoc, 590, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 3;
        }
    }

    void systemSelect(MyShapeRenderer renderer, Vector2 index) {
        // textAlign(LEFT, TOP);
        // displayText("Selected Sector: " + floor(selected.x + 1) + ", System: " +
        // floor(selected.y + 1), 30, 0);
        // textAlign(RIGHT, BOTTOM);
        Sector sector = s[(int) index.x][(int) index.y];
        sector.renderSector(renderer);
        for (int i = 0; i < sector.systemAmount; i++) {
            if (isClicked && within(this, targetingPointLoc, sector.getSystem(i).loc.x, sector.getSystem(i).loc.y,
                    sector.getSystem(i).r)) {
                selected.y = i;
            }
        }
    }

    void sectorMap(MyShapeRenderer renderer) {
        int amount = 5;
        int index = 0;
        for (int i = 0; i <= amount; i++) {
            for (int j = 0; j < amount; j++) {
                // stroke(150);
                Vector2 offset = new Vector2(40, 40);
                if (i == amount)
                    renderer.line(x + i * offset.x, y + j * offset.y, x + (i + 0.3f) * offset.x, y + j * offset.y,
                            Color.WHITE, Color.WHITE);
                else if (j != 0)
                    renderer.line(x + i * offset.x, y + j * offset.y, x + (i + 1f) * offset.x, y + j * offset.y,
                            Color.WHITE, Color.WHITE);
                if (j == amount - 1)
                    renderer.line(x + i * offset.x, y + j * offset.y, x + i * offset.x, y + (j + 0.2f) * offset.y,
                            Color.WHITE, Color.WHITE);
                else
                    renderer.line(x + i * offset.x, y + j * offset.y, x + i * offset.x, y + (j + 1f) * offset.y,
                            Color.WHITE, Color.WHITE);
            }
        }

        renderer.setColor(0, 0, 0, 0);

        int with = 0;
        Vector2 temp = new Vector2(0, 0);
        for (int j = 0; j < amount - 1; j++) {
            for (int i = 0; i < amount; i++) {
                Vector2 offset = new Vector2(40, 40);
                s[i][j].renderTiny(renderer, this, new Vector2(40, 40));
                // fill(255);
                // textSize(map(10, 0, 1600, 0, width + height));
                // text(str(index + 1), x + i * offset.x, y + j * offset.y, offset.x, offset.y);
                if (within(this, targetingPointLoc, x + i * offset.x, y + j * offset.y, offset.x, offset.y)) {
                    with = index;
                    temp = new Vector2(i, j);
                }
                index++;
            }
        }
        if (isClicked) {
            selected.x = with;
            selectedSector = temp;
            selected.y = 0;
            selected.z = 0;
            System.out.println("Conversion Function: " + convertIndexToVector(with));
            System.out.println("index: " + with);
            System.out.println("Actual: " + temp);
        }
    }

    void changePointPos(Vector2 direction) {
        if (within(new Vector2(targetingPointLoc.x + direction.x, targetingPointLoc.y + direction.y), 0f, 0f,
                imageSize.x,
                imageSize.y)) {
            targetingPointLoc.x += direction.x;
            targetingPointLoc.y += direction.y;
        }
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        super.batchRenderer(batch, font, pMousePressed);
        image(tacticalDisplaySurroundings, x, y, w, h);
        int amount = 5;
        switch (scene) {
            case 0:
                int index = 0;
                for (int j = 0; j < amount - 1; j++) {
                    for (int i = 0; i < amount; i++) {
                        Vector2 offset = new Vector2(40, 40);
                        font.setColor(255, 255, 255, 255);
                        // textSize(10);
                        font.draw(batch, String.valueOf(index + 1), x + i * offset.x, h-(j * offset.y)+offset.y, offset.x, 0,
                                true);
                        index++;
                    }
                }
                rect(Color.BLACK, 0, h-10, w, 20);
                // textAlign(LEFT, TOP);
                // batch.setColor(Color.WHITE);
                font.setColor(Color.WHITE);
                font.draw(batch, "Selected Sector: " + Math.floor(selected.x + 1), x+30, y+h);
                // textAlign(RIGHT, BOTTOM);
                break;
        }
    }

    void shapeRenderer(MyShapeRenderer renderer, Sound click, Panel selectionPanel, boolean pMousePressed) {
        // textSize(13);
        // fill(255);
        if (selectionPanel.Button(click, new Vector2(5, 0), pMousePressed)) {
            isClicked = true;
        }
        switch (scene) {
            case 0:
                sectorMap(renderer);
                break;
            case 1:
                systemSelect(renderer, selectedSector);
                break;
            case 2:
                systemMap(renderer);
                // renderer.setColor(0, 0, 0, 0);
                // rect(0, -100, w, 110);
                // rect(0, h - 10, h, 2000);
                // fill(255);
                // textAlign(LEFT, TOP);
                // textSize(13);
                // displayText("Selected Sector: " + floor(selected.x + 1) + ", System: " +
                // floor(selected.y + 1)
                // + ", \nPlanet: " + floor(selected.z + 1), 30, 0);
                // textAlign(RIGHT, BOTTOM);
                break;
            case 3:
                s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) selected.y)
                        .getPlanet((int) selected.z)
                        .renderPlanetSystem(renderer, new Vector2(x, y), new Vector2(w, h), y + y / 2);

                // fill(255);
                // textAlign(LEFT, TOP);
                // textSize(13);
                // text("Sector: " + floor(selected.x + 1) + ", System: " + floor(selected.y +
                // 1) + ", Planet: "
                // + floor(selected.z + 1), 30, 0);
                // textAlign(RIGHT, BOTTOM);
                break;
            default:
                rect(0, 0, w, h);
        }
        if (scene != 3)
            target(targetingPointLoc.x, targetingPointLoc.y, 5);
        // noStroke();
        rect(Color.BLACK, -2, -2, 12, 175);
        rect(Color.BLACK, 218, 0, 10, 173);
        shapeRenderer(renderer);
        isClicked = false;
    }

    Vector2[] randomSysCoords(int amount) {
        Vector2[] r = new Vector2[amount];
        for (int i = 0; i < amount; i++) {
            r[i] = new Vector2(MathUtils.random(limits.x, limits.y), MathUtils.random(limits.z, limits.w));
        }
        return r;
    }
}
