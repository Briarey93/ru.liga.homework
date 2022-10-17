package ru.liga.telegramBotService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.telegramBotService.commands.*;
import ru.liga.telegramBotService.utils.Settings;
import ru.liga.telegramBotService.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Бот.
 */
@Slf4j
public class TelegramBotService extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    /**
     * Обработчик сообщения, не являющегося командой.
     */
    private final NonCommand nonCommand;

    /**
     * Настройки по умолчанию.
     */
    @Getter
    private static final Settings defaultSettings = new Settings("USA", "AVERAGE", "WEEK");

    /**
     * Настройки для разных пользователей. Ключ - уникальный id чата
     */
    @Getter
    private static Map<Long, Settings> userSettings;

    /**
     * Конструктор, инициализирующий бота.
     *
     * @param botName  имя бота.
     * @param botToken токен бота.
     */
    public TelegramBotService(String botName, String botToken) {
        super();
        BOT_NAME = botName;
        BOT_TOKEN = botToken;
        log.debug("Имя бота и токен получены");

        this.nonCommand = new NonCommand();
        log.debug("Класс обработки сообщения, не являющегося командой, создан");

        register(new StartCommand("start", "Старт"));
        log.debug("Команда start создана");

        register(new HelpCommand("help", "Помощь"));
        log.debug("Команда help создана");

        register(new SettingsCommand("settings", "Мои настройки"));
        log.debug("Команда settings создана");

        register(new SourceCommand("source", "Источник"));
        log.debug("Команда source создана");

        register(new AlgorithmCommand("algorithm", "Алгоритм"));
        log.debug("Команда algorithm создана");

        register(new PeriodCommand("period", "Период"));
        log.debug("Команда period создана");

        register(new PredictionCommand("predict", "Предсказание"));
        log.debug("Команда predict создана");

        userSettings = new HashMap<>();
        log.info("Бот создан.");
    }

    /**
     * Получить имя бота.
     *
     * @return имя бота.
     */
    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    /**
     * Получить токен бота.
     *
     * @return токен бота.
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    /**
     * Получить уникальные настройки пользователя.
     *
     * @param chatId id пользователя.
     * @return настройки пользователя.
     */
    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = TelegramBotService.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    /**
     * Ответ на запрос, не являющийся командой
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = Utils.getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
    }

    /**
     * Установить ответ на пользовательское сообщение, не являющийся командой.
     *
     * @param chatId   id пользователя.
     * @param userName имя пользователя.
     * @param text     пользовательское сообщение.
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Сообщение, не являющееся командой. Пользователь: %s", e.getMessage(),
                    userName));
            e.printStackTrace();
        }
    }
}
