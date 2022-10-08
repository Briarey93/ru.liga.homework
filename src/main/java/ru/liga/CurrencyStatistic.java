package ru.liga;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс агрегирующий статистику валюты на ближайший день/неделю.
 */
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
     * Получить курс на завтра.
     *
     * @return - курс.
     */
    public BigDecimal getCurrencyTomorrow() {
        return currencyTomorrow;
    }

    /**
     * Получить курс на неделю.
     *
     * @return - недельный курс.
     */
    public List<BigDecimal> getCurrencyWeek() {
        return currencyWeek;
    }

    /**
     * Получить даты грядущей недели.
     *
     * @return - даты.
     */
    public List<LocalDate> getDate() {
        return date;
    }

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
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            this.date.add(date.plusDays(i + 1));
        }
    }

    /**
     * Предсказывание курса валют на неделю.
     */
    private void predictCurrencyWeek() {
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            currencyWeek.add(PredictionAlgorithm.predictAverage7(currencyWeek));
            currencyWeek.remove(0);
        }
    }
}
