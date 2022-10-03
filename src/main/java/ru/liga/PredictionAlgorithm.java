package ru.liga;

import java.util.List;

public class PredictionAlgorithm {

    public static final int AVERAGE = 7;

    public static double predictAverage7(final List<Double> courseList) {
        if (courseList == null) {
            System.out.println("Course List is null.");
            return -1;
        }

        if (courseList.size() < AVERAGE) {
            System.out.println("Can't calculate prediction. Course List too small.");
            return -1;
        }

        double result = 0;
        for (int i = 0; i < AVERAGE; i++) {
            result += courseList.get(i);
        }
        return result / AVERAGE;
    }
}
