package ru.liga.telegramBotService.utils;

public class Constants {
    public static final String TUTORIAL =
            "Если вы хотите изменить эти параметры, используйте команды:\n" +
                    "\t-rate <source>, где source это доступный источник валют\n" +
                    "\t-alg <algorithm>, где algorithm это доступный аолгоритм предсказаний\n" +
                    "\t-prd <period>, где period это доступный период предсказаний.\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "\t-rate USA -alg AVERAGE -prd DAY";

    public static final String TUTORIAL_SOURCE =
            "Если вы хотите изменить параметр источника валют, используйте каоманду:\n" +
                    "\t-rate <source>, где source - источник валют.\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "\t-rate USA";

    public static final String TUTORIAL_ALGORITHM =
            "Если вы хотите изменить параметр алгоритма рассчёта, используйте команду:\n" +
                    "\t-alg <algorithm>, где algorithm - доступный алгоритм\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "\t-alg AVERAGE";

    public static final String TUTORIAL_PERIOD =
            "Если вы хотите изменить параметр периода рассчёта, используйте команду:\n" +
                    "\t-prd <period>, где period - доступный период\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "\t-prd DAY";
}
