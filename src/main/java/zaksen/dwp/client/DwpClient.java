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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        Boolean update;
        public void tick()
        {
            if(ticker > 0)
            {
                ticker -= 1;
                update = false;
            }
            else
            {
                updateTimer();
                ticker = 20;
                update = true;
            }
        }

        public int getTimerSeconds()
        {
            List<Integer> numbers = new ArrayList<>(Arrays.stream(getTimerValue()).boxed().toList());
            return (numbers.get(0) * 3600) + (numbers.get(1) * 60) + numbers.get(2);
        }

        public void updateTimer() {
            int seconds = getTimerSeconds();
            if(seconds > 3600){
                seconds--;
                int h = seconds / 3600;
                int m = (seconds - h * 3600) / 60;
                int s = (seconds - h * 3600) - m * 60;
                this.setTimer(h + " ч. " + m + " мин. " + s + " сек.");
            }
            else if (3600 > seconds && seconds >= 60) {
                seconds--;
                int m = seconds / 60;
                int s = seconds - m * 60;
                this.setTimer(m + " мин. " + s + " сек.");
            }
            else if (60 > seconds && seconds > 0)
            {
                seconds--;
                int s = seconds;
                this.setTimer(s + " сек.");
            }
            else{
                this.timer = Text.of(0 + " сек.");
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

    public static List<String> getNearestBosses()
    {
        List<String> topNearestBosses = new ArrayList<>();
        int iteration = 0;
        int[] secMas = new int[Dwp.CONFIG.nearestBossesCount];
        List<Boss> tickingBosses = new ArrayList<>();
        for(Boss boss : Bosses)
        {
            int seconds = boss.getTimerSeconds();
            if(seconds > 0 && iteration < Dwp.CONFIG.nearestBossesCount)
            {
                secMas[iteration] = seconds;
                iteration++;
                tickingBosses.add(boss);
            }
        }
        secMas = SortShell(secMas);
        List<Integer> secMas2 = new ArrayList<>(Arrays.stream(secMas).boxed().toList());
        Collections.reverse(secMas2);
        int iteration2 = 0;
        for(int i : secMas2)
        {
            for(Boss boss : tickingBosses)
            {
                if(boss.getTimerSeconds() == i && i != 0)
                {
                    if(iteration2 < Dwp.CONFIG.nearestBossesCount && iteration2 < secMas2.size() && !topNearestBosses.contains(boss.getName().getString() + " - " + boss.getTimer().getString()))
                    {
                        topNearestBosses.add(secMas2.indexOf(i), boss.getName().getString() + " - " + boss.getTimer().getString());
                        iteration2++;
                    }
                }
            }
        }

        return topNearestBosses;
    }

    static int[] SortShell(int[] mas)
    {
        int j;
        int d = mas.length / 2;
        while (d > 0)
        {
            for (int i = 0; i < (mas.length - d); i++)
            {
                j = i;
                while ((j >= 0) && (mas[j] > mas[j + d]))
                {
                    int z = mas[j];
                    mas[j] = mas[j + d];
                    mas[j + d] = z;
                    j -= d;
                }
            }
            d /= 2;
        }
        return mas;
    }

    @Override
    public void onInitializeClient()
    {
        KeyInputHandler.register();
        ScreenTextures.loadThemes();
        Bosses.add(new Boss(Text.of("Древний Лучник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Слизень"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Стальной Страж"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Кошмар"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Близнецы"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Повелитель Огня"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Паучиха"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Утопленник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Колдун"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Смерть"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Наездник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Разбойник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Лавовый куб"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Варден"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Призрачный Охотник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Чёрный Дракон"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Гигант"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Проклятый Легион"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Монстр"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Некромант"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Пожиратель Тьмы"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Чудовище"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Октопус"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Кузнец"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Могущественный Шалкер"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Заклинатель"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Мёртвый Всадник"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Кобольд"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Самурай"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Повелитель Мёртвых"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Теневой Лорд"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Гигантская Черепаха"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Голиаф"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Разрушитель"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Снежный Монстр"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Крик"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Спектральный куб"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Тень"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Синтия"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Магнус"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Вестник Ада"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Цербер"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Ифрит"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Бафомет"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Пиглин"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Королева Пиглинов"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Хоглин"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Зомби Пиглин"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Брутальный Пиглин"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Магма"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Зоглин"), null, Text.of("0 сек.")));
        Bosses.add(new Boss(Text.of("Адский Рыцарь"), null, Text.of("0 сек.")));
    }
}
