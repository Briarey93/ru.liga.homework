package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

/**
 * Интерфейс алгоритма предсказаний курса валют.
 */
public interface PredictionAlgorithm {
    /**
     * Алгоритм предсказания валют.
     */
    void predict(CurrencyStatistic currentCurrencyStatistic,
                 CurrencyStatistic predictionCurrencyStatistic,
                 int lengthPeriod);
}
