package ru.liga.predictionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SourceReader {

    private static final String FILE_IS_EMPTY = "File is empty!";
    private static final String SOURCE_NULL = "source == null";
    private static final String SOURCE_FILE_IS_NOT_EXIST = "source file is not exist";

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
            throw new IllegalArgumentException(SOURCE_NULL);
        }
        file = new File(source);
        if (!file.exists()) {
            throw new IllegalArgumentException(SOURCE_FILE_IS_NOT_EXIST);
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
            System.out.println(FILE_IS_EMPTY);
        }

        while ((line = br.readLine()) != null) {
            new CurrencyAnalyzer(currentCurrencyStatistic).analyze(line);
        }
    }
}
