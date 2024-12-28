package net.i_no_am.auto_disconnect.config;

import io.github.itzispyder.improperui.ImproperUIAPI;
import io.github.itzispyder.improperui.config.ConfigReader;
import net.i_no_am.auto_disconnect.Global;
import net.i_no_am.auto_disconnect.config.settings.ConfigSettings;

public class Config implements Global {

    public static void loadConfig(){}

    public static ConfigReader ADconfig = ImproperUIAPI.getConfigReader(modId, "config.properties");
    public static ConfigSettings<Boolean> enable = new ConfigSettings<>(ADconfig.readBool("enable", true));
    public static ConfigSettings<Integer> range = new ConfigSettings<>(ADconfig.readInt("range", 5));
    public static ConfigSettings<Boolean> checkPlayerHealth = new ConfigSettings<>(ADconfig.readBool("checkPlayerHealth", true));
    public static ConfigSettings<Integer> selfPlayerHealth = new ConfigSettings<>(ADconfig.readInt("selfPlayerHealth", 2));
    public static ConfigSettings<Boolean> checkAnchors = new ConfigSettings<>(ADconfig.readBool("checkAnchors", true));
    public static ConfigSettings<Boolean> checkCrystals = new ConfigSettings<>(ADconfig.readBool("checkCrystals", true));
    public static ConfigSettings<Boolean> checkGlowstone = new ConfigSettings<>(ADconfig.readBool("checkGlowStone", true));
    public static ConfigSettings<Boolean> checkNewPlayers = new ConfigSettings<>(ADconfig.readBool("checkNewPlayers", false));
    public static ConfigSettings<Boolean> autoDisable = new ConfigSettings<>(ADconfig.readBool("autoDisable", true));
}