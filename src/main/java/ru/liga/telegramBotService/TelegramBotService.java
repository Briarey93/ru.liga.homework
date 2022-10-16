package ru.liga.telegramBotService;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.telegramBotService.commands.StartCommand;
import ru.liga.telegramBotService.commands.HelpCommand;

@Slf4j
public class TelegramBotService extends TelegramLongPollingCommandBot {

    private final String BOT_NAME;
    private final String BOT_TOKEN;

    public TelegramBotService(String botName, String botToken) {
        super();
        BOT_NAME = botName;
        BOT_TOKEN = botToken;

        register(new StartCommand("start", "Старт"));

        register(new HelpCommand("help", "Помощь"));

        log.info("TEST: Bot Started");
        log.debug("TEST: Bot Started");
        log.debug("TEST: Bot Started");
        log.debug("TEST: Bot Started");

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }
}
