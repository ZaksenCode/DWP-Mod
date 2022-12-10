package zaksen.dwp;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zaksen.dwp.config.DwpConfig;

public class Dwp implements ModInitializer {
    public static final String MOD_ID = "dwp";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    public static DwpConfig CONFIG;
    @Override
    public void onInitialize() {
        AutoConfig.register(DwpConfig.class, GsonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(DwpConfig.class).getConfig();;
    }
}
