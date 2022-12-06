package zaksen.dwp.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import zaksen.dwp.Dwp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ScreenTextures
{
    public static final Theme Vanilla_Theme = new Theme("VANILLA", new Identifier(Dwp.MOD_ID, "textures/gui/theme/vanilla.png"));
    public static final List<Theme> Custom_Themes = new ArrayList<Theme>();

    public static void loadThemes()
    {
        new File(MinecraftClient.getInstance().runDirectory.getAbsolutePath() + "/dwp_themes/").mkdirs();
        File[] themes_list = new File(MinecraftClient.getInstance().runDirectory.getAbsolutePath() + "/dwp_themes/").listFiles();
        for (File theme : Objects.requireNonNull(themes_list)) {
            if (theme.isFile())
            {
                Theme custom_theme = new Theme(theme.getName(), new Identifier(Dwp.MOD_ID, "dwp_themes/" + theme.getName()));
                Custom_Themes.add(custom_theme);
            }
        }
    }

    static class Theme
    {
        private final String name;
        private final Identifier ATLAS_MENU_TEXTURE;
        public Theme(String name, Identifier atlas)
        {
            this.name = name;
            this.ATLAS_MENU_TEXTURE = atlas;
        }
        public String getName()
        {
            return this.name;
        }
        public Identifier getAtlas()
        {
            return this.ATLAS_MENU_TEXTURE;
        }
    }
}