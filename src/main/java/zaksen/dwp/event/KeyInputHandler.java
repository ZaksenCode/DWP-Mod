package zaksen.dwp.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import zaksen.dwp.screen.MainScreen;

public class KeyInputHandler
{
    public static final String KEY_CATEGORY_DWP = "key.category.dwp";
    public static final String KEY_OPEN_MENU = "key.dwp.main_menu";

    public static KeyBinding openMenuKey;

    public static void registerKeyInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(openMenuKey.wasPressed())
            {
                client.setScreen(new MainScreen());
            }
        });
    }

    public static void register()
    {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_MENU,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY_DWP
        ));

        registerKeyInputs();
    }
}
