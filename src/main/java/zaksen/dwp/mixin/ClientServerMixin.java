package zaksen.dwp.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zaksen.dwp.client.DwpClient;

import java.util.function.BooleanSupplier;

@Mixin(ClientWorld.class)
public class ClientServerMixin
{
    @Inject(at = @At("TAIL"), method = "tick")
    public void onTick(BooleanSupplier shouldKeepTicking, CallbackInfo ci)
    {
        for(DwpClient.Boss boss : DwpClient.Bosses)
        {
            boss.tick();
        }
    }
}
