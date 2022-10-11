package ru.liga;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmAverage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PredictionAlgorithmAverageTest {

    public static final int NOT_AVERAGE = 3;

//    List<BigDecimal> doubleList = new ArrayList<>();
//    PredictionAlgorithmAverage predictionAlgorithmAverage = new PredictionAlgorithmAverage();
//
//    @Test
//    public void nullPointerPredict() {
//        assertNull(predictionAlgorithmAverage.predict(null));
//        assertNull(predictionAlgorithmAverage.predict(doubleList));
//
//        for (int i = 0; i < NOT_AVERAGE; i++) {
//            doubleList.add(BigDecimal.valueOf(1));
//        }
//        assertNull(predictionAlgorithmAverage.predict(doubleList));
//    }
//
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