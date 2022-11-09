package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Суперкласс для сервисных команд.
 */
@Slf4j
abstract class ServiceCommand extends BotCommand {

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Отправка ответа пользователю.
     */
    void sendAnswer(AbsSender absSender,
                    Long chatId,
                    String commandName,
                    String userName,
                    String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            log.error(String.format("Ошибка %s. Команда %s. Пользователь: %s",
                    e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}