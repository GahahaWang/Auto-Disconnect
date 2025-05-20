package net.i_no_am.auto_disconnect.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.i_no_am.auto_disconnect.Global;

import java.util.ArrayList;
import java.util.List;

public class Config extends MidnightConfig implements Global {

    @Entry(category = "GENERAL", name = "Enable Auto Disconnect")
    public static boolean enable = true;

    @Entry(category = "GENERAL", name = "Disable Automatically After Trigger")
    public static boolean autoDisable = true;

    @Entry(category = "GENERAL", name = "Detection Range", isSlider = true, min = 0, max = 400, precision = 1)
    public static int range = 5;

    @Condition(requiredModId = "isTHisEverGonnnABeNoticeBySomeonE")
    @Entry(category = "GENERAL", name = "Dummy Setting, IGNORE!!!")
    public static boolean shouldCheck = true;

    @Entry(category = "PLAYERS", name = "Check Player Health")
    public static boolean checkPlayerHealth = false;

    @Entry(category = "PLAYERS", name = "Minimum Health to Disconnect", isSlider = true, min = 1, max = 20)
    public static int selfPlayerHealth = 2;

    @Entry(category = "PLAYERS", name = "Detect Players in Render Distance")
    public static boolean checkPlayersInRenderDistance = false;

    @Entry(category = "PLAYERS", name = "Check for new online players")
    public static boolean checkNewPlayers = false;

    @Entry(category = "PLAYERS", name = "Check for a specific player in the server")
    public static boolean checkPlayersInList = false;

    @Entry(category = "PLAYERS", name = "Player names list:")
    public static List<String> playersName = new ArrayList<>();

    @Entry(category = "PVP", name = "Check for End Crystals in render distance")
    public static boolean checkCrystals = false;

    @Entry(category = "PVP", name = "Check for Respawn Anchors in render distance")
    public static boolean checkAnchors = false;

    @Entry(category = "PVP", name = "Check for Glowstone")
    public static boolean checkGlowstone = false;

    @Entry(category = "PVP", name = "The number of loaded glowstones in anchor", isSlider = true, min = 1, max = 4)
    public static int glowstone_num = 1;
}
