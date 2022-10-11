package ru.liga.predictionService.predictionAlg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Алгоритм предсказания курса валюты на следующий день,
 * основа - предидущие AVERAGE курсов валют.
 * результатом является СреднееАрифметическое от основы.
 */
public class PredictionAlgorithmAverage implements PredictionAlgorithm {

    public static final int AVERAGE = 7;

    /**
     * Метод предсказания курса валют на неделю вперед,
     * основываясь на AVERAGE курсов до.
     *
     * @param courseList - статистика курсов до дня Х.
     * @return - список курсов валют на неделю вперед.
     */
    public BigDecimal predict(final List<BigDecimal> courseList) {
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
