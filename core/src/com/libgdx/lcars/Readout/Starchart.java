package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.SpaceTravel.Sector;

import static com.libgdx.lcars.Useful.convertIndexToVector;
import static com.libgdx.lcars.Useful.within;

public class Starchart extends Readout {
    final Vector2 imageSize = new Vector2(228, 173);

    private Sector[][] s;

    public Vector2 targetingPointLoc;
    public Vector3 selected; // X: Sector Y: System Z: Planet
    public Vector2 currentSector = new Vector2(0, 0);
    public Vector2 selectedSector = new Vector2(0, 0);

    private Texture tacticalDisplaySurroundings;
    private boolean isMouseMode = false;

    public boolean isClicked = false;

    Starchart(Sector[][] s, float x, float y) {
        super(x, y, 228, 173);
        this.s = s;
        selected = new Vector3(0, 0, 1);
        targetingPointLoc = new Vector2(imageSize.x / 2, imageSize.y / 2);
        tacticalDisplaySurroundings = new Texture(Gdx.files.internal("Tactical Display Surroundings.png"));
    }

    public void setIsMouseMode(boolean value) {
        isMouseMode = value;
    }

    // Test if the given value is within a given distance to a given position. Some
    // require a readout for coordinate system conversions.
    

    // Convert a single index into a vector.

    private void target(MyShapeRenderer shape, float x, float y, float size) {
        shape.rectLine(this.x + (x - size), this.y + (y), this.x + (x + size), this.y + (y), 3,
                Color.valueOf("#FFAD29FF"), Color.valueOf("#FFAD29FF")); // a cross with an orange collor
        shape.rectLine(this.x + (x), this.y + (y - size), this.x + (x), this.y + (y + size), 3,
                Color.valueOf("#FFAD29FF"), Color.valueOf("#FFAD29FF")); // a cross with an orange collor
    }

    private void systemMap(MyShapeRenderer renderer) {
        float yCoord = y + h / 2;
        boolean mouseClickBool = false;

        // orbital path
        // TODO: Add Orbital Path Arcs

        // render the selected system from the sector.
        s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) Math.floor(selected.y))
                .renderPlanets(renderer, yCoord);

        // Mouse Input
        if (Gdx.input.isButtonJustPressed(0) && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()), 450, yCoord,
                s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 0; // Set this as the selected system. if it has the said system.
            mouseClickBool = true;
        } else if (Gdx.input.isButtonJustPressed(0) && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()), 490, yCoord,
                s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 1; // Set this as the selected system. if it has the said system.
            mouseClickBool = true;
        } else if (Gdx.input.isButtonJustPressed(0) && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()), 540, yCoord,
                s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 2; // Set this as the selected system. if it has the said system.
            mouseClickBool = true;
        } else if (Gdx.input.isButtonJustPressed(0) && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()), 590, yCoord,
                s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 3; // Set this as the selected system. if it has the said system.
            mouseClickBool = true;
        }

        // Orange Selection Cross Input
        if (!mouseClickBool && isClicked
                && within(this, targetingPointLoc, 450, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 0; // Set this as the selected system. if it has the said system.
        } else if (!mouseClickBool && isClicked
                && within(this, targetingPointLoc, 490, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 1; // Set this as the selected system. if it has the said system.
        } else if (!mouseClickBool && isClicked
                && within(this, targetingPointLoc, 540, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 2; // Set this as the selected system. if it has the said system.
        } else if (!mouseClickBool && isClicked
                && within(this, targetingPointLoc, 590, yCoord, s[(int) selectedSector.x][(int) selectedSector.y]
                        .getSystem((int) Math.floor(selected.y)).getPlanet((int) selected.z).size * 2)) {
            selected.z = 3; // Set this as the selected system. if it has the said system.
        }
    }

    private void systemSelect(MyShapeRenderer renderer, Vector2 index) {
        displayText("Selected Sector: " + Math.floor(selected.x + 1) + ", System: " + Math.floor(selected.y + 1), 30, h,
                0.7f);
        // create a dummy sector at the selected index for ease of access
        Sector sector = s[(int) index.x][(int) index.y];
        sector.renderSector(renderer);
        boolean mouseClickBool = false;
        for (int i = 0; i < sector.systemAmount; i++) {
            if (!isClicked && Gdx.input.isButtonJustPressed(0)
                    && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()),
                            sector.getSystem(i).loc.x, sector.getSystem(i).loc.y, sector.getSystem(i).r * 2)) {
                selected.y = i;
                mouseClickBool = true;
                isMouseMode = true;
            }

            if (!mouseClickBool && isClicked
                    && within(this, targetingPointLoc, sector.getSystem(i).loc.x, sector.getSystem(i).loc.y,
                            sector.getSystem(i).r * 2)) {
                selected.y = i;
            }
        }
    }

    private void sectorMap(MyShapeRenderer renderer) {
        int amount = 5;
        int index = 0;
        // draw sector lines
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

        // render system preview in each sector and do selection logic
        int selectedSectorIndex = 0;
        Vector2 selectedSectorID = new Vector2(0, 0);
        boolean mouseClickBool = false;
        for (int j = 0; j < amount - 1; j++) {
            for (int i = 0; i < amount; i++) {
                Vector2 offset = new Vector2(40, 40);
                s[i][j].renderTiny(renderer, this, new Vector2(40, 40)); // tell system to render a tiny version of
                                                                         // itself
                // Do the same thing as below but with a mouse to click on the sector.
                if (!isClicked && Gdx.input.isButtonJustPressed(0)
                        && within(new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY()),
                                x + i * offset.x, y + j * offset.y, offset.x, offset.y)) {
                    selectedSectorIndex = index;
                    selectedSectorID = new Vector2(i, j);
                    mouseClickBool = true;
                    isMouseMode = true;
                }

                // if targeting point is within the sector, choose the sector.
                if (within(this, targetingPointLoc, x + i * offset.x, y + j * offset.y, offset.x, offset.y)
                        && !mouseClickBool) {
                    selectedSectorIndex = index; // I have a feeling that this index is just for debug perposes
                    selectedSectorID = new Vector2(i, j);
                }
                index++;
            }
        }
        if (isClicked || mouseClickBool) {
            // if the select button is pressed, set the sector the targeting point is over
            // as the selected sector.
            selected.x = selectedSectorIndex;
            selectedSector = selectedSectorID;
            selected.y = 0;
            selected.z = 0;
            // print debug information of the convertIndextoVector function
            System.out.println("Conversion Function: " +  convertIndexToVector(selectedSectorIndex));
            System.out.println("index: " + selectedSectorIndex);
            System.out.println("Actual: " + selectedSectorID);
        }
    }

    protected void changePointPos(Vector2 direction) {
        // If targeting point is within the readout, move the targeting point
        if (within(new Vector2(targetingPointLoc.x + direction.x, targetingPointLoc.y + direction.y), 0f, 0f,
                imageSize.x,
                imageSize.y)) {
            targetingPointLoc.x += direction.x;
            targetingPointLoc.y += direction.y;
        }
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        // Batch = shape
        super.batchRenderer(batch, font, pMousePressed);
        image(tacticalDisplaySurroundings, x, y, w, h);
        int amount = 5;
        switch (scene) {
            case 0:
                int index = 0;
                font.getData().setScale(0.6f);
                for (int j = 0; j < amount - 1; j++) {
                    for (int i = 0; i < amount; i++) {
                        Vector2 offset = new Vector2(40, 40);
                        font.setColor(255, 255, 255, 255);
                        // textSize(10);
                        font.draw(batch, String.valueOf(index + 1), x + i * offset.x - 5,
                                (j * offset.y) + (offset.y * 2.3f),
                                offset.x, 0,
                                true);
                        index++;
                    }
                }
                rect(Color.BLACK, 0, h - 10, w, 20);
                displayText(Color.WHITE, "Selected Sector: " + Math.floor(selected.x + 1), 30, h, 0.7f);
                break;
            case 1:
                Sector sector = s[(int) selectedSector.x][(int) selectedSector.y];
                for (int i = 0; i < sector.systemAmount; i++) {
                    font.draw(batch, "System: " + (i + 1), sector.getSystem(i).loc.x, sector.getSystem(i).loc.y);
                }
                break;
        }
    }

    public void shapeRenderer(MyShapeRenderer renderer, Sound click, Panel selectionPanel, boolean pMousePressed) {
        if (selectionPanel.Button(click, new Vector2(5, 0), pMousePressed)) {
            isClicked = true;
            isMouseMode = false;
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
                displayText("Selected Sector: " + Math.floor(selected.x + 1) + ", System: " + Math.floor(selected.y + 1)
                        + ", \nPlanet: " + Math.floor(selected.z + 1) + ", Name: "
                        + s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) selected.y)
                                .getPlanet((int) selected.z).name,
                        30, h - 0.7f, 0.7f);
                break;
            case 3:
                s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) selected.y)
                        .getPlanet((int) selected.z)
                        .renderPlanetSystem(renderer, new Vector2(x, y), new Vector2(w, h), (float) (y + h / 2));

                displayText("Sector: " + Math.floor(selected.x + 1) + ", System: " + Math.floor(selected.y + 1)
                        + ", \nPlanet: " + Math.floor(selected.z + 1) + ", Name: "
                        + s[(int) selectedSector.x][(int) selectedSector.y].getSystem((int) selected.y)
                                .getPlanet((int) selected.z).name,
                        30, h - 0.7f, 0.7f);
                break;
            default:
                rect(0, 0, w, h);
        }
        if (scene != 3 && !isMouseMode)
            target(renderer, targetingPointLoc.x, targetingPointLoc.y, 5);

        rect(Color.BLACK, -2, -2, 12, 175);
        rect(Color.BLACK, 218, 0, 10, 173);
        shapeRenderer(renderer);
        isClicked = false;
    }
}
