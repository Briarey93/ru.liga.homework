package ru.liga.telegramBotService;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.telegramBotService.commands.SettingsCommand;
import ru.liga.telegramBotService.commands.StartCommand;
import ru.liga.telegramBotService.commands.HelpCommand;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TelegramBotService extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    private static final Settings defaultSettings = new Settings("USA", "average","week");

    /**
     * Настройки файла для разных пользователей. Ключ - уникальный id чата
     */
    @Getter
    private static Map<Long, Settings> userSettings;

    public TelegramBotService(String botName, String botToken) {
        super();
        BOT_NAME = botName;
        BOT_TOKEN = botToken;

        register(new StartCommand("start", "Старт"));
        log.debug("Команда start создана");

        register(new HelpCommand("help", "Помощь"));
        log.debug("Команда help создана");

        register(new SettingsCommand("settings", "Мои настройки"));
        log.debug("Команда settings создана");

        userSettings = new HashMap<>();
        log.info("TEST: Bot Started");
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public static Settings getUserSettings(Long chatId) {
        Map<Long, Settings> userSettings = TelegramBotService.getUserSettings();
        Settings settings = userSettings.get(chatId);
        if (settings == null) {
            return defaultSettings;
        }
        return settings;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }
}
