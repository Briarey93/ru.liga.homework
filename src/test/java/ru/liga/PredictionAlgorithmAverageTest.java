package ru.liga;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PredictionAlgorithmAverageTest {

    public static final int NOT_AVERAGE = 3;

    List<BigDecimal> doubleList = new ArrayList<>();
//    PredictionAlgorithmAverage predictionAlgorithmAverage = new PredictionAlgorithmAverage(new CurrencyStatistic(),new CurrencyStatistic(),0);

//    @Test(expected = IllegalArgumentException.class)
//    public void nullPointerPredict() {
//        predictionAlgorithmAverage.predict();
//        assertNull(predictionAlgorithmAverage.predict());
//        assertNull(predictionAlgorithmAverage.predict());
//
//        for (int i = 0; i < NOT_AVERAGE; i++) {
//            doubleList.add(BigDecimal.valueOf(1));
//        }
//        assertNull(predictionAlgorithmAverage.predict(doubleList));
//    }

//    @Test
//    public void predict() {
//        for (int i = 0; i < 7; i++) {
//            doubleList.add(BigDecimal.valueOf(1));
//        }
//        assertEquals(BigDecimal.valueOf(100000, 5), predictionAlgorithmAverage.predict(doubleList));
//
//        doubleList.clear();
//        for (int i = 0; i < 7; i++) {
//            doubleList.add(BigDecimal.valueOf(i));
//        }
//        assertEquals(BigDecimal.valueOf(300000, 5), predictionAlgorithmAverage.predict(doubleList));
//
//        //example
//        Assertions.assertThat(1.00000).isEqualTo(1);
//    }
}