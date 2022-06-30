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
import com.libgdx.lcars.TextArrays;
import com.libgdx.lcars.ship.Ship;

public class MainReadout extends Readout {
    private Texture standby;

    private Panel mainSystemsPanel;

    private Panel navSystemsPanel;
    private Panel navCenterPanel;
    private Panel navTopPanel;
    private Panel navBottomPanel;
    private Sound click;

    private Panel engMainPanel;
    private Panel engSidePanel;

    public float selectedSpeed;

    private int WCsegmantLight = 0;

    public Ship s;

    protected Starchart chart;

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

        chart = new Starchart(s.s, x + 10, h - 310);

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
                if (mainSystemsPanel.Button(click, new Vector2(2, 3), pMousePressed))
                    scene = 11;
                if (mainSystemsPanel.Button(click, new Vector2(2, 1), pMousePressed))
                    scene = 12;
                break;
            case 11:
                // Navigational Panel
                navSystemsPanel.render(shape, 0, false);
                navCenterPanel.render(shape, 0, false);
                navTopPanel.render(shape, 0, false);
                navBottomPanel.render(shape, 0, false);

                chart.shapeRenderer(shape, click, navTopPanel, pMousePressed);
                break;
            case 2:
                int offset = 10;
                engMainPanel.render(shape, 0, false);
                engSidePanel.render(shape, 0, false);

                // 12 segmants
                rect(new Color(0.78f, 0.78f, 0.78f, 1), 0, offset + 112, 150, 340 - 120 * 2 + 18, 50);
                rect(new Color(0.39f, 0.39f, 0.39f, 1), 0, offset + 340 / 2 - 20, 150, 40, 3);
                Color c1 = new Color();
                if (s.getWarpCore().isEnabled())
                    c1 = Color.WHITE;
                else
                    c1 = new Color(0.59f, 0.59f, 0.59f, 1);
                rect(c1, 0, offset + 340 / 2 - 10, 150, 20);
                shape.setColor(100, 100, 100, 1);
                shape.ellipse(x + 150 / 2 - 25, offset + y + 340 / 2 - 25, 50, 50);
                Color c2 = new Color();
                if (s.getWarpCore().isEnabled())
                    c2 = Color.WHITE;
                else
                    c2 = new Color(0.59f, 0.59f, 0.59f, 1);
                shape.setColor(c2);
                shape.ellipse(x + 150 / 2 - 12.5f, offset + y + 340 / 2 - 12.5f, 25, 25);

                shape.setColor(new Color(0.39f, 0.39f, 0.39f, 1));
                shape.rect(x + 150 / 2 - 2.5f, offset + y + 340 / 2 - 15, 5, 30);
                for (int i = 0; i < 6; i++) {
                    Color c3;
                    if (WCsegmantLight == i)
                        c3 = Color.valueOf("#95D2FFFF");
                    else
                        c3 = Color.valueOf("#0075CBFF");
                    rect(c3, 10, offset + 20 * (i), 150 - 20, 20, 10);
                }
                for (int i = 0; i < 6; i++) {
                    Color c3;
                    if (WCsegmantLight == i)
                        c3 = Color.valueOf("#95D2FFFF");
                    else
                        c3 = Color.valueOf("#0075CBFF");
                    rect(c3, 10, offset + 20 * (5 - i) + (340 - 120), 150 - 20, 20, 10);
                }

                if (s.getWarpCore().isEnabled())
                    WCsegmantLight = (int) Math.floor((System.currentTimeMillis() / 1000) % 6);
                else
                    WCsegmantLight = -1;
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
                // Navigational Panel
                int increment = 5;
                font.setColor(Color.BLACK);
                navSystemsPanel.textRenderer(batch, font, 0.5f);
                navCenterPanel.textRenderer(batch, font);
                navTopPanel.textRenderer(batch, font);
                navBottomPanel.textRenderer(batch, font);

                chart.batchRenderer(batch, font, pMousePressed);
                switch (circleButton(batch, click, 10, h - 110, 100, 100, pMousePressed)) {
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

                if (s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance != 0) {
                    Color c = new Color(Color.WHITE);
                    if (Math.floor(System.currentTimeMillis() / 1000) % 2 == 0 && (!s.getWarpCore().isEnabled()))
                        c = new Color(1, 0.39f, 0.39f, 1);
                    else
                        c = new Color(Color.WHITE);
                    if (!s.getWarpCore().isEnabled())
                        displayText(c, "Error: No Active Warp Core", 10, h - 130, 0.7f);
                    else if (s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance != 0)
                        displayText(c, "Distance Left: "
                                + String.valueOf(
                                        Math.round((s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance)))
                                + "LY", 10, h - 130, 0.7f);
                } else if (s.getImpulse().travelDistance - s.getWarpCore().traveledDistance != 0) {
                    Color c = new Color(Color.WHITE);
                    if (Math.floor(System.currentTimeMillis() / 1000) % 2 == 0 && (!s.getImpulse().isEnabled()))
                        c = new Color(1, 0.39f, 0.39f, 1);
                    else
                        c = new Color(Color.WHITE);
                    if (!s.getImpulse().isEnabled())
                        displayText(c, "Error: No Active Impulse Engines", 10, h - 130, 0.7f);
                    else if (s.getImpulse().travelDistance - s.getWarpCore().traveledDistance != 0)
                        displayText(c, "Distance Left: "
                                + String.valueOf(
                                        Math.round(s.getImpulse().travelDistance - s.getImpulse().traveledDistance))
                                + "AU", 10, h - 130, 0.7f);
                } else
                    displayText("At Destination", 10, h - 130, 0.7f);

                if (navTopPanel.Button(click, new Vector2(0, 0), pMousePressed)) {
                    chart.scene = 0;
                } else if (navTopPanel.Button(click, new Vector2(1, 0), pMousePressed)) {
                    chart.scene = 1;
                } else if (navTopPanel.Button(click, new Vector2(2, 0), pMousePressed)) {
                    chart.scene = 2;
                } else if (navTopPanel.Button(click, new Vector2(3, 0), pMousePressed)) {
                    chart.scene = 3;
                } else if (navTopPanel.Button(click, new Vector2(4, 0), pMousePressed)) {
                    chart.selected = new Vector3(s.sectorCoords);
                    chart.selectedSector = new Vector2(chart.convertIndexToVector(s.sectorCoords.x).x,
                            chart.convertIndexToVector(s.sectorCoords.x).y);
                    chart.scene = 3;
                }

                if (s.getImpulse().traveledDistance - s.getImpulse().travelDistance != 0) {
                    if (navCenterPanel.Button(click, new Vector2(0, 1), pMousePressed) && s.getImpulse().isEnabled()) {
                        s.isTravelingImpulse = true;
                        selectedSpeed = 0.33f;
                    } else if (navCenterPanel.Button(click, new Vector2(0, 2), pMousePressed)
                            && s.getImpulse().isEnabled()) {
                        s.isTravelingImpulse = true;
                        selectedSpeed = 0.66f;
                    } else if (navCenterPanel.Button(click, new Vector2(0, 3), pMousePressed)
                            && s.getImpulse().isEnabled()) {
                        s.isTravelingImpulse = true;
                        selectedSpeed = 1;
                    }
                }

                if (navBottomPanel.Button(click, new Vector2(0, 0), pMousePressed) && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 1;
                } else if (navBottomPanel.Button(click, new Vector2(1, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 2;
                } else if (navBottomPanel.Button(click, new Vector2(2, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 3;
                } else if (navBottomPanel.Button(click, new Vector2(3, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 4;
                } else if (navBottomPanel.Button(click, new Vector2(4, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 5;
                } else if (navBottomPanel.Button(click, new Vector2(5, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 6;
                } else if (navBottomPanel.Button(click, new Vector2(6, 0), pMousePressed)
                        && (s.getWarpCore().isEnabled())) {
                    s.isTravelingWarp = true;
                    selectedSpeed = 7;
                }
                circleButton(batch, click, 890 - x, 100, 100, 100, pMousePressed);
                break;
            case 12:
                s.getMining().renderMiningSystem(this, click, pMousePressed);
                break;
            case 2:
                engMainPanel.textRenderer(batch, font);
                engSidePanel.textRenderer(batch, font);
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

    public Starchart getStarchart() {
        return chart;
    }

    @Override
    public void dispose() {
        super.dispose();
        standby.dispose();
    }
}
