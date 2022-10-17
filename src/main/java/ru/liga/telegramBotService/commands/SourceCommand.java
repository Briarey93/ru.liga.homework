package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.telegramBotService.utils.Constants;
import ru.liga.telegramBotService.utils.Utils;

/**
 * Команда /rate.
 */
@Slf4j
public class SourceCommand extends ServiceCommand {

    public SourceCommand(String identifier, String description) {
        super(identifier, description);
    }

    /**
     * Действие бота на команду /source.
     * Выводит список доступных алгорифмов и их краткое описание.
     * Так же выводит пример команды -rate.
     */
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        StringBuilder sendMsg = new StringBuilder("Доступные источники валют:\n");
        for (int i = 0; i < Constants.getSOURCE().size(); i++) {
            sendMsg.append(String.format("  - %s - %s\n",
                    Constants.getSOURCE().get(i),
                    Constants.getSOURCE_DESCRIPTION().get(i)));
        }
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                sendMsg.append(String.format("\n%s",
                        Constants.getTUTORIAL_SOURCE())).toString());

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
