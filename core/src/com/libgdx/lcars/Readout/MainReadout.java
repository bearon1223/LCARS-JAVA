package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.TextArrays;
import com.libgdx.lcars.ship.Ship;

public class MainReadout extends Readout {
    private Texture standby;

    private Panel mainSystemsPanel;
    private NavigationPanel nav;

    private Sound click;

    private Panel engMainPanel;
    private Panel engSidePanel;

    public Ship s;

    private Starchart chart;

    public MainReadout(Ship s, float x, float y, float w, float h) {
        super(x, y, w, h);
        this.s = s;
        standby = new Texture(Gdx.files.internal("Federation Standby.jpg"));

        mainSystemsPanel = new Panel(this, 300, 0, w - 300, h, 3, 4);

        mainSystemsPanel.rename("Navigation", 2, 0);
        mainSystemsPanel.rename("Tactical", 2, 1);
        mainSystemsPanel.rename("Mining", 2, 2);
        mainSystemsPanel.rename("Repair", 2, 3);

        engMainPanel = new Panel(this, 160, h - 100, 450, 100, 4, 1).addNames(TextArrays.engMainPanelNames);
        engSidePanel = new Panel(this, w - 125, 10, 130, h - 110, 1, 5).addNames(TextArrays.engSidePanelNames);

        chart = new Starchart(s.getSector(), x + 10, h - 310);

        nav = new NavigationPanel(chart, s, x, y, w, h);
    }

    public void shapeRenderer(MyShapeRenderer shape, Sound click, boolean pMousePressed) {
        this.click = click;
        shapeRenderer(shape);
        switch (scene) {
            case 1:
                mainSystemsPanel.render(shape, 0, false);
                if (mainSystemsPanel.Button(click, new Vector2(2, 3), pMousePressed))
                    scene = 11;
                if (mainSystemsPanel.Button(click, new Vector2(2, 1), pMousePressed))
                    scene = 12;
                break;
            case 11: // Navigation
                nav.shapeRenderer(shape, click, pMousePressed);
                break;
            case 2:
                engMainPanel.render(shape, 0, false);
                engSidePanel.render(shape, 0, false);

                // 12 segmants
                s.getWarpCore().render(shape, this);
                s.getPower().render(this, 160, 10);
                break;
            case 7:
                s.getCargo().render(shape, click, pMousePressed, this);
                break;
        }
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        super.batchRenderer(batch, font, pMousePressed);
        switch (scene) {
            case 0:
                image(standby, x, y, w, h);
                break;
            case 1:
                mainSystemsPanel.textRenderer(batch, font);
                break;
            case 11:
                nav.batchRenderer(batch, font, pMousePressed);
                break;
            case 12:
                s.getMining().renderMiningSystem(this, click, pMousePressed);
                break;
            case 2:
            // Engineering Panel
                engMainPanel.textRenderer(batch, font);
                engSidePanel.textRenderer(batch, font);
                s.getPower().textRender(this, 160, 10);
                //TODO: Add Power readout (Probably a sliding bar)
                // font.draw(batch, ""+(s.getPower().getPower()), 500, 30);
                if (engMainPanel.Button(click, new Vector2(3, 0), pMousePressed)) {
                    s.getWarpCore().disable();
                }

                if (engMainPanel.Button(click, new Vector2(2, 0), pMousePressed)) {
                    s.getWarpCore().enable();
                }

                if (engSidePanel.Button(click, new Vector2(0, 3), pMousePressed)) {
                    s.getImpulse().disable();
                }

                if (engSidePanel.Button(click, new Vector2(0, 2), pMousePressed)) {
                    s.getImpulse().enable();
                }
                break;
        }
    }

    public float getSelectedSpeed() {
        return nav.selectedSpeed;
    }

    public Starchart getStarchart() {
        return chart;
    }

    @Override
    public void dispose() {
        super.dispose();
        standby.dispose();
    }
}
