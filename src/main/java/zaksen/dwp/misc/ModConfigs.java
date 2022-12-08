package zaksen.dwp.misc;

import com.mojang.datafixers.util.Pair;
import zaksen.dwp.Dwp;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static int scrollValue;
    public static int minBosses;
    public static boolean showBossesList;
    public static double bossesListX;
    public static double bossesListY;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Dwp.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("settings.scrollValue", "150"), "int");
        configs.addKeyValuePair(new Pair<>("settings.min_bosses_columns", "3"), "int");
        configs.addKeyValuePair(new Pair<>("settings.show_bosses_list", false), "bool");
        configs.addKeyValuePair(new Pair<>("settings.bosses_list_x", 10.0), "double");
        configs.addKeyValuePair(new Pair<>("settings.bosses_list_y", 10.0), "double");
    }

    private static void assignConfigs() {
        scrollValue = CONFIG.getOrDefault("settings.scrollValue", 150);
        minBosses = CONFIG.getOrDefault("settings.min_bosses_columns", 3);
        showBossesList = CONFIG.getOrDefault("settings.show_bosses_list", false);
        bossesListX = CONFIG.getOrDefault("settings.bosses_list_x", 10.0);
        bossesListY = CONFIG.getOrDefault("settings.bosses_list_y", 10.0);

        System.out.println("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
