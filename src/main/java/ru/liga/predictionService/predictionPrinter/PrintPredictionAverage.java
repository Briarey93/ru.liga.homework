package ru.liga.predictionService.predictionPrinter;

import ru.liga.predictionService.CurrencyStatistic;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PrintPredictionAverage implements PrintPrediction {

    @Override
    public void print(CurrencyStatistic predictedCurrencyStatistic, String currencyType, int lengthPeriod) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy - ", Locale.ENGLISH);

        switch (lengthPeriod) {
            case (1):
                System.out.print("\"rate " + currencyType + " tomorrow\" ");
                break;

            case (7):
                System.out.println("\"rate " + currencyType + " week\"");
                break;

            case (30):
                System.out.println("\"rate " + currencyType + " month\"");
                break;
        }

        for (int i = 0; i < lengthPeriod; i++) {
            System.out.print("\t" + predictedCurrencyStatistic.getDates().get(i).format(formatter));
            System.out.printf("%.2f\n", predictedCurrencyStatistic.getCurrencyStatistics().get(i));
        }
    }
}
