package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

/**
 * Интерфейс алгоритма предсказаний курса валют.
 */
public interface PredictionAlgorithm {
    /**
     * Алгоритм предсказания валют.
     *
     * @param currentCurrencyStatistic    - текущие курсы валют.
     * @param predictionCurrencyStatistic - предсказываемые курсы валют.
     * @param lengthPeriod                - период предсказания.
     */
    void predict(CurrencyStatistic currentCurrencyStatistic,
                 CurrencyStatistic predictionCurrencyStatistic,
                 int lengthPeriod);

    static PredictionAlgorithm getPredictionAlgorithm(String algorithmType) {
        if (algorithmType.equalsIgnoreCase("average")) {
            return new PredictionAlgorithmAverage();
        } else if (algorithmType.equalsIgnoreCase("lastYear")) {
            return new PredictionAlgorithmLastYear();
        } else if (algorithmType.equalsIgnoreCase("mystic")) {
            return new PredictionAlgorithmMystic();
        }
        throw new RuntimeException(algorithmType + " is unknown algorithm type.");
    }
}
