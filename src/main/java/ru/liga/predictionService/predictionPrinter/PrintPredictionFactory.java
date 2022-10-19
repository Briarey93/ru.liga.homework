package ru.liga.predictionService.predictionPrinter;

public interface PrintPredictionFactory {

    static PrintPrediction createPrinterPrediction_BasedOnAlgorithmType(String algorithmType) {
        if (algorithmType.equalsIgnoreCase("average")) {
            return new PrintPredictionAverage();
        } else if (algorithmType.equalsIgnoreCase("lastYear")) {
            return new PrintPredictionAverage();
        } else if (algorithmType.equalsIgnoreCase("mystic")) {
            return new PrintPredictionAverage();
        }
        throw new RuntimeException(algorithmType + " is unknown algorithm type.");
    }
}
