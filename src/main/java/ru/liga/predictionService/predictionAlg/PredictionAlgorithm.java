package ru.liga.predictionService.predictionAlg;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс алгоритма предсказаний курса валют.
 */
public interface PredictionAlgorithm {

    /**
     * Метод предсказания.
     *
     * @param courseList - входные параметры для предсказания.
     * @return - валютный курс.
     */
    BigDecimal predict(final List<BigDecimal> courseList);
}
