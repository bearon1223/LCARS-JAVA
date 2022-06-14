package com.libgdx.lcars;

import com.badlogic.gdx.math.MathUtils;

public final class TextArrays {
        public static final String[][] smallSettingsPanelNames = {
                        { "LCARS", generateRandomString() },
                        { "LOCK", "SYS INFO" },
                        { "SYS TIME", generateRandomString() },
                        { "LOG OFF", "ONLINE" },
        };

        public static final String[][] navBottomPanelNames = {
                        { "WARP 1", "WARP 2", "WARP 3", "WARP 4", "WARP 5", "WARP 6", "WARP 7" }
        };

        public static final String[][] navTopPanelNames = {
                        { "SECTOR MAP", "SYSTEMS MAP", "SOLAR SYSTEM MAP", "PLANET MAP", "CURRENT PLANET", "SELECT" }
        };

        public static final String[][] navCenterPanelNames = {
                        { "FULL STOP" },
                        { "33% IMPULSE" },
                        { "66% IMPULSE" },
                        { "FULL IMPULSE" }
        };

        public static final String[][] mainSideMenuNames = {
                        { "SYSTEM DIRECTORY" },
                        { "ENGINE CONTROL" },
                        { "MED DIRECTORY" },
                        { "ANTENNA ARRAY" },
                        { "STELLAR MAPS" },
                        { "MISSION OPS" },
                        { "CARGO BAYS" },
        };

        public static final int[][] mainSideMenuClickID = {
                        { 1 },
                        { 2 },
                        { 3 },
                        { 4 },
                        { 5 },
                        { 6 },
                        { 7 },
        };

        public static final String[][] navPanelNames = {
                        { "Warp 7" },
                        { "Warp 6" },
                        { "Warp 5" },
                        { "Warp 4" },
                        { "Warp 3" },
                        { "Warp 2" },
                        { "Warp 1" },
                        { "Impulse" },
        };

        public static final String[][] upperButtonsNames = {
                        { "BACK", generateRandomString() },
                        { generateRandomString(), generateRandomString() },
                        { generateRandomString(), generateRandomString() },
                        { generateRandomString(), generateRandomString() },
        };

        public static final String[][] auxMainPanelNames = {
                        // eject, rerout reactor power, startup, shutDown, replaceDilithium
                        { "EJECT CORE", "REROUT POWER", "STARTUP", "EMERGENCY SHUTDOWN", "REPLACE REACTION MASS" }
        };

        public static final String[][] auxSidePanelNames = {
                        // Replace Reaction Mass, Shutdown, Startup, Powersave, reroute reactor power
                        { "REPLACE DEUTERIUM" },
                        { "SHUTDOWN" },
                        { "ENABLE" },
                        { "POWER SAVE" },
                        { "REROUT POWER" }
        };

        public static String generateRandomString(int length) {
                String[] number = new String[length];
                for (int i = 0; i < number.length; i++) {
                        if (i != (int) (length / 4.5f))
                                number[i] = String.valueOf((int) MathUtils.random(1, 9));
                        else
                                number[i] = "-";
                }
                String output = String.join("", number);
                return output;
        }

        public static String generateRandomString() {
                return generateRandomString(9);
        }
}
