package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующиe LENGTH_PERIOD деней,
 * основа(AVERAGE) - предидущие 7 курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverage implements PredictionAlgorithm {

    private final static int AVERAGE = 7;
    private final int LENGTH_PERIOD;

    private final CurrencyStatistic currentCurrencyStatistic;
    private final CurrencyStatistic predictionCurrencyStatistic;

    public PredictionAlgorithmAverage(CurrencyStatistic currentCurrencyStatistic,
                                      CurrencyStatistic predictionCurrencyStatistic,
                                      int lengthPeriod) {
        LENGTH_PERIOD = lengthPeriod;
        this.currentCurrencyStatistic = currentCurrencyStatistic;
        this.predictionCurrencyStatistic = predictionCurrencyStatistic;
    }

    /**
     * Алгоритм предсказания валют.
     */
    @Override
    public void predict() {
        List<BigDecimal> courseList = currentCurrencyStatistic.getCurrencyStatistics().subList(0, AVERAGE);
        if (courseList.isEmpty()) {
            System.out.println("Course List is null.");
            return;
        }
        if (courseList.size() < AVERAGE) {
            System.out.println("Can't calculate prediction. Course List too small.");
            return;
        }

        predictionCurrencyStatistic.getDates().add(0, currentCurrencyStatistic.getDates().get(0));
        for (int i = 0; i < LENGTH_PERIOD; i++) {
            predictionCurrencyStatistic.getDates().add(0, predictionCurrencyStatistic.getDates().get(0).plusDays(1));
            courseList.add(0, predictNextCurrency(courseList.subList(0, AVERAGE)));
        }

        predictionCurrencyStatistic.getCurrencyStatistics().addAll(courseList.subList(0, LENGTH_PERIOD));
    }

    private BigDecimal predictNextCurrency(List<BigDecimal> courseList) {
        return courseList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(AVERAGE), 5, RoundingMode.HALF_UP);
    }
}
