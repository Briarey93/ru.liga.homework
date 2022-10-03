package ru.liga;

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
            "2 - last week currency course\n" +
            "Other - Exit\n";

    public static final String EXIT_MSG = "Exit! goodBye.";

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String tmp;
        int typeCurrency;
        int typePeriod;

        System.out.println(TYPE_SOURCE);

        tmp = in.nextLine();
        if (isStringOfDigits(tmp)) {
            return;
        }

        typeCurrency = Integer.parseInt(tmp);
        if (isInRange(typeCurrency)) {
            return;
        }

        System.out.println(TYPE_PERIOD);

        tmp = in.nextLine();
        if (isStringOfDigits(tmp)) {
            return;
        }

        typePeriod = Integer.parseInt(tmp);
        if (isInRange(typePeriod)) {
            return;
        }

        SourceReader sourceReader = new SourceReader();
        sourceReader.setup(SOURCE[typeCurrency]);
        CurrencyStatistic currencyStatistic = sourceReader.readSource();

        printResult(typePeriod, currencyStatistic);
    }

    private static boolean isInRange(int value) {
        if (value > 3 || value < 1) {
            System.out.println(EXIT_MSG);
            return true;
        }
        return false;
    }

    private static boolean isStringOfDigits(String tmp) {
        for (char a :
                tmp.toCharArray()) {
            if (!Character.isDigit(a)) {
                System.out.println(EXIT_MSG);
                return true;
            }
        }
        return false;
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
                List<Double> resultCurrencyStat = currencyStatistic.getCurrencyWeek();

                System.out.println("\"rate USD week\"");
                for (int i = 0; i < resultDate.size(); i++) {
                    System.out.print("\t" + resultDate.get(i).format(formatter));
                    System.out.printf("%.2f\n", resultCurrencyStat.get(i));
                }
                break;
        }
    }
}
