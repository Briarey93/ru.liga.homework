package ru.liga.predictionService.predictionAlg;

public interface PredictionAlgorithmFactory {

    static PredictionAlgorithm createPredictionAlgorithm_BasedOnAlgorithmType(String algorithmType) {
        if (algorithmType.equalsIgnoreCase("average")) {
            return new PredictionAlgorithmAverage();
        } else if (algorithmType.equalsIgnoreCase("lastYear")) {
            return new PredictionAlgorithmLastYear();
        } else if (algorithmType.equalsIgnoreCase("mystic")) {
            return new PredictionAlgorithmMystic();
        }
        throw new RuntimeException(algorithmType + " is unknown algorithm type.");
    }
}
