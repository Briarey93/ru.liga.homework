package ru.liga.predictionService.predictionPrinter;

import ru.liga.predictionService.data.CurrencyStatistic;

public interface PrintPrediction {
    void print(final CurrencyStatistic predictedCurrencyStatistic,
               final String currencyType,
               final int lengthPeriod);

    static PrintPrediction getPrintPrediction(String algorithmType) {
        if (algorithmType.equalsIgnoreCase("average")) {
            return new PrintPredictionAverage();
        } else if (algorithmType.equalsIgnoreCase("lastYear")) {
            return new PrintPredictionAverage();
        } else if (algorithmType.equalsIgnoreCase("mystic")) {
            return new PrintPredictionAverage();
        }
        throw new RuntimeException(algorithmType + " is unknown algorithm type.");
    }
}
