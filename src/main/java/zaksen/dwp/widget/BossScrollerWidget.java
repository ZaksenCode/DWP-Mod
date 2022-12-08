package zaksen.dwp.widget;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.AbstractParentElement;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import zaksen.dwp.Dwp;
import zaksen.dwp.client.DwpClient;
import zaksen.dwp.misc.ModConfigs;

import java.util.List;

public class BossScrollerWidget extends AbstractParentElement implements Drawable, Element, Selectable
{
    protected final MinecraftClient client;
    protected int screenWidth;
    protected int screenHeight;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    private final List<BossCardWidget> entries = Lists.newArrayList();
    private final List<BossCardWidget> allEntries = Lists.newArrayList();
    private int scroll = 0;

    public BossScrollerWidget(MinecraftClient client, int screenWidth, int screenHeight, int x, int y, int width) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = 180;
        this.client = client;
    }
    public void updatePosition(int screenWidth, int screenHeight, int x, int y) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = x;
        this.y = y;
    }

    public void tick()
    {
        for(BossCardWidget entry : entries)
        {
            entry.tick();
        }
    }

    public void updateEntries() {
        entries.clear();
        allEntries.clear();

        int yCard = 30;
        int xCard = 30;
        for (DwpClient.Boss boss : DwpClient.Bosses)
        {
            BossCardWidget ThisCard = new BossCardWidget(MinecraftClient.getInstance(), xCard, yCard, 146, 180, boss.getBossEntity(), boss);
            entries.add(ThisCard);
            ThisCard.setName(boss.getName());
            ThisCard.setTimer(boss.getTimer());
            ThisCard.parent = this;
            xCard += 150;
        }

        allEntries.addAll(this.entries);
    }

    public int getWidth() {
        return 150;
    }

    public int getHeight() {
        return 180;
    }

    private int getMaxScroll() {
        return (getEntries().size() * 150) - (ModConfigs.minBosses * 150);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        double scaleFactor = client.getWindow().getScaleFactor();
        RenderSystem.enableScissor(
                (int) ((double) x * scaleFactor),
                0,
                (int) ((double) width * scaleFactor),
                Integer.MAX_VALUE
        );
        var bosses = getEntries();
        for (int i = 0; i < bosses.size(); i++) {
            var entry = bosses.get(i);
            int x = entry.defaultX - scroll;
            int y = entry.y;
            entry.directRender(
                    matrices, i, x, y, mouseX, mouseY,
                    mouseX >= x && mouseX < x + entry.getWidth() && mouseY >= y && mouseY < y + entry.getHeight(),
                    delta
            );
        }
        RenderSystem.disableScissor();
    }

    public List<BossCardWidget> getEntries() {
        return entries;
    }

    @Override
    public List<? extends Element> children() {
        return getEntries();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        this.scroll += amount * ModConfigs.scrollValue;
        if (this.scroll < 0) {
            this.scroll = 0;
        } else if (this.scroll > getMaxScroll()) {
            this.scroll = getMaxScroll();
        }
        for(BossCardWidget Boss : getEntries())
        {
            Boss.updatePosition(Boss.defaultX - scroll, Boss.y);
        }
        return true;
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
