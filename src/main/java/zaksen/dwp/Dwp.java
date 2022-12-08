package zaksen.dwp;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zaksen.dwp.misc.ModConfigs;

public class Dwp implements ModInitializer {
    public static final String MOD_ID = "dwp";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ModConfigs.registerConfigs();
    }
}
