package ru.liga.predictionService;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс агрегирующий статистику валюты.
 */
@Getter
public class CurrencyStatistic {

    /**
     * Курсы валют.
     */
    private List<BigDecimal> currencyStatistics = new ArrayList<>();

    /**
     * Data.
     */
    private List<LocalDate> dates = new ArrayList<>();

}
