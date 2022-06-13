package com.libgdx.lcars.Readout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.TextArrays;

public class MainReadout extends Readout {
    private Texture standby;

    private Panel mainSystemsPanel;

    private Panel navSystemsPanel;
    private Panel navCenterPanel;
    private Panel navTopPanel;
    private Panel navBottomPanel;
    private Sound click;

    private Starchart chart;

    public MainReadout(float x, float y, float w, float h) {
        super(x, y, w, h);
        standby = new Texture(Gdx.files.internal("Federation Standby.jpg"));

        mainSystemsPanel = new Panel(this, 0, 0, w, h, 6, 5);

        mainSystemsPanel.rename("Navigation", 5, 0);
        mainSystemsPanel.rename("Tactical", 5, 1);

        chart = new Starchart(x + 10, h - 310);

        // tD = new TacticalDisplay(x + 10, y + 150);
        navSystemsPanel = new Panel(this, 340, h - 150 - 150, 150, 150, 3, 6);
        navCenterPanel = new Panel(this, 243, h - 120 - 211, 95, 211, 1, 4).addNames(TextArrays.navCenterPanelNames);
        navTopPanel = new Panel(this, 120, h - 120, w - 125, 120, 6, 1).addNames(TextArrays.navTopPanelNames);
        navBottomPanel = new Panel(this, 0, h - 330 - (h - 330), w, h - 330, 7, 1)
                .addNames(TextArrays.navBottomPanelNames);
    }

    public void shapeRenderer(MyShapeRenderer shape, Sound click, boolean pMousePressed) {
        this.click = click;
        shapeRenderer(shape);
        switch (scene) {
            case 1:
                mainSystemsPanel.render(shape, 0, false);
                if (mainSystemsPanel.Button(click, new Vector2(5, 4), pMousePressed))
                    scene = 11;
                break;
            case 11:
                // Navigational Panel
                navSystemsPanel.render(shape, 0, false);
                navCenterPanel.render(shape, 0, false);
                navTopPanel.render(shape, 0, false);
                navBottomPanel.render(shape, 0, false);

                chart.shapeRenderer(shape, click, navTopPanel, pMousePressed);
                break;
            default:
                rect(new Color(1, 1, 1, 1), x, y, scene * 10, 20);
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
                // Navigational Panel
                int increment = 5;
                navSystemsPanel.textRenderer(batch, font, 0.5f);
                navCenterPanel.textRenderer(batch, font);
                navTopPanel.textRenderer(batch, font);
                navBottomPanel.textRenderer(batch, font);

                chart.batchRenderer(batch, font, pMousePressed);
                switch (circleButton(batch, click, 10, h - 110, 100, 100, pMousePressed)) {
                    case 1:
                        System.out.println("1");
                        break;
                    case 2:
                        System.out.println("2");
                        break;
                    case 3:
                        System.out.println("3");
                        break;
                    case 4:
                        System.out.println("4");
                        break;
                    case 5:
                        chart.changePointPos(new Vector2(0, -increment));
                        break;
                    case 6:
                        chart.changePointPos(new Vector2(0, increment));
                        break;
                    case 7:
                        chart.changePointPos(new Vector2(-increment, 0));
                        break;
                    case 8:
                        chart.changePointPos(new Vector2(increment, 0));
                        break;
                }

                if (navTopPanel.Button(click, new Vector2(0, 0), pMousePressed)) {
                    chart.scene = 0;
                } else if (navTopPanel.Button(click, new Vector2(1, 0), pMousePressed)) {
                    chart.scene = 1;
                } else if (navTopPanel.Button(click, new Vector2(2, 0), pMousePressed)) {
                    chart.scene = 2;
                } else if (navTopPanel.Button(click, new Vector2(3, 0), pMousePressed)) {
                    chart.scene = 3;
                } else if (navTopPanel.Button(click, new Vector2(4, 0), pMousePressed)) {
                    // chart.selected = new Vector3(coordinates.x, coordinates.y, coordinates.z);
                    // chart.selectedSector = chart.convertIndexToVector(coordinates.x);
                    // chart.scene = 3;
                }
                circleButton(batch, click, 890 - x, 100, 100, 100, pMousePressed);
                break;
        }
    }

    public void seperateRender(SpriteBatch batch, BitmapFont font, boolean pMousePressed, MyShapeRenderer shape,
            Sound click) {

    }

    @Override
    public void dispose() {
        super.dispose();
        standby.dispose();
    }
}
