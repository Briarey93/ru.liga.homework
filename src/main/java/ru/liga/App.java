package ru.liga;

import ru.liga.currencyService.CurrencyStatistic;
import ru.liga.readerService.SourceReader;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;


/**
 * Course stat.
 */
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
        int typePeriod;

        System.out.println(TYPE_SOURCE);
        typeCurrency = tryReadType(in);
        if (isNotInRange(typeCurrency, 1, 3)) {
            return;
        }

        System.out.println(TYPE_PERIOD);
        typePeriod = tryReadType(in);
        if (isNotInRange(typePeriod, 1, 2)) {
            return;
        }

        executeApplication(SOURCE[typeCurrency], typePeriod);
    }

    /**
     * Метод логики и последовательности действий приложения.
     *
     * @param source     - исходные данные.
     * @param typePeriod - период искомых данных.
     */
    private static void executeApplication(String source, int typePeriod) {
        SourceReader sourceReader = new SourceReader();
        sourceReader.setup(source);

        CurrencyStatistic currencyStatistic;
        try {
            currencyStatistic = sourceReader.readSource();
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла.");
            return;
        }

        currencyStatistic.predict();

        printResult(typePeriod, currencyStatistic);
    }

    private static boolean isNotInRange(int value, int left, int right) {
        if (value < left || value > right) {
            System.out.println(EXIT_MSG);
            return true;
        }
        return false;
    }

    private static int tryReadType(Scanner in) {
        try {
            return in.nextInt();
        } catch (Exception e) {
            return 0;
        }
    }

    private static void printResult(int stateType, CurrencyStatistic currencyStatistic) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy - ", Locale.ENGLISH);

        switch (stateType) {
            case (1):
                System.out.print("\"rate TRY tomorrow\" ");
                System.out.print(currencyStatistic.getDate().get(0).format(formatter));
                System.out.printf("%.2f\n", currencyStatistic.getCurrencyTomorrow());
                break;
            case (2):
                List<LocalDate> resultDate = currencyStatistic.getDate();
                List<BigDecimal> resultCurrencyStat = currencyStatistic.getCurrencyWeek();

                System.out.println("\"rate USD week\"");
                for (int i = 0; i < resultDate.size(); i++) {
                    System.out.print("\t" + resultDate.get(i).format(formatter));
                    System.out.printf("%.2f\n", resultCurrencyStat.get(i));
                }
                break;
        }
    }
}
