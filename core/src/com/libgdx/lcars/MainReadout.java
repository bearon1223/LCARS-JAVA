package com.libgdx.lcars;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainReadout extends Readout {
    private Texture standby;

    private Panel mainSystemsPanel;

    private Panel navSystemsPanel;
    private Panel navCenterPanel;
    private Panel navTopPanel;
    private Panel navBottomPanel;

    public MainReadout(float x, float y, float w, float h) {
        super(x, y, w, h);
        standby = new Texture(Gdx.files.internal("Federation Standby.jpg"));

        mainSystemsPanel = new Panel(this, 0, 0, w, h, 6, 5);

        mainSystemsPanel.rename("Navigation", 5, 0);
        mainSystemsPanel.rename("Tactical", 5, 1);

        // tD = new TacticalDisplay(x + 10, y + 150);
        navSystemsPanel = new Panel(this, 340, h - 150 - 150, 150, 150, 3, 6);
        navCenterPanel = new Panel(this, 243, h - 120 - 211, 95, 211, 1, 4).addNames(TextArrays.navCenterPanelNames);
        navTopPanel = new Panel(this, 120, h - 120, w - 125, 120, 6, 1).addNames(TextArrays.navTopPanelNames);
        navBottomPanel = new Panel(this, 0, h - 330 - (h - 330), w, h - 330, 7, 1)
                .addNames(TextArrays.navBottomPanelNames);
    }

    public void shapeRenderer(MyShapeRenderer shape, Sound click, boolean pMousePressed) {
        switch (scene) {
            case 1:
                mainSystemsPanel.render(shape, 0, false);
                if (mainSystemsPanel.Button(click, new Vector2(5, 4), pMousePressed))
                    scene = 11;
                break;
            case 11:
                // Navigational Panel
                // tD.render(navTopPanel);
                navSystemsPanel.render(shape, 0, false);
                navCenterPanel.render(shape, 0, false);
                navTopPanel.render(shape, 0, false);
                navBottomPanel.render(shape, 0, false);
                break;
            default:
                shape.setColor(255, 255, 255, 255);
                shape.rect(x, y, scene * 10, 20);
                break;
        }
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font) {
        switch (scene) {
            case 0:
                batch.draw(standby, x, y, w, h);
                break;
            case 1:
                mainSystemsPanel.textRenderer(batch, font);
                break;
            case 11:
                // Navigational Panel
                font.getData().setScale(0.5f);
                navSystemsPanel.textRenderer(batch, font);
                font.getData().setScale(1);
                navCenterPanel.textRenderer(batch, font);
                navTopPanel.textRenderer(batch, font);
                navBottomPanel.textRenderer(batch, font);
                break;
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        standby.dispose();
    }
}
