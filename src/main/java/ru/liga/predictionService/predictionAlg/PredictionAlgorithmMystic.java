package ru.liga.predictionService.predictionAlg;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;
import ru.liga.predictionService.data.RowDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


/**
 * Алгоритм предсказания курса валюты на следующиe lengthPeriod деней,
 * результатом является рандомный курс валюты на эту дату за прошлыу года.
 */
@Slf4j
public class PredictionAlgorithmMystic implements PredictionAlgorithm {

    private final static int YEAR_STARTS_FROM = 2005;

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
        List<RowDto> rowsDto = new ArrayList<>();
        for (int i = 1; i < date.getYear() - YEAR_STARTS_FROM; i++) {
            for (RowDto row : currentCurrencyStatistic.getRowsDto()) {
                if (row.getDate().equals(date.minusYears(i))) {
                    rowsDto.add(row);
                }
            }
        }
        RowDto result;
        if (rowsDto.size() == 0) {
            result = new RowDto();
            result.setCurrency(new BigDecimal(0));
            log.debug(String.format("Для даты %s не существует мистического курса, " +
                    "т.к. нет данных курсов данной даты за прошлые года.", date));
        } else {
            result = rowsDto.get((int) (rowsDto.size() * Math.random()));
            log.debug(String.format("Основанием для мистического курса %s, является курс за %s",
                    date, result.getDate()));
        }


        result.setDate(date);
        return result;
    }
}
