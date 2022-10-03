package ru.liga;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {

    /**
     * Файл для чтения.
     */
    private File file;

    /**
     * Установка файла для чтения.
     *
     * @param source - путь к исходному файлу.
     */
    public void setup(final String source) {
        if (source == null) {
            throw new IllegalArgumentException("source == null");
        }
        file = new File(source);
        if (!file.exists()) {
            throw new IllegalArgumentException("source file is not exist");
        }
    }

    /**
     * Чтение файла.
     *
     * @return - статистика валюты.
     */
    public CurrencyStatistic readSource() {
        CurrencyAnalyzer currencyAnalyzer = new CurrencyAnalyzer();

        try (
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr)
        ) {
            processFile(currencyAnalyzer, br);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        currencyAnalyzer.getCurrencyStatistic().predict();

        return currencyAnalyzer.getCurrencyStatistic();
    }

    /**
     * Построчное чтение файла с запуском анализом.
     *
     * @param currencyAnalyzer - анализатор курсов валют.
     * @param br               - поток чтения файла.
     * @throws IOException - исключение.
     */
    private void processFile(
            final CurrencyAnalyzer currencyAnalyzer,
            final BufferedReader br)
            throws IOException {
        String line;
        if (br.readLine() == null) {
            System.out.println("File is empty!");
        }

        int i = 0;
        while ((line = br.readLine()) != null && i < PredictionAlgorithm.AVERAGE) {
            currencyAnalyzer.analyze(line);
            i++;
        }
    }
}
