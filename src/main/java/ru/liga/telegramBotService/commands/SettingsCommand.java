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
                                "\t-rate <source>, где source это доступный источник валют\n" +
                                "\t-alg <algorithm>, где algorithm это доступный аолгоритм предсказаний\n" +
                                "\t-prd <period>, где period это доступный период предсказаний.\n\n" +
                                "\uD83D\uDC49Пример использования:\n" +
                                "\t-rate USA -alg AVERAGE -prd DAY",
                        settings.getSource(), settings.getAlgorithm(), settings.getPeriod()));

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}