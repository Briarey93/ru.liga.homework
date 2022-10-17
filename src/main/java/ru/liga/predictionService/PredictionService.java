package ru.liga.predictionService;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmFactoryInitializer;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithmFactory;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;

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

        PredictionAlgorithmFactory predictionAlgorithmFactory =
                PredictionAlgorithmFactoryInitializer
                        .createPredictionAlgorithm_AndPrinterPrediction_BasedOnAlgorithmType(ALGORITHM_TYPE);
        predictionAlgorithm = predictionAlgorithmFactory.createPredictionAlgorithm();
        printPrediction = predictionAlgorithmFactory.createPrintPrediction();
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
            System.out.println("Ошибка чтения файла.");
            System.out.println(e.getMessage());
            return null;
        }

        try {
            predictionAlgorithm.predict(currentCurrencyStatistic, predictionCurrencyStatistic, LENGTH_PERIOD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

        printPrediction.print(predictionCurrencyStatistic, CURRENCY_TYPE, LENGTH_PERIOD);
        // TODO: добавить везде где можно/нужно джава доки.
        // TODO: весь вывод должен быть на одном языке во всех файлах.
        // TODO: @Slf4j добавить классам.

        log.debug("Завершено выполнение программы рассчёта предсказания валют");
        return predictionCurrencyStatistic;
    }
}
