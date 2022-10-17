package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.telegramBotService.utils.Constants;
import ru.liga.telegramBotService.utils.Utils;

@Slf4j
public class AlgorithmCommand extends ServiceCommand {

    public AlgorithmCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        StringBuilder sendMsg = new StringBuilder("Доступные алгоритмы:\n");
        for (int i = 0; i < Constants.getALGORITHM().size(); i++) {
            sendMsg.append(String.format("  - %s - %s\n",
                    Constants.getALGORITHM().get(i),
                    Constants.getALGORITHM_DESCRIPTION().get(i)));
        }
        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                sendMsg.append(String.format("\n%s",
                        Constants.getTUTORIAL_ALGORITHM())).toString());

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
