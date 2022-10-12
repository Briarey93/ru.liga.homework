package ru.liga.predictionService.predictionAlg;

import ru.liga.predictionService.predictionPrinter.PrintPrediction;

public interface PredictionAlgorithmFactory {
    PredictionAlgorithm createPredictionAlgorithm();
    PrintPrediction createPrintPrediction();
}
