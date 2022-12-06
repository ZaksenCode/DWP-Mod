package zaksen.dwp.widget;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import zaksen.dwp.Dwp;

import java.util.List;
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class BossCardWidget extends AbstractParentElement implements Drawable, Element, Selectable {
    public static final Identifier CARD_TEXTURE = new Identifier(Dwp.MOD_ID, "textures/gui/boss_card.png");
    protected int width;
    protected int height;
    public boolean active = true;
    private boolean focused;
    protected boolean hovered;
    public int x;
    public int y;
    protected float alpha = 1.0F;
    private Text Timer;
    private Text Name;
    private LivingEntity RenderEntity;
    public boolean selected = false;
    public BossScrollerWidget parent;
    public MinecraftClient client;


    public boolean changeFocus(boolean lookForwards) {
        if (this.active) {
            this.focused = !this.focused;
            this.onFocusedChanged(this.focused);
            return this.focused;
        } else {
            return false;
        }
    }

    public void updatePosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    protected void onFocusedChanged(boolean newFocused) {
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return true;
    }

    public BossCardWidget(MinecraftClient client, int x, int y, int width, int height, @Nullable LivingEntity entity)
    {
        this.client = client;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.RenderEntity = entity;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta)
    {
        this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
        this.renderCard(matrices, mouseX, mouseY, delta);
    }

    public void directRender(MatrixStack matrices, int index, int x, int y, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, CARD_TEXTURE);
        if (selected) {
            drawTexture(matrices, x, y, 0, 0, 146, 180, 256, 256);
        } else if (hovered && children().stream().noneMatch(element -> element.isMouseOver(mouseX, mouseY))) {
            drawTexture(matrices, x, y, 0, 0, 146, 180, 256, 256);
        }
        renderIcon(matrices, index, x, y, mouseX, mouseY, hovered, tickDelta);
        RenderSystem.disableBlend();
    }

    protected void renderIcon(MatrixStack matrices, int index, int x, int y, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        RenderSystem.setShaderTexture(0, CARD_TEXTURE);
        drawTexture(matrices, x, y, 146, 180, 0, 0, 146, 180, 256, 256);
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CARD_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.width / 2, this.height);
        this.drawTexture(matrices, this.x + this.width / 2, this.y, 146 - this.width / 2, 0, this.width / 2, this.height);
        this.renderBackground(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, this.getName(), this.x + this.width / 2, this.y + 15, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        drawCenteredText(matrices, textRenderer, this.getTimer(), this.x + this.width / 2, this.y + 30, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        float look_x = (float)(this.x + (this.width / 2)) - mouseX;
        float look_y = (float)(this.y + (this.height / 2)) - mouseY;
        if(RenderEntity != null)
        {
            drawEntity(this.x + (this.width / 2), this.y + (this.height / 2) + 40, 30, look_x, look_y, RenderEntity);
        }
        else
        {
            drawEntity(this.x + (this.width / 2), this.y + (this.height / 2) + 40, 30, look_x, look_y, minecraftClient.player);
        }
    }
    public void renderCard(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        TextRenderer textRenderer = minecraftClient.textRenderer;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, CARD_TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.drawTexture(matrices, this.x, this.y, 0, 0, this.width / 2, this.height);
        this.drawTexture(matrices, this.x + this.width / 2, this.y, 146 - this.width / 2, 0, this.width / 2, this.height);
        this.renderBackground(matrices, minecraftClient, mouseX, mouseY);
        int j = this.active ? 16777215 : 10526880;
        drawCenteredText(matrices, textRenderer, this.getName(), this.x + this.width / 2, this.y + 15, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        drawCenteredText(matrices, textRenderer, this.getTimer(), this.x + this.width / 2, this.y + 30, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
        float look_x = (float)(this.x + (this.width / 2)) - mouseX;
        float look_y = (float)(this.y + (this.height / 2)) - mouseY;
        if(RenderEntity != null)
        {
            drawEntity(this.x + (this.width / 2), this.y + (this.height / 2) + 40, 30, look_x, look_y, RenderEntity);
        }
        else
        {
            drawEntity(this.x + (this.width / 2), this.y + (this.height / 2) + 40, 30, look_x, look_y, minecraftClient.player);
        }
    }

    protected MutableText getNarrationMessage() {
        return getNarrationMessage(this.getName());
    }

    public static MutableText getNarrationMessage(Text message) {
        return Text.translatable("gui.narrate.button", new Object[]{message});
    }

    protected void renderBackground(MatrixStack matrices, MinecraftClient client, int mouseX, int mouseY) {
    }

    public Text getTimer() {
        return this.Timer;
    }
    public Text getName() {
        return this.Name;
    }

    public void setTimer(Text timer) { this.Timer = timer;}
    public void setName(Text name) { this.Name = name;}
    public boolean isFocused() {
        return this.focused;
    }
    public boolean isNarratable() {
        return false;
    }

    public void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)Math.atan((double)(mouseX / 40.0F));
        float g = (float)Math.atan((double)(mouseY / 40.0F));
        MatrixStack matrixStack = RenderSystem.getModelViewStack();
        matrixStack.push();
        matrixStack.translate((double)x, (double)y, 1050.0);
        matrixStack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        MatrixStack matrixStack2 = new MatrixStack();
        matrixStack2.translate(0.0, 0.0, 1000.0);
        matrixStack2.scale((float)size, (float)size, (float)size);
        Quaternion quaternion = Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F);
        Quaternion quaternion2 = Vec3f.POSITIVE_X.getDegreesQuaternion(g * 20.0F);
        quaternion.hamiltonProduct(quaternion2);
        matrixStack2.multiply(quaternion);
        float h = entity.bodyYaw;
        float i = entity.getYaw();
        float j = entity.getPitch();
        float k = entity.prevHeadYaw;
        float l = entity.headYaw;
        entity.bodyYaw = 180.0F + f * 20.0F;
        entity.setYaw(180.0F + f * 40.0F);
        entity.setPitch(-g * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.prevHeadYaw = entity.getYaw();
        DiffuseLighting.method_34742();
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        quaternion2.conjugate();
        entityRenderDispatcher.setRotation(quaternion2);
        entityRenderDispatcher.setRenderShadows(false);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, matrixStack2, immediate, 15728880);
        });
        immediate.draw();
        entityRenderDispatcher.setRenderShadows(true);
        entity.bodyYaw = h;
        entity.setYaw(i);
        entity.setPitch(j);
        entity.prevHeadYaw = k;
        entity.headYaw = l;
        matrixStack.pop();
        RenderSystem.applyModelViewMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of();
    }

    @Override
    public boolean isMouseOver(double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + getWidth() && mouseY >= y && mouseY < y + getHeight();
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
    }

    @Override
    public SelectionType getType() {
        return SelectionType.NONE;
    }
}
