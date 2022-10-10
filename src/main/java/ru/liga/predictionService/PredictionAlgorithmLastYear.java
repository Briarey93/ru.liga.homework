package ru.liga.predictionService;

import java.math.BigDecimal;
import java.util.List;

public class PredictionAlgorithmLastYear implements PredictionAlgorithm{
    /**
     * Метод предсказания.
     *
     * @param courseList - входные параметры для предсказания.
     * @return - валютный курс.
     */
    @Override
    public BigDecimal predict(List<BigDecimal> courseList) {
        return null;
    }
}
