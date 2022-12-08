package zaksen.dwp.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import zaksen.dwp.client.DwpClient;
import zaksen.dwp.widget.BossScrollerWidget;

import static zaksen.dwp.screen.ScreenTextures.Vanilla_Theme;

@Environment(EnvType.CLIENT)
public class BossesScreen extends Screen {
    public BossesScreen() {
        super(Text.translatable("menu.dwp.bosses_title"));
    }

    public void init()
    {
        initWidgets();
    }

    private void initWidgets() {
        this.addDrawableChild(new TexturedButtonWidget(5, 5, 20, 20, 0, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            this.client.setScreen(new MainScreen());
        }, Text.empty()));
        this.addDrawableChild(new TexturedButtonWidget(5, 29, 20, 20, 20, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            this.client.setScreen(new BossesScreen());
        }, Text.empty()));
        this.addDrawableChild(new TexturedButtonWidget(5, 53, 20, 20, 40, 0, 20, Vanilla_Theme.getAtlas(), 128, 128, (button) -> {
            this.client.setScreen(new SettingsScreen());
        }, Text.empty()));
        BossScrollerWidget BossScroller = new BossScrollerWidget(
                client, width, height, 30, 30,
                150 * DwpClient.Bosses.size()
        );
        BossScroller.updateEntries();

        this.addDrawableChild(BossScroller);
    }

    public void tick() {
        super.tick();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 10, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
