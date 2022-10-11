package ru.liga.predictionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {

    /**
     * Файл для чтения.
     */
    private File file;

    private CurrencyStatistic currentCurrencyStatistic;

    public SourceReader(CurrencyStatistic currentCurrencyStatistic) {
        this.currentCurrencyStatistic = currentCurrencyStatistic;
    }

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
     */
    public void readSource() {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)
        ) {
            processFile(br);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Построчное чтение файла с запуском анализа
     * и записью данных в статистику.     *
     *
     * @param br - ридер.
     * @throws IOException - ошибка чтения.
     */
    private void processFile(
            final BufferedReader br)
            throws IOException {
        String line;
        if (br.readLine() == null) {
            System.out.println("File is empty!");
        }

        while ((line = br.readLine()) != null) {
            new CurrencyAnalyzer(currentCurrencyStatistic).analyze(line);
        }
    }
}
