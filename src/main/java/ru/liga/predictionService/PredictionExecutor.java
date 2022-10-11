package ru.liga.predictionService;

import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmAverage;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class PredictionExecutor {
    private int lengthPeriod;
    private SourceReader sourceReader;

    private CurrencyStatistic currentCurrencyStatistic;
    private CurrencyStatistic predictionCurrencyStatistic;


    private PredictionAlgorithm predictionAlgorithm;
    private PrintPrediction printPrediction;


    public PredictionExecutor(final String source, final int lengthPeriod) {
        currentCurrencyStatistic = new CurrencyStatistic();
        predictionCurrencyStatistic = new CurrencyStatistic();

        this.lengthPeriod = lengthPeriod;
        sourceReader = new SourceReader(currentCurrencyStatistic);
        sourceReader.setup(source);

        setPredictionAlgorithmAndPrinterBasedOnLengthPeriod();
    }

    /**
     * Метод логики и последовательности действий приложения.
     */
    public void executeApplication() {
        try {
            sourceReader.readSource();
        } catch (Exception e) {
            System.out.println("Ошибка чтения файла.");
            return;
        }

        predictionAlgorithm.predict();
        // TODO: выводит "rate USD week" на все валюты __USD__
        // TODO: реализовать печать результатов через сервис печати.
        // TODO: найте все магические числа/строки и вынести в константы.
        // TODO: добавить везде где можно/нужно джава доки.
        printResult();
    }

    private void setPredictionAlgorithmAndPrinterBasedOnLengthPeriod() {
        switch (lengthPeriod) {
            case (1):
                predictionAlgorithm = new PredictionAlgorithmAverage(currentCurrencyStatistic, predictionCurrencyStatistic, 1);
                break;
            case (2):
                predictionAlgorithm = new PredictionAlgorithmAverage(currentCurrencyStatistic, predictionCurrencyStatistic, 7);
                break;
            case (3):
                predictionAlgorithm = new PredictionAlgorithmAverage(currentCurrencyStatistic, predictionCurrencyStatistic, 30);
                break;
        }
    }

    private void printResult() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy - ", Locale.ENGLISH);
        List<LocalDate> resultDate;
        List<BigDecimal> resultCurrencyStat;

        switch (lengthPeriod) {

            case (1):
                System.out.print("\"rate TRY tomorrow\" ");
                System.out.print(predictionCurrencyStatistic.getDates().get(0).format(formatter));
                System.out.printf("%.2f\n", predictionCurrencyStatistic.getCurrencyStatistics().get(0));
                break;
            case (2):
                resultDate = predictionCurrencyStatistic.getDates();
                resultCurrencyStat = predictionCurrencyStatistic.getCurrencyStatistics();

                System.out.println("\"rate USD week\"");
                for (int i = 6; i >= 0; i--) {
                    System.out.print("\t" + resultDate.get(i).format(formatter));
                    System.out.printf("%.2f\n", resultCurrencyStat.get(i));
                }
                break;
            case (3):
                resultDate = predictionCurrencyStatistic.getDates();
                resultCurrencyStat = predictionCurrencyStatistic.getCurrencyStatistics();

                System.out.println("\"rate USD month\"");
                for (int i = 29; i >= 0; i--) {
                    System.out.print("\t" + resultDate.get(i).format(formatter));
                    System.out.printf("%.2f\n", resultCurrencyStat.get(i));
                }
                break;
        }
    }
}
