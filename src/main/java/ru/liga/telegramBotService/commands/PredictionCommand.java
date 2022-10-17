package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.predictionService.CurrencyStatistic;
import ru.liga.predictionService.PredictionService;
import ru.liga.telegramBotService.TelegramBotService;
import ru.liga.telegramBotService.utils.Settings;
import ru.liga.telegramBotService.utils.Utils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class PredictionCommand extends ServiceCommand {

    private static final String SOURCE_PATH = "src/main/resources/RC_F01_01_2002_T01_10_2022_";
    private static final String FORMAT = ".csv";

    private final Map<String, Integer> PERIOD_VALUE = new HashMap<String, Integer>() {{
        put("day", 1);
        put("week", 7);
        put("month", 30);
    }};


    public PredictionCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        Long chatId = chat.getId();
        Settings settings = TelegramBotService.getUserSettings(chatId);

        StringBuilder combineMsg = new StringBuilder();
        combineMsg.append(String.format("\"rate %s %s\"\n", settings.getSource(), settings.getPeriod()));

        int lengthPeriod = PERIOD_VALUE.get(settings.getPeriod());

        CurrencyStatistic predictedCurrencyStatistic = new PredictionService(
                SOURCE_PATH + settings.getSource() + FORMAT,
                settings.getSource(),
                settings.getAlgorithm(),
                lengthPeriod)
                .executeApplication();

        if (predictedCurrencyStatistic == null) {
            log.error(String.format("Пользователь %s. Команда %s вышла с ошибкой.", userName,
                    this.getCommandIdentifier()));
            return;
        }

        parseCurrencyStatistic(combineMsg, lengthPeriod, predictedCurrencyStatistic);
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, combineMsg.toString());

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));

    }

    private void parseCurrencyStatistic(StringBuilder combineMsg, int lengthPeriod, CurrencyStatistic predictedCurrencyStatistic) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy", Locale.ENGLISH);

        for (int i = 0; i < lengthPeriod; i++) {
            combineMsg.append(String.format("    %s - %s\n",
                    predictedCurrencyStatistic.getDates().get(i).format(formatter),
                    predictedCurrencyStatistic.getCurrencyStatistics().get(i)));
        }
    }
}
