package ru.liga.predictionService;

import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmAverage;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PredictionExecutor {
    private final int LENGTH_PERIOD;
    private final String CURRENCY_TYPE;
    private SourceReader sourceReader;

    private CurrencyStatistic currentCurrencyStatistic;
    private CurrencyStatistic predictionCurrencyStatistic;


    private PredictionAlgorithm predictionAlgorithm;
    private PrintPrediction printPrediction;


    public PredictionExecutor(final String source, final int lengthPeriod, final String currencyType) {
        CURRENCY_TYPE = currencyType;
        this.LENGTH_PERIOD = lengthPeriod;

        currentCurrencyStatistic = new CurrencyStatistic();
        predictionCurrencyStatistic = new CurrencyStatistic();

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
        // TODO: реализовать печать результатов через class печати.
        // TODO: найте все магические числа/строки и вынести в константы.
        // TODO: добавить везде где можно/нужно джава доки.
        printResult();
    }

    private void setPredictionAlgorithmAndPrinterBasedOnLengthPeriod() {
        predictionAlgorithm = new PredictionAlgorithmAverage(currentCurrencyStatistic, predictionCurrencyStatistic, LENGTH_PERIOD);
    }

    private void printResult() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy - ", Locale.ENGLISH);

        switch (LENGTH_PERIOD) {
            case (1):
                System.out.print("\"rate " + CURRENCY_TYPE + " tomorrow\" ");
                break;

            case (7):
                System.out.println("\"rate " + CURRENCY_TYPE + " week\"");
                break;

            case (30):
                System.out.println("\"rate " + CURRENCY_TYPE + " month\"");
                break;
        }


        for (int i = 0; i < LENGTH_PERIOD; i++) {
            System.out.print("\t" + predictionCurrencyStatistic.getDates().get(i).format(formatter));
            System.out.printf("%.2f\n", predictionCurrencyStatistic.getCurrencyStatistics().get(i));
        }
    }
}
