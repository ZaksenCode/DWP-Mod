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
                updateTimer();
                ticker = 20;
            }
            else
            {
                ticker -= 1;
            }
        }

        public void updateTimer()
        {
            int[] numbers = getTimerValue();
            for(int i = 0; i < numbers.length / 2; i++)
            {
                int temp = numbers[i];
                numbers[i] = numbers[numbers.length - i - 1];
                numbers[numbers.length - i - 1] = temp;
            }
            boolean null_sec = false;
            boolean null_min = false;
            boolean null_hour = false;
            boolean next_minus = false;
            for(int number : numbers)
            {
                if(next_minus)
                {
                    if(number == 0)
                    {
                        continue;
                    }
                    else
                    {
                        number -= 1;
                        next_minus = false;
                        continue;
                    }
                }

                if(number == 0 && !null_sec)
                {
                    null_sec = true;
                }
                else if (number == 0 && !null_min)
                {
                    null_min = true;
                }
                else if (number == 0 && !null_hour)
                {
                    null_hour = true;
                }

                if(null_sec && null_min && null_hour)
                {
                    timer = null;
                    break;
                }

                if(!null_sec)
                {
                    number -= 1;
                }
                else
                {
                    number = 60;
                    next_minus = true;
                    continue;
                }

                if(!null_min)
                {
                    number -= 1;
                }
                else
                {
                    number = 60;
                    next_minus = true;
                }
            }
        }

        public int[] getTimerValue()
        {
            String timerStr = getTimer().getString();
            int[] result = new int[3];
            if(timerStr != null)
            {
                int counter = 0;
                timerStr = timerStr.replaceAll("ч.","").replaceAll("мин.","").replaceAll("сек.","");
                String[] numbers = timerStr.split("  ");
                for(String number : numbers)
                {
                    number = number.replaceAll(" ", "");
                    result[counter] = Integer.parseInt(number);
                    counter++;
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
