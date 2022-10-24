package ru.liga.predictionService.predictionPrinter;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
public class PrintPredictionAverage implements PrintPrediction {

    @Override
    public void print(CurrencyStatistic predictedCurrencyStatistic,
                      String currencyType,
                      int lengthPeriod) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE dd.MM.yyyy", Locale.ENGLISH);
        StringBuilder msg = new StringBuilder();

        switch (lengthPeriod) {
            case (1):
                msg.append(String.format("\"rate %s day\" ", currencyType));
                break;

            case (7):
                msg.append(String.format("\"rate %s week\"\n", currencyType));
                break;

            case (30):
                msg.append(String.format("\"rate %s month\"\n", currencyType));
                break;
        }

        for (int i = 0; i < lengthPeriod; i++) {
            msg.append(String.format("\t%s - ", predictedCurrencyStatistic.getRowsDto().get(i).getDate().format(formatter)));
            msg.append(String.format("%.2f\n", predictedCurrencyStatistic.getRowsDto().get(i).getCurrency()));
        }
        log.debug("\n" + msg);
    }
}
