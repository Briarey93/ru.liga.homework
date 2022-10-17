package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.telegramBotService.utils.Settings;
import ru.liga.telegramBotService.TelegramBotService;
import ru.liga.telegramBotService.utils.Utils;

@Slf4j
public class SettingsCommand extends ServiceCommand {

    public SettingsCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        Long chatId = chat.getId();
        Settings settings = TelegramBotService.getUserSettings(chatId);
        sendAnswer(absSender, chatId, this.getCommandIdentifier(), userName,
                String.format("*Текущие настройки*\n" +
                                "- валюта - %s\n" +
                                "- алгоритм - %s\n" +
                                "- период - %s\n\n" +
                                "Если вы хотите изменить эти параметры, используйте команды:\n" +
                                "-rate source, где source это доступный источник валют\n" +
                                "-rate algorithm, где algorithm это доступный аолгоритм предсказаний\n" +
                                "-rate period, где period это доступный период предсказаний.\n" +
                                "\uD83D\uDC49Пример использования:\n" +
                                "-rate dollarUSA -algorithm average -period day",
                        settings.getAlgorithm(), settings.getPeriod(), settings.getSource()));

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
