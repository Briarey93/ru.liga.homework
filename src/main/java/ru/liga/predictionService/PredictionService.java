package ru.liga.predictionService;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;
import ru.liga.predictionService.fileParse.SourceReader;
import ru.liga.predictionService.predictionAlg.PredictionAlgorithm;
import ru.liga.predictionService.predictionPrinter.PrintPrediction;

@Slf4j
public class PredictionService {

    private final String SOURCE;
    private final String CURRENCY_TYPE;
    private final int LENGTH_PERIOD;

    private final SourceReader sourceReader;

    private final CurrencyStatistic currentCurrencyStatistic;
    private final CurrencyStatistic predictionCurrencyStatistic;

    private final PredictionAlgorithm predictionAlgorithm;
    private final PrintPrediction printPrediction;


    public PredictionService(final String source,
                             final String currencyType,
                             final String algorithmType,
                             final int lengthPeriod) {
        SOURCE = source;
        CURRENCY_TYPE = currencyType;
        LENGTH_PERIOD = lengthPeriod;

        currentCurrencyStatistic = new CurrencyStatistic();
        predictionCurrencyStatistic = new CurrencyStatistic();

        sourceReader = new SourceReader(currentCurrencyStatistic);

        predictionAlgorithm = PredictionAlgorithm.getPredictionAlgorithm(algorithmType);
        printPrediction = PrintPrediction.getPrintPrediction(algorithmType);
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
            e.printStackTrace();
            return null;
        }

        printPrediction.print(predictionCurrencyStatistic, CURRENCY_TYPE, LENGTH_PERIOD);

        log.debug("Завершено выполнение программы рассчёта предсказания валют");
        return predictionCurrencyStatistic;
    }
}
