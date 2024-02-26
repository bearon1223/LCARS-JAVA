package com.libgdx.lcars.Readout;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.MyShapeRenderer;
import com.libgdx.lcars.Panel;
import com.libgdx.lcars.TextArrays;
import com.libgdx.lcars.ship.Ship;

import static com.libgdx.lcars.Useful.convertIndexToVector;

public class NavigationPanel extends Readout {
    private Panel navSystemsPanel;
    private Panel navCenterPanel;
    private Panel navTopPanel;
    private Panel navBottomPanel;

    private Starchart chart;

    private Ship s;

    public float selectedSpeed;

    private Sound click;

    public NavigationPanel(Starchart chart, Ship ship, float x, float y, float w, float h) {
        super(x, y, w, h);

        this.s = ship;

        this.chart = chart;

        navSystemsPanel = new Panel(this, 340, h - 150 - 150, 150, 150, 3, 6);
        navCenterPanel = new Panel(this, 243, h - 120 - 211, 95, 211, 1, 4).addNames(TextArrays.navCenterPanelNames);
        navTopPanel = new Panel(this, 120, h - 120, w - 125, 120, 6, 1).addNames(TextArrays.navTopPanelNames);
        navBottomPanel = new Panel(this, 0, h - 330 - (h - 330), w, h - 330, 7, 1)
                .addNames(TextArrays.navBottomPanelNames);
    }

    public void shapeRenderer(MyShapeRenderer shape, Sound click, boolean pMousePressed) {
        this.click = click;

        navSystemsPanel.render(shape, 0, false);
        navCenterPanel.render(shape, 0, false);
        navTopPanel.render(shape, 0, false);
        navBottomPanel.render(shape, 0, false);

        chart.shapeRenderer(shape, click, navTopPanel, pMousePressed);
    }

    @Override
    public void batchRenderer(SpriteBatch batch, BitmapFont font, boolean pMousePressed) {
        super.batchRenderer(batch, font, pMousePressed);
        // Navigational Panel
        int increment = 5;
        font.setColor(Color.BLACK);
        navSystemsPanel.textRenderer(batch, font, 0.5f);
        navCenterPanel.textRenderer(batch, font);
        navTopPanel.textRenderer(batch, font);
        navBottomPanel.textRenderer(batch, font);

        chart.batchRenderer(batch, font, pMousePressed);
        // Move the selector cross
        switch (circleButton(batch, click, 10, h - 110, 100, 100, pMousePressed)) {
            case 5:
                chart.changePointPos(new Vector2(0, -increment));
                chart.setIsMouseMode(false);
                break;
            case 6:
                chart.changePointPos(new Vector2(0, increment));
                chart.setIsMouseMode(false);
                break;
            case 7:
                chart.changePointPos(new Vector2(-increment, 0));
                chart.setIsMouseMode(false);
                break;
            case 8:
                chart.changePointPos(new Vector2(increment, 0));
                chart.setIsMouseMode(false);
                break;
        }

        // determine if the distance is using the warp core or the impulse drive or we are at the destination
        if (s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance != 0) {
            Color c = new Color(Color.WHITE);
            // Alternate colors
            if (Math.floor(System.currentTimeMillis() / 1000) % 2 == 0 && (!s.getWarpCore().isEnabled()))
                c = new Color(1, 0.39f, 0.39f, 1);
            
            // If the warpcore is not enabled, say so, else show the distance remaining in Light years
            if (!s.getWarpCore().isEnabled())
                displayText(c, "Error: No Active Warp Core", 10, h - 130, 0.7f);
            else if (s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance != 0)
                displayText(c, "Distance Left: "
                        + String.valueOf(
                                Math.round((s.getWarpCore().travelDistance - s.getWarpCore().traveledDistance)))
                        + "LY", 10, h - 130, 0.7f);
        } else if (s.getImpulse().travelDistance - s.getWarpCore().traveledDistance != 0) {
            Color c = new Color(Color.WHITE);
            // alternate colors
            if (Math.floor(System.currentTimeMillis() / 1000) % 2 == 0 && (!s.getImpulse().isEnabled()))
                c = new Color(1, 0.39f, 0.39f, 1);
            
            // If the impulse drive is not enabled, say so. Else show the distance remaining in AU (bruh ur au is probs not accurate lol)
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
            // TODO: Fix this? what tf is wrong here tho?
            chart.selected = new Vector3(s.sectorCoords);
            Vector2 sectorIndex = new Vector2(convertIndexToVector(s.sectorCoords.x).x,
                    convertIndexToVector(s.sectorCoords.x).y);
            chart.selectedSector = sectorIndex;
                    System.out.printf("Converting %f into %f, %f", s.sectorCoords.x, sectorIndex.x, sectorIndex.y);//13,2,2
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
    }
}
