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

        currencyTomorrow = this.currencyWeek.get(0);
    }

    /**
     * Предсказываемые даты.
     */
    private void predictDate() {
        LocalDate date = this.date.get(this.date.size() - 1);
        this.date.clear();
        for (int i = 0; i < PredictionAlgorithmAverage.AVERAGE; i++) {
            this.date.add(date.plusDays(i + 1));
        }
    }

    /**
     * Предсказывание курса валют на неделю.
     */
    private void predictCurrencyWeek() {
        for (int i = 0; i < PredictionAlgorithmAverage.AVERAGE; i++) {
            currencyWeek.add(new PredictionAlgorithmAverage().predict(currencyWeek));
            currencyWeek.remove(0);
        }
    }
}
