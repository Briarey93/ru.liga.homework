package ru.liga.predictionService.predictionAlg;

public class PredictionAlgorithmFactoryInitializer {
    public static PredictionAlgorithmFactory createPredictionAlgorithm_AndPrinterPrediction_BasedOnAlgorithmType(String algorithmType) {
        if (algorithmType.equalsIgnoreCase("average")) {
            return new PredictionAlgorithmFactoryAverage();
        }
        throw new RuntimeException(algorithmType + " is unknown algorithm type.");
    }
}
