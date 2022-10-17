package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import ru.liga.telegramBotService.utils.Utils;

@Slf4j
public class PeriodCommand extends ServiceCommand {

    public PeriodCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);

        log.debug(String.format("Пользователь %s. Начато выполнение команды %s", userName,
                this.getCommandIdentifier()));

        sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Доступные периоды:\n" +
                        "\t- DAY - 1 день\n" +
                        "\t- WEEK - 7 дней\n" +
                        "\t- MONTH - 30 дней\n\n" +
                        "Если вы хотите изменить параметр периода рассчёта, используйте команду:\n" +
                        "\t-prd <period>, где period доступный период\n\n" +
                        "\uD83D\uDC49Пример использования:\n" +
                        "\t-prd DAY");

        log.debug(String.format("Пользователь %s. Завершено выполнение команды %s", userName,
                this.getCommandIdentifier()));
    }
}
