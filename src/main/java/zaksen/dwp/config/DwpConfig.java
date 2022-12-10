package zaksen.dwp.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static zaksen.dwp.Dwp.MOD_ID;

@Config(name = MOD_ID)
public class DwpConfig implements ConfigData
{
    @Override
    public void validatePostLoad() throws ValidationException {
        ConfigData.super.validatePostLoad();
    }

    public int scrollValue = 150;
    public static int minBosses = 3;
    public boolean  showBossesList = false;
    public int bossesListX = 10, bossesListY = 10;
    public int fontColor = 16777215;
}
