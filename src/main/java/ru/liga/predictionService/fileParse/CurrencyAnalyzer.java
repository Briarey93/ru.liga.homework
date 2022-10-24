package ru.liga.predictionService.fileParse;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;
import ru.liga.predictionService.data.RowDto;

@Slf4j
public class CurrencyAnalyzer {

    private static final int POSITION_FOR_NOMINAL = 0;
    private static final int POSITION_FOR_DATE = 1;
    private static final int POSITION_FOR_CURRENCY = 2;


    /**
     * Статистика валюты.
     */
    final private CurrencyStatistic currencyStatistic;

    public CurrencyAnalyzer(CurrencyStatistic currencyStatistic) {
        this.currencyStatistic = currencyStatistic;
    }

    /**
     * Анализатор строки формата "nominal;data;curs"
     * запись результата в статистику.
     *
     * @param line - строка.
     */
    public void analyze(final String line) {
        String[] num = line.split(";");

        try {
            RowDto rowDto = new RowDto();
            rowDto.setCurrency(num[POSITION_FOR_CURRENCY], num[POSITION_FOR_NOMINAL]);
            rowDto.setDate(num[POSITION_FOR_DATE]);
            currencyStatistic.addRow(rowDto);
        } catch (Exception e) {
            log.debug(String.format("Строка \"%s\" пропущена", line));
        }
    }
}
