package ru.liga;

import ru.liga.predictionService.PredictionService;

import java.util.Scanner;


/**
 * Course stat.
 */
public class App {

    private static final String SOURCE_PATH = "src/main/resources/RC_F01_01_2002_T01_10_2022_";
    private static final String FORMAT = ".csv";

    private static final String TYPE_DOLLAR = "DOLLAR_USA";
    private static final String TYPE_EURO = "EURO";
    private static final String TYPE_LIRA = "Turkish_LIRA";

    private static final String[] SOURCE_TYPE = {
            TYPE_DOLLAR,
            TYPE_EURO,
            TYPE_LIRA};

    private static final String[] ALGORITHM_TYPE = {
            "Average"};

    private static final int[] LENGTH_PERIOD = {1, 7, 30};

    private static final String PRINT_CHOICE_SOURCE_TYPE_MSG =
            "Выберите интересующую вас валюту:\n" +
                    "1 - " + TYPE_DOLLAR + "\n" +
                    "2 - " + TYPE_EURO + "\n" +
                    "3 - " + TYPE_LIRA + "\n" +
                    "Other - Exit\n";

    private static final String PRINT_CHOICE_ALGORITHM_TYPE_MSG =
            "Выберите алгоритм предсказания курса:\n" +
                    "1 - Среднее арифметическое\n" +
                    "Other - Exit\n";

    private static final String PRINT_CHOICE_LENGTH_PERIOD_MSG =
            "Выберите период предсказания курса:\n" +
                    "1 - Среднее арифметическое на 1 день\n" +
                    "2 - Среднее арифметическое на 7 дней\n" +
                    "3 - Среднее арифметическое на 30 дней\n" +
                    "Other - Exit\n";

    private static final String EXIT_MSG = "Exit! goodBye.";


    /**
     * Точка входа в программу.
     * Запрашиваем у пользователя входные параметры.
     *
     * @param args - входные параметры.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int typeCurrency;
        int typeAlgorithm;
        int lengthPeriod = 0;

        System.out.println(PRINT_CHOICE_SOURCE_TYPE_MSG);
        typeCurrency = tryReadNextInteger(in) - 1;
        if (isNotInRange(typeCurrency, 0, SOURCE_TYPE.length)) {
            return;
        }

        System.out.println(PRINT_CHOICE_ALGORITHM_TYPE_MSG);
        typeAlgorithm = tryReadNextInteger(in) - 1;
        if (isNotInRange(typeAlgorithm, 0, ALGORITHM_TYPE.length)) {
            return;
        }

        if (typeAlgorithm == 0) {
            System.out.println(PRINT_CHOICE_LENGTH_PERIOD_MSG);
            lengthPeriod = tryReadNextInteger(in) - 1;
            if (isNotInRange(lengthPeriod, 0, LENGTH_PERIOD.length)) {
                return;
            }
        }

        new PredictionService(
                SOURCE_PATH + SOURCE_TYPE[typeCurrency] + FORMAT,
                SOURCE_TYPE[typeCurrency],
                ALGORITHM_TYPE[typeAlgorithm],
                LENGTH_PERIOD[lengthPeriod])
                .executeApplication();
    }

    private static boolean isNotInRange(
            final int value,
            final int left,
            final int right) {
        if (value < left || value > right) {
            System.out.println(EXIT_MSG);
            return true;
        }
        return false;
    }

    private static int tryReadNextInteger(final Scanner in) {
        try {
            return in.nextInt();
        } catch (Exception e) {
            return -1;
        }
    }
}
