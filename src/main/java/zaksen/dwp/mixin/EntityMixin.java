package zaksen.dwp.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zaksen.dwp.Dwp;
import zaksen.dwp.client.DwpClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static zaksen.dwp.client.DwpClient.lastBossName;

@Mixin(EntityS2CPacket.class)
public class EntityMixin
{
    public List<zaksen.dwp.client.DwpClient.Boss> Bosses = DwpClient.Bosses;
    @Inject(at = @At("RETURN"), method = "getEntity")
    public void getEntity(World world, CallbackInfoReturnable<Entity> cir)
    {
        try {
            if(cir != null)
            {
                Entity receivedEntry = cir.getReturnValue();
                if (receivedEntry != null && receivedEntry.getType() == EntityType.ARMOR_STAND) {
                    String entityName = Objects.requireNonNull(receivedEntry.getCustomName()).getString();
                    if (!entityName.equals("null")) {
                        if (entityName.contains("Босс")) {
                            if(!Objects.equals(lastBossName, entityName))
                            {
                                lastBossName = entityName;
                            }
                        }
                        if (entityName.contains("сек.")) {
                            if(lastBossName != null)
                            {
                                if(Bosses != null)
                                {
                                    for(DwpClient.Boss boss : Bosses)
                                    {
                                        if(boss.getName().getString().equals(lastBossName.replaceFirst("Босс ", "")))
                                        {
                                            boss.setTimer(entityName);
                                            Dwp.LOG.info(Arrays.toString(boss.getTimerAsValue()));
                                            lastBossName = null;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            Dwp.LOG.warn("EntityMixin: " + e);
        }
    }
}
