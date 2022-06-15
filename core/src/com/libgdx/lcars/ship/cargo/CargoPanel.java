package com.libgdx.lcars.ship.cargo;

import static com.badlogic.gdx.math.MathUtils.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.Readout.Readout;

public class CargoPanel extends Panel {
    private Texture[] textures;
    private Item[] item;
    private int selected = 0;

    public CargoPanel(Texture[] textures, float x, float y, float w, float h, float pCy) {
        super(x, y, w, h, 1, pCy);
        this.textures = textures;
        item = new Item[(int) pCy];
    }

    public void setItem(Item item, int id) {
        this.item[id] = item;
    }

    public Item getItem(int id) {
        return item[id];
    }

    public void setSelected(int selectedItem) {
        selected = selectedItem;
    }

    public CargoPanel addNames(String[][] nameArray) {
        this.names = nameArray;
        return this;
    }

    public void textRenderer(Readout r, Item[] item) {
        int offset = 5;
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        for (int i = 0; i < panelCount.y; i++) {
            r.image(textures[(int) ((panelCount.y - 1) - i)], x + 5, (float) y + i * rectSize.y + 5,
                    rectSize.y - offset - 10,
                    rectSize.y - offset - 10);
            r.displayText(Color.BLACK, names[(int) ((panelCount.y - 1) - i)][0], x + rectSize.y - r.x,
                    (float) y + i * rectSize.y + 15, rectSize.x, -1, 1);
                    r.displayText(Color.BLACK, (Math.floor(item[(int) ((panelCount.y - 1) - i)].itemCount * 10) / 10) + " Liters",
                    x - 10 - r.x, (float) y + i * rectSize.y + 15,
                    rectSize.x, 1, 1);

                    r.displayText(Color.BLACK, "Price Per Unit: "+item[(int) ((panelCount.y - 1) - i)].perItemCost,
                    x - 10 - r.x, (float) y + i * rectSize.y + 15,
                    rectSize.x, 0, 1);
        }
    }

    public void render(Readout r) {
        int offset = 5;
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                Color c = new Color(Color.WHITE);
                if (selected == panelCount.y-j)
                    c = new Color(Color.ORANGE);
                else
                    c = new Color(map(0, 255, 0, 1, colors[i][j].x), map(0, 255, 0, 1, colors[i][j].y),
                            map(0, 255, 0, 1, colors[i][j].z), 1);
                r.rect(c, (float) x + i * rectSize.x - r.x, (float) y + j * rectSize.y - r.y, rectSize.x - offset,
                        rectSize.y - offset);
            }
        }
    }
}
