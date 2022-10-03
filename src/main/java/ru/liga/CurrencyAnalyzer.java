package ru.liga;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CurrencyAnalyzer {
    /**
     * Статистика валюты.
     */
    final private CurrencyStatistic currencyStatistic = new CurrencyStatistic();

    /**
     * Анализатор строки формата "nominal;data;curs"
     * запись результата в статистику.
     *
     * @param line - строка.
     */
    public void analyze(final String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy", Locale.ENGLISH);
        StringBuilder tmp = new StringBuilder();
        char current;
        int i;
        int nominal;


        for (i = 0; i < line.length(); i++) {
            current = line.charAt(i);
            if (current == ';') {
                break;
            }
            tmp.append(current);
        }

        nominal = Integer.parseInt(tmp.toString());
        tmp = new StringBuilder();
        i++;

        for (; i < line.length(); i++) {
            current = line.charAt(i);
            if (current == ';') {
                break;
            }
            tmp.append(current);
        }

        currencyStatistic.getDate().add(LocalDate.parse(tmp.toString(), formatter));
        tmp = new StringBuilder();
        i++;

        for (; i < line.length(); i++) {
            current = line.charAt(i);
            if (current == ';') {
                break;
            }
            if (current == ',') {
                current = '.';
            }
            tmp.append(current);
        }

        currencyStatistic.getCurrencyWeek().add(Double.parseDouble(tmp.toString()) / nominal);
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
