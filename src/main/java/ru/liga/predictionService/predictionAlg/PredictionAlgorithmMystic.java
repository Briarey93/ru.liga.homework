package ru.liga.predictionService.predictionAlg;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.CurrencyStatistic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * результатом является рандомный курс валюты на эту дату за прошлыу года.
 */
@Slf4j
public class PredictionAlgorithmMystic implements PredictionAlgorithm {

    private final static int SCALE = 2;
    private final static int YEAR_STARTS_FROM = 2005;

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
            LocalDate dateTmp = getRandomDateFromPastYearsOfThisDate(currentCurrencyStatistic, date.plusDays(i));

            log.debug(String.format("Основанием для мистического курса %s, является курс за %s", date.plusDays(i), dateTmp));

            predictionCurrencyStatistic.getCurrencyStatistics()
                    .add(predictNextCurrency(currentCurrencyStatistic, dateTmp));
        }
    }

    /**
     * Получить рандомную дату соответствующего дня за прошлые года.
     */
    private LocalDate getRandomDateFromPastYearsOfThisDate(CurrencyStatistic currentCurrencyStatistic, LocalDate date) {
        List<LocalDate> result = new ArrayList<>();
        for (int i = 1; i <= date.getYear() - YEAR_STARTS_FROM; i++) {
            if (currentCurrencyStatistic.getDates().contains(date.minusYears(i))) {
                result.add(date.minusYears(i));
            }
        }
        return result.get((int) (result.size() * Math.random()));
    }

    private BigDecimal predictNextCurrency(CurrencyStatistic currentCurrencyStatistic, LocalDate date) {
        return currentCurrencyStatistic.getCurrencyStatistics()
                .get(currentCurrencyStatistic.getDates().indexOf(date))
                .setScale(SCALE, RoundingMode.HALF_UP);
    }
}
