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
import java.util.Locale;

@Slf4j
public class PredictionCommand extends ServiceCommand {

    private static final String SOURCE_PATH = "src/main/resources/RC_F01_01_2002_T01_10_2022_";
    private static final String FORMAT = ".csv";

    private static final String TYPE_DOLLAR = "DOLLAR_USA";
    private static final String TYPE_EURO = "EURO";
    private static final String TYPE_LIRA = "Turkish_LIRA";

    private static final String[] SOURCE_TYPE = {
            TYPE_DOLLAR,
            TYPE_EURO,
            TYPE_LIRA};

    private static final String[] ALGORITHM_TYPE = {
            "Average"};

    private static final int[] LENGTH_PERIOD = {1, 7, 30};


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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy", Locale.ENGLISH);
        StringBuilder combineMsg = new StringBuilder();

        int typeCurrency = 0;
        int typeAlgorithm = 0;
        int lengthPeriod = 1;

        CurrencyStatistic predictedCurrencyStatistic = new PredictionService(
                SOURCE_PATH + SOURCE_TYPE[typeCurrency] + FORMAT,
                SOURCE_TYPE[typeCurrency],
                ALGORITHM_TYPE[typeAlgorithm],
                LENGTH_PERIOD[lengthPeriod])
                .executeApplication();

        for (int i = 0; i < LENGTH_PERIOD[lengthPeriod]; i++) {
            combineMsg.append("\t")
                    .append(predictedCurrencyStatistic.getDates().get(i).format(formatter))
                    .append(" - ")
                    .append(predictedCurrencyStatistic.getCurrencyStatistics().get(i))
                    .append("\n");
        }
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName, combineMsg.toString());


        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
