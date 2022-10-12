package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.predictionPrinter.PrintPrediction;
import ru.liga.predictionService.predictionPrinter.PrintPredictionAverage;

public class PredictionAlgorithmFactoryAverage implements PredictionAlgorithmFactory {
    @Override
    public PredictionAlgorithm createPredictionAlgorithm() {
        return new PredictionAlgorithmAverage();
    }

    @Override
    public PrintPrediction createPrintPrediction() {
        return new PrintPredictionAverage();
    }
}
