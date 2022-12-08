package zaksen.dwp.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import zaksen.dwp.Dwp;
import zaksen.dwp.misc.ModConfigs;
import zaksen.dwp.widget.CustomCheckWidget;

import static java.lang.String.valueOf;
import static zaksen.dwp.screen.ScreenTextures.Vanilla_Theme;

@Environment(EnvType.CLIENT)
public class SettingsScreen extends Screen {

    public SettingsScreen() {
        super(Text.translatable("menu.dwp.settings_title"));
    }

    public void init()
    {
        initWidgets();
        loadSettings();
    }

    //settings
    private TextFieldWidget minBossesInput;
    private CustomCheckWidget showBossesList;
    private TextFieldWidget scrollValueInput;

    private void initWidgets() {
        this.addDrawableChild(new TexturedButtonWidget(5, 5, 20, 20, 0, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            saveSettings();
            this.client.setScreen(new MainScreen());
        }, Text.empty()));
        this.addDrawableChild(new TexturedButtonWidget(5, 29, 20, 20, 20, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            saveSettings();
            this.client.setScreen(new BossesScreen());
        }, Text.empty()));
        this.addDrawableChild(new TexturedButtonWidget(5, 53, 20, 20, 40, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            saveSettings();
            this.client.setScreen(new SettingsScreen());
        }, Text.empty()));
        minBossesInput = this.addDrawableChild(new TextFieldWidget(this.textRenderer,30, 30, 20, 20, Text.empty()));
        minBossesInput.setMaxLength(2);
        scrollValueInput = this.addDrawableChild(new TextFieldWidget(this.textRenderer,30, 80, 30, 20, Text.empty()));
        minBossesInput.setMaxLength(3);
        showBossesList = this.addDrawableChild(new CustomCheckWidget(30,55,20,20, Text.translatable("dwp.menu.settings_show_list"), false));
    }

    public void tick() {
        super.tick();
    }

    public void loadSettings()
    {
        try
        {
            minBossesInput.setText(valueOf(ModConfigs.minBosses));
            showBossesList.setChecked(ModConfigs.showBossesList);
            scrollValueInput.setText(valueOf(ModConfigs.scrollValue));
        }
        catch (Exception e)
        {
            Dwp.LOG.error(e + "\n in class SettingsScreen");
        }
    }

    public void saveSettings()
    {
        try
        {
            ModConfigs.minBosses = Integer.parseInt(minBossesInput.getText());
            ModConfigs.showBossesList = showBossesList.isChecked();
            ModConfigs.scrollValue = Integer.parseInt(scrollValueInput.getText());
        }
        catch (Exception e)
        {
            Dwp.LOG.error(e + "\n in class SettingsScreen");
        }
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        drawTextWithShadow(matrices, this.textRenderer, Text.translatable("dwp.menu.settings_minbosses"), 54, 36, 16777215);
        drawTextWithShadow(matrices, this.textRenderer, Text.translatable("dwp.menu.settings_scroll_value"), 64, 86, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
