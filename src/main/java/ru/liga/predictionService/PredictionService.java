package ru.liga.predictionService;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmFactory;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;
import ru.liga.predictionService.predictionPrinter.PrintPredictionFactory;

@Slf4j
public class PredictionService {

    private final String SOURCE;
    private final String CURRENCY_TYPE;
    private final String ALGORITHM_TYPE;
    private final int LENGTH_PERIOD;

    private SourceReader sourceReader;

    private CurrencyStatistic currentCurrencyStatistic;
    private CurrencyStatistic predictionCurrencyStatistic;

    private PredictionAlgorithm predictionAlgorithm;
    private PrintPrediction printPrediction;


    public PredictionService(final String source,
                             final String currencyType,
                             final String algorithmType,
                             final int lengthPeriod) {
        SOURCE = source;
        CURRENCY_TYPE = currencyType;
        ALGORITHM_TYPE = algorithmType;
        LENGTH_PERIOD = lengthPeriod;

        currentCurrencyStatistic = new CurrencyStatistic();
        predictionCurrencyStatistic = new CurrencyStatistic();

        sourceReader = new SourceReader(currentCurrencyStatistic);

        predictionAlgorithm = PredictionAlgorithmFactory.createPredictionAlgorithm_BasedOnAlgorithmType(ALGORITHM_TYPE);
        printPrediction = PrintPredictionFactory.createPrinterPrediction_BasedOnAlgorithmType(ALGORITHM_TYPE);
        log.debug("PredictionService инициализирован");
    }

    /**
     * Метод логики и последовательности действий приложения.
     */
    public CurrencyStatistic executeApplication() {
        log.debug("Начато выполнение программы рассчёта предсказания валют");

        try {
            sourceReader.setup(SOURCE);
            sourceReader.readSource();
        } catch (Exception e) {
            log.error(String.format("Ошибка чтения файла. %s", e));
            return null;
        }

        try {
            predictionAlgorithm.predict(currentCurrencyStatistic, predictionCurrencyStatistic, LENGTH_PERIOD);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }

        printPrediction.print(predictionCurrencyStatistic, CURRENCY_TYPE, LENGTH_PERIOD);

        log.debug("Завершено выполнение программы рассчёта предсказания валют");
        return predictionCurrencyStatistic;
    }
}
