package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.telegramBotService.utils.Constants;
import ru.liga.telegramBotService.utils.Utils;

@Slf4j
public class HelpCommand extends ServiceCommand {

    public HelpCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Привет, я бот, который поможет вам рассчитать курс валюты на будующие даты," +
                        " используя специальные алгоритмы предсказания.\n" +
                        "❗*Список команд*\n" +
                        "  /start - включение бота\n" +
                        "  /settings - показывает текущие настройки предсказания\n" +
                        "  /predict - выводит результат предсказания\n" +
                        "  /source - показать доступные источники валют\n" +
                        "  /algorithm - показать доступные алгоритмы предсказаний\n" +
                        "  /period - показать доступные периоды предсказаний\n" +
                        "  /help - помощь\n\n" +
                        "По умолчанию я сформирую предсказание для валюты доллар США на период недели," +
                        "рассчитанное по алгоритму Среднее арифметическое значение " +
                        "на основании 7 последних курсов.\n" +
                        Constants.TUTORIAL +
                        "\n\nЖелаю удачи\uD83D\uDE42");

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
