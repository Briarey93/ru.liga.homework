package ru.liga.predictionService;

import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmFactory;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmFactoryAverage;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PredictionExecutor {
    private SourceReader sourceReader;
    private final String CURRENCY_TYPE;
    private final String ALGORITHM_TYPE;
    private final int LENGTH_PERIOD;

    private CurrencyStatistic currentCurrencyStatistic;
    private CurrencyStatistic predictionCurrencyStatistic;

    private PredictionAlgorithm predictionAlgorithm;
    private PrintPrediction printPrediction;


    public PredictionExecutor(final String source,
                              final String currencyType,
                              final String algorithmType,
                              final int lengthPeriod) {
        CURRENCY_TYPE = currencyType;
        ALGORITHM_TYPE = algorithmType;
        LENGTH_PERIOD = lengthPeriod;

        currentCurrencyStatistic = new CurrencyStatistic();
        predictionCurrencyStatistic = new CurrencyStatistic();

        sourceReader = new SourceReader(currentCurrencyStatistic);
        sourceReader.setup(source);

        PredictionAlgorithmFactory predictionAlgorithmFactory =
                createPredictionAlgorithm_AndPrinterPrediction_BasedOnAlgorithmType();
        predictionAlgorithm = predictionAlgorithmFactory.createPredictionAlgorithm();
        printPrediction = predictionAlgorithmFactory.createPrintPrediction();
    }

    private PredictionAlgorithmFactory createPredictionAlgorithm_AndPrinterPrediction_BasedOnAlgorithmType() {
        if (ALGORITHM_TYPE.equalsIgnoreCase("average"))
            return new PredictionAlgorithmFactoryAverage();
        throw new RuntimeException(ALGORITHM_TYPE + " is unknown algorithm type.");
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

        if (currentCurrencyStatistic.getCurrencyStatistics().isEmpty()) {
            return;
        }
        predictionAlgorithm.predict(currentCurrencyStatistic, predictionCurrencyStatistic, LENGTH_PERIOD);
        printResult();
        printPrediction.printPrediction(predictionCurrencyStatistic, CURRENCY_TYPE, LENGTH_PERIOD);
        // TODO: реализовать печать результатов через class печати.
        // TODO: добавить везде где можно/нужно джава доки.
        // TODO: весь вывод должен быть на одном языке во всех файлах.
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
