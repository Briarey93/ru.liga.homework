package ru.liga;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class PredictionAlgorithmTest {

    public static final int NOT_AVERAGE = 3;

    List<BigDecimal> doubleList = new ArrayList<>();

    @Test
    public void nullPointerPredict() {
        assertNull(PredictionAlgorithm.predictAverage7(null));
        assertNull(PredictionAlgorithm.predictAverage7(doubleList));

        for (int i = 0; i < NOT_AVERAGE; i++) {
            doubleList.add(BigDecimal.valueOf(1));
        }
        assertNull(PredictionAlgorithm.predictAverage7(doubleList));
    }

    @Test
    public void predict() {
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            doubleList.add(BigDecimal.valueOf(1));
        }
        assertEquals(BigDecimal.valueOf(100000, 5), PredictionAlgorithm.predictAverage7(doubleList));

        doubleList.clear();
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            doubleList.add(BigDecimal.valueOf(i));
        }

        assertEquals(BigDecimal.valueOf(300000, 5), PredictionAlgorithm.predictAverage7(doubleList));

        //example
        Assertions.assertThat(1.00000).isEqualTo(1);
    }
}