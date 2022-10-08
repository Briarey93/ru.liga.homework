package ru.liga;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class PredictionAlgorithm {

    public static final int AVERAGE = 7;
// TODO создать интерфейс и сделать реализацию отдельно на 1 и 7 дней. + новые алгоритмы предсказаний.
// TODO забрать функцию предикт из CurrencyStatistic

    /**
     * Метод предсказания курса валют на неделю вперед,
     * основываясь на AVERAGE курсов до.
     *
     * @param courseList - статистика курсов до дня Х.
     * @return - список курсов валют на неделю вперед.
     */
    public static BigDecimal predictAverage7(final List<BigDecimal> courseList) {
        if (courseList == null) {
            System.out.println("Course List is null.");
            return null;
        }

        if (courseList.size() < AVERAGE) {
            System.out.println("Can't calculate prediction. Course List too small.");
            return null;
        }

        return courseList.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(AVERAGE), 5, RoundingMode.HALF_UP);
    }
}
