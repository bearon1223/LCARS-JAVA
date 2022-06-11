package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Panel {
    public float x, y;
    public Vector2 size;
    public Vector2 panelCount;
    private Vector3[][] colors;

    private String[][] names;

    public Panel(float x, float y, Vector2 size, Vector2 panelCount) {
        this.x = x;
        this.y = y/* 600 - y - size.y */;
        this.size = size;
        this.panelCount = panelCount;
        names = new String[(int) panelCount.y][(int) panelCount.x];
        colors = new Vector3[(int) panelCount.x][(int) panelCount.y];
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                colors[i][j] = new Vector3(50, MathUtils.random(100, 200), MathUtils.random(200, 255));
                names[j][i] = TextArrays.generateRandomString();
            }
        }
    }

    public Panel(Readout r, float x, float y, float w, float h, float pCx, float pCy) {
        this(r.x + x, r.y + y, w, h, pCx, pCy);
    }

    public Panel(float x, float y, float w, float h, Vector2 panelCount) {
        this(x, y, new Vector2(w, h), panelCount);
    }

    public Panel(float x, float y, float w, float h, float pCx, float pCy) {
        this(x, y, w, h, new Vector2(pCx, pCy));
    }

    public Panel addNames(String[][] nameArray) {
        this.names = nameArray;
        return this;
    }

    public void setTextSize(BitmapFont font, float scaleXY){
        font.getData().setScale(scaleXY);
    }

    public void rename(String txt, int idx, int idy) {
        names[idy][idx] = txt;
    }

    public boolean Button(Sound clickSound, Vector2 id, boolean pMousePressed) {
        if (Gdx.input.isTouched()) {
            Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), 600 - Gdx.input.getY(), 0);
            if (touchPos.x > x + id.x * rectSize.x && touchPos.x < x + id.x * rectSize.x + rectSize.x
                    && touchPos.y > (y + id.y * rectSize.y) && touchPos.y < (y + id.y * rectSize.y) + rectSize.y
                    && Gdx.input.isTouched() != pMousePressed) {
                clickSound.play(0.125f);
                return true;
            }
        }
        return false;
    }

    void clickArray(Sound clickSound, Readout r, int[][] array, boolean pMousePressed) {
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                if (Button(clickSound, new Vector2(i, j), pMousePressed)) {
                    r.scene = array[(int) ((panelCount.y - 1) - j)][i];
                }
            }
        }
    }

    void textRenderer(SpriteBatch batch, BitmapFont font) {
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        float offset = rectSize.y / 1.5f;
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                font.draw(batch, names[(int) ((panelCount.y - 1) - j)][i], (float) x + i * rectSize.x,
                        (float) y + j * rectSize.y + offset,
                        rectSize.x - 5, 1, true);
            }
        }
    }

    void render(MyShapeRenderer renderer, int round, boolean isReversed) {
        int offset = 5;
        Vector2 rectSize = new Vector2((size.x) / panelCount.x, (size.y) / panelCount.y);
        for (int i = 0; i < panelCount.x; i++) {
            for (int j = 0; j < panelCount.y; j++) {
                float tempx = (float) x + i * rectSize.x;
                float tempy = (float) y + j * rectSize.y;
                float tempw = rectSize.x - offset;
                float temph = rectSize.y - offset;
                renderer.setColor(colors[i][j].x, colors[i][j].y, colors[i][j].z, 255);
                if (round == 0)
                    renderer.rect((float) x + i * rectSize.x, (float) y + j * rectSize.y, rectSize.x - offset,
                            rectSize.y - offset);
                if (round == 1 && !isReversed) {
                    renderer.rect(tempx + tempw / 4, tempy, tempw - tempw / 4, temph);
                    renderer.ellipse(tempx, tempy, tempw - tempw / 2, temph);
                } else if (round == 1 && isReversed) {
                    if (i == panelCount.x - 1) {
                        renderer.rect(tempx, tempy, tempw - tempw / 4, temph);
                        renderer.ellipse(tempw + tempx - tempw / 2, tempy, tempw - tempw / 2, temph);
                    } else {
                        renderer.rect(tempx + tempw / 4, tempy, tempw - tempw / 4, temph);
                        renderer.ellipse(tempx, tempy, tempw - tempw / 2, temph);
                    }
                } else if (round == 2) {
                    renderer.roundedRect((float) x + i * rectSize.x, (float) y + j * rectSize.y, rectSize.x - offset,
                            rectSize.y - offset, (rectSize.y - offset)/2);
                }
            }
        }
    }
}
