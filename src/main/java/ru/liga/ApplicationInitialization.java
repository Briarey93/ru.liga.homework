package ru.liga;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.telegramBotService.TelegramBotService;

import ru.liga.consoleAppService.ConsoleAppService;

import java.util.Map;

public class ApplicationInitialization {

    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
//        ConsoleAppService.consoleAppStart();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramBotService(getenv.get("BOT_NAME"), getenv.get("BOT_TOKEN")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
