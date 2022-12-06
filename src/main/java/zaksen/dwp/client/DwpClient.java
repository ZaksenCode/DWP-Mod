package zaksen.dwp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import zaksen.dwp.event.KeyInputHandler;
import zaksen.dwp.screen.ScreenTextures;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class DwpClient implements ClientModInitializer
{
    public static class Boss
    {
        Text name = Text.empty();
        LivingEntity bossEntity;
        public Boss(Text name, @Nullable LivingEntity entity)
        {
            this.name = name;
            if(entity != null)
            {
                this.bossEntity = entity;
            }
        }
        public Text getName()
        {
            return this.name;
        }
        public LivingEntity getBossEntity()
        {
            return this.bossEntity;
        }
    }
    public static List<Boss> Bosses = new ArrayList<Boss>();
    @Override
    public void onInitializeClient()
    {
        KeyInputHandler.register();
        ScreenTextures.loadThemes();
        Bosses.add(new Boss(Text.translatable("dwp.bosses.boss1"), null));
        Bosses.add(new Boss(Text.translatable("dwp.bosses.boss2"), null));
        Bosses.add(new Boss(Text.translatable("dwp.bosses.boss3"), null));
        Bosses.add(new Boss(Text.translatable("dwp.bosses.boss4"), null));
        Bosses.add(new Boss(Text.translatable("dwp.bosses.boss5"), null));
    }
}
