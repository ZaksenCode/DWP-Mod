package zaksen.dwp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import zaksen.dwp.Dwp;
import zaksen.dwp.event.KeyInputHandler;
import zaksen.dwp.screen.ScreenTextures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Environment(EnvType.CLIENT)
public class DwpClient implements ClientModInitializer
{
    public static class Boss
    {
        Text timer;
        Text name;
        LivingEntity bossEntity;

        public Boss(Text name, @Nullable LivingEntity entity, Text timer)
        {
            this.name = name;
            if(entity != null)
            {
                this.bossEntity = entity;
            }
            this.timer = timer;
        }
        public Text getName()
        {
            return this.name;
        }
        public Text getTimer() { return this.timer; }
        public LivingEntity getBossEntity()
        {
            return this.bossEntity;
        }
        public void setTimer(String newTime)
        {
            this.timer = Text.of(newTime);
        }

        int ticker;
        public void tick()
        {
            if(ticker > 0)
            {
                ticker -= 1;
            }
            else
            {
                updateTimer();
                ticker = 20;
            }
        }


        public void updateTimer() {
            List<Integer> numbers = new ArrayList<>(Arrays.stream(getTimerValue()).boxed().toList());
            var seconds = (numbers.get(0) * 3600) + (numbers.get(1) * 60) + numbers.get(2);
            if(seconds > 3600){
                seconds--;
                int h = seconds / 3600;
                int m = (seconds - h * 3600) / 60;
                int s = (seconds - h * 3600) - m * 60;
                Dwp.LOG.info("Sec: " + seconds + " → " + h + " ч. " + m + " мин. " + s + " сек.");
                this.setTimer(h + " ч. " + m + " мин. " + s + " сек.");
            }
            else if (3600 > seconds && seconds > 0) {
                seconds--;
                int m = seconds / 60;
                int s = seconds - m * 60;
                Dwp.LOG.info("Sec: " + seconds + " → " + m + " мин. " + s + " сек.");
                this.setTimer(m + " мин. " + s + " сек.");
            }
            else{
                this.timer = Text.of(0 + " мин. " + 0 + " сек.");
            }
        }
        public int[] getTimerValue()
        {
            String timerStr = getTimer().getString();
            int[] result = new int[3];
            if(timerStr != null)
            {
                int counter = 2;
                timerStr = timerStr.replaceAll("ч.","").replaceAll("мин.","").replaceAll("сек.","");
                String[] numbers = timerStr.split("  ");
                List<String> numbers2 = new ArrayList<>(Arrays.stream(numbers).toList());
                Collections.reverse(numbers2);
                for(String number : numbers2)
                {
                    number = number.replaceAll(" ", "");
                    result[counter] = Integer.parseInt(number);
                    counter--;
                }
            }
            else
            {
                result = null;
            }
            return result;
        }
    }

    public static List<Boss> Bosses = new ArrayList<Boss>();
    public static String lastBossName;
    @Override
    public void onInitializeClient()
    {
        KeyInputHandler.register();
        ScreenTextures.loadThemes();
        Bosses.add(new Boss(Text.of("Древний Лучник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Слизень"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Стальной Страж"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Кошмар"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Близнецы"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Повелитель Огня"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Паучиха"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Утопленник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Колдун"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Смерть"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Наездник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Разбойник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Лавовый Куб"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Варден"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Призрачный Охотник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Чёрный Дракон"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Гигант"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Проклятый Легион"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Монстр"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Некромант"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Пожиратель Тьмы"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Чудовище"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Октопус"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Кузнец"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Могущественный Шалкер"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Заклинатель"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Мёртвый Всадник"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Кобольд"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Самурай"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Повелитель Мёртвых"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Теневой Лорд"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Гигантская Черепаха"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Голиаф"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Разрушитель"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Снежный Монстр"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Крик"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Спектральный Куб"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Тень"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Синтия"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Магнус"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Вестник Ада"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Цербер"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Ифрит"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Бафомет"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Пиглин"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Королева Пиглинов"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Хоглин"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Зомби Пиглин"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Брутальный Пиглин"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Магма"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Зоглин"), null, Text.of("0 мин. 0 сек.")));
        Bosses.add(new Boss(Text.of("Адский Рыцарь"), null, Text.of("0 мин. 0 сек.")));
    }
}
