package ru.liga;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PredictionAlgorithmTest {

    public static final int NOT_AVERAGE = 3;

    List<Double> doubleList = new ArrayList<>();

    @Test
    public void nullPointerPredict() {
        assertEquals(-1, PredictionAlgorithm.predictAverage7(null), 0.0001);
        assertEquals(-1, PredictionAlgorithm.predictAverage7(doubleList), 0.0001);

        for (int i = 0; i < NOT_AVERAGE; i++) {
            doubleList.add(1.0);
        }
        assertEquals(-1, PredictionAlgorithm.predictAverage7(doubleList), 0.0001);
    }

    @Test
    public void predict() {
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            doubleList.add(1.0);
        }
        assertEquals(1, PredictionAlgorithm.predictAverage7(doubleList), 0.0001);

        doubleList.clear();
        for (int i = 0; i < PredictionAlgorithm.AVERAGE; i++) {
            doubleList.add((double) i);
        }
        assertEquals(3, PredictionAlgorithm.predictAverage7(doubleList), 0.0001);
    }
}