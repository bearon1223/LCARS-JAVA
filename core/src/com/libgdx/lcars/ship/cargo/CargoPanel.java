package com.libgdx.lcars.ship.cargo;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.Readout.Readout;

import static com.badlogic.gdx.math.MathUtils.map;

public class CargoPanel extends Panel {
    Texture[] textures;

    public CargoPanel(Texture[] textures, float x, float y, float w, float h, float pCy) {
        super(x, y, w, h, 1, pCy);
        this.textures = textures;
    }

    public CargoPanel addNames(String[][] nameArray) {
        this.names = nameArray;
        return this;
    }

    public void textRenderer(Readout r, float[] itemAmount) {
        int offset = 5;
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        for (int i = 0; i < panelCount.y; i++) {
            r.image(textures[(int) ((panelCount.y - 1) - i)], x + 5, (float) y + i * rectSize.y + 5, rectSize.y - offset - 10,
                    rectSize.y - offset - 10);
            r.displayText(Color.BLACK, names[(int) ((panelCount.y - 1) - i)][0], x + rectSize.y - r.x,
                    (float) y + i * rectSize.y + 15, rectSize.x, -1, 1);
            r.displayText(Color.BLACK, (Math.floor(itemAmount[(int) ((panelCount.y - 1) - i)]*10)/10) + " Liters", x - 10 - r.x, (float) y + i * rectSize.y + 15,
                    rectSize.x, 0, 1);
        }
    }

    public void render(Readout r) {
        int offset = 5;
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                Color c = new Color(map(0, 255, 0, 1, colors[i][j].x), map(0, 255, 0, 1, colors[i][j].y),
                        map(0, 255, 0, 1, colors[i][j].z), 1);
                r.rect(c, (float) x + i * rectSize.x - r.x, (float) y + j * rectSize.y - r.y, rectSize.x - offset,
                        rectSize.y - offset);
            }
        }
    }
}
