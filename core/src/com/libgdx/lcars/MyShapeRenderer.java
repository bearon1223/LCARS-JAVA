package com.libgdx.lcars;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import static com.badlogic.gdx.math.MathUtils.map;

public class MyShapeRenderer extends ShapeRenderer {
    /*
     * Maps the color values from standard 0-255 to libgdx's 0-1
     */
    
    @Override
    public void setColor(float r, float g, float b, float a) {
        super.setColor(map(0, 255, 0, 1, r), map(0, 255, 0, 1, g), map(0, 255, 0, 1, b), map(0, 255, 0, 1, a));
    }

    /*
     * Draws a rectangle with rounded corners of the given radius.
     */

    public void roundedRect(float x, float y, float width, float height, float radius) {
        // Central rectangle
        super.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);

        // Four side rectangles, in clockwise order
        super.rect(x + radius, y, width - 2 * radius, radius);
        super.rect(x + width - radius, y + radius, radius, height - 2 * radius);
        super.rect(x + radius, y + height - radius, width - 2 * radius, radius);
        super.rect(x, y + radius, radius, height - 2 * radius);

        // Four arches, clockwise too
        super.arc(x + radius, y + radius, radius, 180f, 90f);
        super.arc(x + width - radius, y + radius, radius, 270f, 90f);
        super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
        super.arc(x + radius, y + height - radius, radius, 90f, 90f);
    }
}
