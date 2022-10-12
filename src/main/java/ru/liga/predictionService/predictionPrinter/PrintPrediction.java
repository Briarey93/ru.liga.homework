package ru.liga.predictionService.predictionPrinter;

import ru.liga.predictionService.CurrencyStatistic;

public interface PrintPrediction {
    void printPrediction(CurrencyStatistic predictedCurrencyStatistic, String currencyType, int lengthPeriod);
}
