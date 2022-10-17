package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующиe LENGTH_PERIOD деней,
 * основа(AVERAGE) - предидущие 7 курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverage implements PredictionAlgorithm {

    private final static int AVERAGE = 7;
    private final static int SCALE = 2;
    private static final String CAN_T_CALCULATE_PREDICTION_COURSE_LIST_TOO_SMALL = "Can't calculate prediction. Course List too small.";


    /**
     * Алгоритм предсказания валют.
     */
    @Override
    public void predict(final CurrencyStatistic currentCurrencyStatistic,
                        final CurrencyStatistic predictionCurrencyStatistic,
                        final int lengthPeriod) {
        List<BigDecimal> courseList;
        try {
            courseList = new ArrayList<>(currentCurrencyStatistic.getCurrencyStatistics().subList(0, AVERAGE));
        } catch (Exception e) {
            throw new RuntimeException(CAN_T_CALCULATE_PREDICTION_COURSE_LIST_TOO_SMALL);
        }

        predictionCurrencyStatistic.getDates().add(0, currentCurrencyStatistic.getDates().get(0));
        for (int i = 0; i < lengthPeriod; i++) {
            predictionCurrencyStatistic.getDates().add(0, predictionCurrencyStatistic.getDates().get(0).plusDays(1));
            courseList.add(0, predictNextCurrency(courseList.subList(0, AVERAGE)));
        }

        predictionCurrencyStatistic.getCurrencyStatistics().addAll(courseList.subList(0, lengthPeriod));

        Collections.reverse(predictionCurrencyStatistic.getDates());
        Collections.reverse(predictionCurrencyStatistic.getCurrencyStatistics());
        predictionCurrencyStatistic.getDates().remove(0);
    }

    private BigDecimal predictNextCurrency(List<BigDecimal> courseList) {
        return courseList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(AVERAGE), SCALE, RoundingMode.HALF_UP);
    }
}
