package ru.liga.predictionService.predictionAlg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующий день,
 * основа - предидущие 7 курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverageOne implements PredictionAlgorithm {

    public static final int AVERAGE = 7;

    /**
     * Метод предсказания.
     *
     * @param courseList - входные параметры для предсказания.
     * @return - валютный курс.
     */
    @Override
    public BigDecimal predict(List<BigDecimal> courseList) {
        if (courseList == null) {
            System.out.println("Course List is null.");
            return null;
        }

        if (courseList.size() < AVERAGE) {
            System.out.println("Can't calculate prediction. Course List too small.");
            return null;
        }

        return courseList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(AVERAGE), 5, RoundingMode.HALF_UP);
    }
}
