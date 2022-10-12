package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующиe LENGTH_PERIOD деней,
 * основа(AVERAGE) - предидущие 7 курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverage implements PredictionAlgorithm {

    private final static int AVERAGE = 7;
    private final static int SCALE = 5;
    private static final String CAN_T_CALCULATE_PREDICTION_COURSE_LIST_TOO_SMALL = "Can't calculate prediction. Course List too small.";
    private static final String COURSE_LIST_IS_NULL = "Course List is null.";

    private int LENGTH_PERIOD;

    private CurrencyStatistic currentCurrencyStatistic;
    private CurrencyStatistic predictionCurrencyStatistic;

    /**
     * Алгоритм предсказания валют.
     */
    @Override
    public void predict(CurrencyStatistic currentCurrencyStatistic,
                        CurrencyStatistic predictionCurrencyStatistic,
                        int lengthPeriod) {
        LENGTH_PERIOD = lengthPeriod;
        this.currentCurrencyStatistic = currentCurrencyStatistic;
        this.predictionCurrencyStatistic = predictionCurrencyStatistic;
        List<BigDecimal> courseList = currentCurrencyStatistic.getCurrencyStatistics().subList(0, AVERAGE);
        if (courseList.isEmpty()) {
            System.out.println(COURSE_LIST_IS_NULL);
            return;
        }
        if (courseList.size() < AVERAGE) {
            System.out.println(CAN_T_CALCULATE_PREDICTION_COURSE_LIST_TOO_SMALL);
            return;
        }

        predictionCurrencyStatistic.getDates().add(0, currentCurrencyStatistic.getDates().get(0));
        for (int i = 0; i < LENGTH_PERIOD; i++) {
            predictionCurrencyStatistic.getDates().add(0, predictionCurrencyStatistic.getDates().get(0).plusDays(1));
            courseList.add(0, predictNextCurrency(courseList.subList(0, AVERAGE)));
        }

        predictionCurrencyStatistic.getCurrencyStatistics().addAll(courseList.subList(0, LENGTH_PERIOD));

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
