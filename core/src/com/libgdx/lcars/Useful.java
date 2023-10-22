package com.libgdx.lcars;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.libgdx.lcars.Readout.Readout;

public class Useful {
    public static Vector3 convertIndexToVector(float index) {
        Vector3 finalConversion = new Vector3(69, 69, 69);
        if (index <= 4)
            finalConversion = new Vector3(index, 0, 0); // Offset index properly
        else if (index <= 9)
            finalConversion = new Vector3(index - 5, 1, 0); // Offset index properly
        else if (index <= 14)
            finalConversion = new Vector3(index - 10, 2, 0); // Offset index properly
        else if (index <= 19)
            finalConversion = new Vector3(index - 15, 3, 0); // Offset index properly
        else
            System.out.println("ur dum"); // Literally will never happen
        return finalConversion;
    }

    public static boolean within(Readout r, Vector2 vector, float x, float y, float d) {
        return (Math.abs(Math.hypot((vector.x + r.x) - (x), (vector.y + r.y) - (y))) <= d / 2);
    }

    public static boolean within(Readout r, Vector2 vector, float x, float y, float w, float h) {
        if (vector.x + r.x > x && vector.y + r.y > y && vector.x + r.x < x + w && vector.y + r.y < y + h)
            return true;
        return false;
    }

    public static boolean within(Vector2 vector, float x, float y, float r) {
        return (Math.abs(Math.hypot(vector.x - x, vector.y - y)) <= r / 2);
    }

    public static boolean within(Vector2 vector, float x, float y, float w, float h) {
        return (vector.x > x && vector.y > y && vector.x < x + w && vector.y < y + h);
    }
}
