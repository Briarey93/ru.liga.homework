package ru.liga.currencyService;

import lombok.Getter;
import ru.liga.predictionService.PredictionAlgorithmAverage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс агрегирующий статистику валюты на ближайший день/неделю.
 */
@Getter
public class CurrencyStatistic {

    /**
     * Предположительный курс на завтра.
     */
    private BigDecimal currencyTomorrow;

    /**
     * Предположительный курс на неделю.
     */
    private List<BigDecimal> currencyWeek = new ArrayList<>();

    /**
     * Data.
     */
    private List<LocalDate> date = new ArrayList<>();

    /**
     * Рассчёт предсказания курсов.
     */
    public void predict() {
        predictDate();
        predictCurrencyWeek();

        currencyTomorrow = currencyWeek.get(PredictionAlgorithmAverage.AVERAGE - 1);
    }

    /**
     * Предсказываемые даты.
     */
    private void predictDate() {
        for (int i = 0; i < PredictionAlgorithmAverage.AVERAGE; i++) {
            date.add(0, date.get(0).plusDays(1));
        }
    }

    /**
     * Предсказывание курса валют на неделю.
     */
    private void predictCurrencyWeek() {
        for (int i = 0; i < PredictionAlgorithmAverage.AVERAGE; i++) {
            currencyWeek.add(0, new PredictionAlgorithmAverage().predict(currencyWeek.subList(0,PredictionAlgorithmAverage.AVERAGE)));
        }
    }
}
