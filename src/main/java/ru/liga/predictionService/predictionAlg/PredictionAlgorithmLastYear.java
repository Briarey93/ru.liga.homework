package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * результатом является курс валюты за прошлый год.
 * Если в прошлом году за эту дату не было курса, то берётся предидущий курс.
 */
public class PredictionAlgorithmLastYear implements PredictionAlgorithm {

    private final static int SCALE = 2;

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

        LocalDate date = currentCurrencyStatistic.getDates().get(0).plusDays(1);

        for (int i = 0; i < lengthPeriod; i++) {
            predictionCurrencyStatistic.getDates().add(date.plusDays(i));
            predictionCurrencyStatistic.getCurrencyStatistics()
                    .add(predictNextCurrency(currentCurrencyStatistic, date.plusDays(i).minusYears(1)));
        }
    }

    private BigDecimal predictNextCurrency(CurrencyStatistic currentCurrencyStatistic, LocalDate date) {
        if (currentCurrencyStatistic.getDates().contains(date)) {
            return currentCurrencyStatistic.getCurrencyStatistics()
                    .get(currentCurrencyStatistic.getDates().indexOf(date))
                    .divide(BigDecimal.valueOf(1), SCALE, RoundingMode.HALF_UP);
        }
        return predictNextCurrency(currentCurrencyStatistic, date.minusDays(1));
    }
}
