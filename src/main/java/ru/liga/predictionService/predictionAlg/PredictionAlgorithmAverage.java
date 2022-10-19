package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * основа(AVERAGE) - предидущие 7 курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverage implements PredictionAlgorithm {

    private final static int AVERAGE = 7;
    private final static int SCALE = 2;


    /**
     * Алгоритм предсказания валют.
     *
     * @param currentCurrencyStatistic    - текущие курсы валют.
     * @param predictionCurrencyStatistic - предсказываемые курсы валют.
     * @param lengthPeriod                - период предсказания.
     */
    @Override
    public void predict(final CurrencyStatistic currentCurrencyStatistic,
                        final CurrencyStatistic predictionCurrencyStatistic,
                        final int lengthPeriod) {
        List<BigDecimal> courseList;
        try {
            courseList = new ArrayList<>(currentCurrencyStatistic.getCurrencyStatistics().subList(0, AVERAGE));
        } catch (Exception e) {
            throw new RuntimeException("Недостаточно данных для алгорифмического рассчёта предсказаний");
        }

        LocalDate date = currentCurrencyStatistic.getDates().get(0).plusDays(1);

        for (int i = 0; i < lengthPeriod; i++) {
            predictionCurrencyStatistic.getDates().add(date.plusDays(i));
            courseList.add(0, predictNextCurrency(courseList.subList(0, AVERAGE)));
        }

        predictionCurrencyStatistic.getCurrencyStatistics().addAll(courseList.subList(0, lengthPeriod));
        Collections.reverse(predictionCurrencyStatistic.getCurrencyStatistics());
    }

    private BigDecimal predictNextCurrency(List<BigDecimal> courseList) {
        return courseList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(AVERAGE), SCALE, RoundingMode.HALF_UP);
    }
}
