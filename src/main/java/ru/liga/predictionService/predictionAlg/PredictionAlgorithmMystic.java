package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;


/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * результатом является рандомный курс валюты на эту дату за прошлыу года.
 */
public class PredictionAlgorithmMystic implements PredictionAlgorithm {

    /**
     * Алгоритм предсказания валют.
     *
     * @param currentCurrencyStatistic    - текущие курсы валют.
     * @param predictionCurrencyStatistic - предсказываемые курсы валют.
     * @param lengthPeriod                - период предсказания.
     */
    @Override
    public void predict(CurrencyStatistic currentCurrencyStatistic,
                        CurrencyStatistic predictionCurrencyStatistic,
                        int lengthPeriod) {

    }
}
