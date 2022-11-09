package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.data.CurrencyStatistic;
import ru.liga.predictionService.data.RowDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        List<RowDto> rowsDto;
        try {
            rowsDto = new ArrayList<>(currentCurrencyStatistic.getRowsDto().subList(0, AVERAGE));
        } catch (Exception e) {
            throw new RuntimeException("Недостаточно данных для алгорифмического рассчёта предсказаний");
        }

        for (int i = 0; i < lengthPeriod; i++) {
            rowsDto.add(0, predictNextDto(rowsDto.subList(0, AVERAGE)));
        }

        predictionCurrencyStatistic.addRowAll(rowsDto.subList(0, lengthPeriod));
        Collections.reverse(predictionCurrencyStatistic.getRowsDto());
    }

    private RowDto predictNextDto(final List<RowDto> sublistDto) {
        RowDto result = new RowDto();
        BigDecimal currencyResult = new BigDecimal(0);
        result.setDate(sublistDto.get(0).getDate().plusDays(1));
        for (int i = 0; i < AVERAGE; i++) {
            currencyResult = currencyResult.add(sublistDto.get(i).getCurrency());
        }
        result.setCurrency(currencyResult.divide(BigDecimal.valueOf(AVERAGE), SCALE, RoundingMode.HALF_UP));
        return result;
    }
}
