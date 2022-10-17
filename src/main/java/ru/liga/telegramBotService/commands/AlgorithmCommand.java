package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
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

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Доступные алгоритмы:\n" +
                        "\t- AVERAGE - среднее арифметическое на основании 7 последних курсов валют.\n\n" +
                        "Если вы хотите изменить параметр алгоритма рассчёта, используйте команду:\n" +
                        "\t-alg <algorithm>, где algorithm  доступный алгоритм\n\n" +
                        "\uD83D\uDC49Пример использования:\n" +
                        "\t-alg AVERAGE");

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
