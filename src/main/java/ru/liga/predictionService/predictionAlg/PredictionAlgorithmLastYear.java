package ru.liga.predictionService.predictionAlg;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;
import ru.liga.predictionService.data.RowDto;

import java.time.LocalDate;

/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * результатом является курс валюты за прошлый год.
 * Если в прошлом году за эту дату не было курса, то берётся предидущий курс.
 */
@Slf4j
public class PredictionAlgorithmLastYear implements PredictionAlgorithm {

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
        LocalDate date = currentCurrencyStatistic.getRowsDto().get(0).getDate().plusDays(1);

        for (int i = 0; i < lengthPeriod; i++) {
            predictionCurrencyStatistic.addRow(predictNextDto(currentCurrencyStatistic, date.plusDays(i)));
        }
    }

    private RowDto predictNextDto(final CurrencyStatistic currentCurrencyStatistic, final LocalDate date) {
        RowDto result = new RowDto();
        result.setDate(date);
        for (RowDto row : currentCurrencyStatistic.getRowsDto()) {
            if (row.getDate().compareTo(date.minusYears(1)) <= 0) {
                result.setCurrency(row.getCurrency());

                log.debug(String.format("Основанием для прошлогоднего курса %s, является курс за %s",
                        date, row.getDate()));

                return result;
            }
        }
        return null;
    }
}
