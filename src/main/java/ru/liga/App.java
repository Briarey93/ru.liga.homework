package ru.liga;

import lombok.Setter;
import ru.liga.predictionService.PredictionExecutor;

import java.util.Scanner;


/**
 * Course stat.
 */
@Setter
public class App {

    public static final String SOURCE_PATH = "src/main/resources/RC_F01_01_2002_T01_10_2022_";
    public static final String FORMAT = ".csv";

    private static final String TYPE_DOLLAR = "DOLLAR_USA";
    private static final String TYPE_EURO = "EURO";
    private static final String TYPE_LIRA = "Turkish_LIRA";

    private final static String[] SOURCE = new String[]{"",
            SOURCE_PATH + TYPE_DOLLAR + FORMAT,
            SOURCE_PATH + TYPE_EURO + FORMAT,
            SOURCE_PATH + TYPE_LIRA + FORMAT};

    private static final String TYPE_SOURCE = "Choice currency:\n" +
            "1 - " + TYPE_DOLLAR + "\n" +
            "2 - " + TYPE_EURO + "\n" +
            "3 - " + TYPE_LIRA + " Lira\n" +
            "Other - Exit\n";

    private static final String TYPE_PERIOD = "Choice period:\n" +
            "1 - tomorrow currency course\n" +
            "2 - next week currency course\n" +
            "Other - Exit\n";

    public static final String EXIT_MSG = "Exit! goodBye.";

    /**
     * Точка входа в программу.
     * Запрашиваем у пользователя входные параметры.
     *
     * @param args - входные параметры.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int typeCurrency;
        int lengthPeriod;

        System.out.println(TYPE_SOURCE);
        typeCurrency = tryReadType(in);
        if (isNotInRange(typeCurrency, 1, 3)) {
            return;
        }

        System.out.println(TYPE_PERIOD);
        lengthPeriod = tryReadType(in);
        if (isNotInRange(lengthPeriod, 1, 2)) {
            return;
        }

        new PredictionExecutor(SOURCE[typeCurrency], lengthPeriod).executeApplication();
    }

    private static boolean isNotInRange(final int value, final int left, final int right) {
        if (value < left || value > right) {
            System.out.println(EXIT_MSG);
            return true;
        }
        return false;
    }

    private static int tryReadType(final Scanner in) {
        try {
            return in.nextInt();
        } catch (Exception e) {
            return 0;
        }
    }
}
