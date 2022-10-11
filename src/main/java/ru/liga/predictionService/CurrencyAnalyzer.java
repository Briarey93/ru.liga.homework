package ru.liga.predictionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrencyAnalyzer {
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy", Locale.ENGLISH);
        String[] num = line.split(";");

        currencyStatistic.getDate().add(LocalDate.parse(num[1], formatter));

        num[2] = num[2].replaceFirst(",", ".");

        currencyStatistic.getCurrencyWeek()
                .add(new BigDecimal(num[2]).divide(BigDecimal.valueOf(Integer.parseInt(num[0])),5, RoundingMode.HALF_UP));

    }

    /**
     * Получить статистику.
     *
     * @return - статистика.
     */
    public CurrencyStatistic getCurrencyStatistic() {
        return currencyStatistic;
    }
}
