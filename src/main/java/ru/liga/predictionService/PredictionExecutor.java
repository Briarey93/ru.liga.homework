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

        // TODO: отдаю в предикшн лист, все манипуляции провожу в нём и сохраняю данные там же. вывод реализую из данных предикшена.
        currentCurrencyStatistic.predict(predictionAlgorithm);
        // TODO: реализовать печать результатов через сервис печати.
        printResult();
    }

    private void setPredictionAlgorithmAndPrinterBasedOnLengthPeriod() {
        switch (lengthPeriod){
            case(1):
                predictionAlgorithm = new PredictionAlgorithmAverage();
                break;
            case (2):
                predictionAlgorithm = new PredictionAlgorithmAverage();
                break;
        }
    }

    private void printResult() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy - ", Locale.ENGLISH);

        switch (lengthPeriod) {
            case (1):
                System.out.print("\"rate TRY tomorrow\" ");
                System.out.print(currentCurrencyStatistic.getDate().get(PredictionAlgorithmAverage.AVERAGE - 1).format(formatter));
                System.out.printf("%.2f\n", currentCurrencyStatistic.getCurrencyTomorrow());
                break;
            case (2):
                List<LocalDate> resultDate = currentCurrencyStatistic.getDate();
                List<BigDecimal> resultCurrencyStat = currentCurrencyStatistic.getCurrencyWeek();

                System.out.println("\"rate USD week\"");
                for (int i = PredictionAlgorithmAverage.AVERAGE - 1; i >= 0; i--) {
                    System.out.print("\t" + resultDate.get(i).format(formatter));
                    System.out.printf("%.2f\n", resultCurrencyStat.get(i));
                }
                break;
        }
    }
}
