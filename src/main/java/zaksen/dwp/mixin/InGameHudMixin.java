package zaksen.dwp.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zaksen.dwp.Dwp;
import zaksen.dwp.client.DwpClient;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin
{

    @Shadow public abstract TextRenderer getTextRenderer();

    @Inject(at = @At("RETURN"), method = "render")
    public void onRender(MatrixStack matrices, float tickDelta, CallbackInfo ci)
    {
        if(Dwp.CONFIG != null)
        {
            if(Dwp.CONFIG.showBossesList)
            {
                this.getTextRenderer().drawWithShadow(matrices, "Ближайшие Боссы:", Dwp.CONFIG.bossesListX, Dwp.CONFIG.bossesListY, Dwp.CONFIG.fontColor);
                int startYValue = Dwp.CONFIG.bossesListY + 10;
                for(String boss : DwpClient.getNearestBosses())
                {
                    this.getTextRenderer().drawWithShadow(matrices, boss, Dwp.CONFIG.bossesListX, startYValue, Dwp.CONFIG.fontColor);
                    startYValue += 10;
                }
            }
        }
    }
}
