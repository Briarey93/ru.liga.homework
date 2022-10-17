package ru.liga.telegramBotService.utils;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Агрегация констант для быстрого доступа.
 */
public class Constants {

    @Getter
    private static final String TUTORIAL =
            "Если вы хотите изменить эти параметры, используйте команды:\n" +
                    "  -rate <source>, где source это доступный источник валют\n" +
                    "  -alg <algorithm>, где algorithm это доступный аолгоритм предсказаний\n" +
                    "  -prd <period>, где period это доступный период предсказаний.\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "  -rate USA -alg AVERAGE -prd DAY";

    @Getter
    private static final String TUTORIAL_SOURCE =
            "Если вы хотите изменить параметр источника валют, используйте каоманду:\n" +
                    "  -rate <source>, где source - источник валют.\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "  -rate USA";

    @Getter
    private static final String TUTORIAL_ALGORITHM =
            "Если вы хотите изменить параметр алгоритма рассчёта, используйте команду:\n" +
                    "  -alg <algorithm>, где algorithm - доступный алгоритм\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "  -alg AVERAGE";

    @Getter
    private static final String TUTORIAL_PERIOD =
            "Если вы хотите изменить параметр периода рассчёта, используйте команду:\n" +
                    "  -prd <period>, где period - доступный период\n\n" +
                    "\uD83D\uDC49Пример использования:\n" +
                    "  -prd DAY";


    @Getter
    private static final List<String> SOURCE =
            Arrays.asList("USA",
                    "EURO",
                    "LIRA");
    @Getter
    private static final List<String> SOURCE_DESCRIPTION =
            Arrays.asList("доллар США",
                    "Евро",
                    "Турецкая лира");

    @Getter
    private static final List<String> ALGORITHM =
            Arrays.asList("AVERAGE");
    @Getter
    private static final List<String> ALGORITHM_DESCRIPTION =
            Arrays.asList("среднее арифметическое на основании 7 последних курсов валют");

    @Getter
    private static final List<String> PERIOD =
            Arrays.asList("DAY",
                    "WEEK",
                    "MONTH");
    @Getter
    private static final List<String> PERIOD_DESCRIPTION =
            Arrays.asList("1 день",
                    "7 дней",
                    "30 дней");
}
