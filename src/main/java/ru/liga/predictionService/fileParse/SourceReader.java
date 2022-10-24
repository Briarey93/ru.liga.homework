package ru.liga.predictionService.fileParse;

import lombok.extern.slf4j.Slf4j;
import ru.liga.predictionService.data.CurrencyStatistic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Чтение файла.
 */
@Slf4j
public class SourceReader {

    /**
     * Файл для чтения.
     */
    private File file;

    /**
     * Текущие валюты, считываемые из файла.
     */
    private final CurrencyStatistic currentCurrencyStatistic;

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
            throw new IllegalArgumentException("Путь к исходному файлу не задан");
        }
        file = new File(source);
        if (!file.exists()) {
            throw new IllegalArgumentException(String.format("Исходный файл(%s) не доступен", source));
        }
    }

    /**
     * Чтение файла.
     */
    public void readSource() {
        log.debug(String.format("Начато чтение файла %s", file));
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)
        ) {
            processFile(br);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        log.debug(String.format("Завершено чтение файла %s", file));
    }

    /**
     * Построчное чтение файла с запуском анализа
     * и записью данных в статистику.
     *
     * @param br - ридер.
     * @throws IOException - ошибка чтения.
     */
    private void processFile(
            final BufferedReader br)
            throws IOException {
        String line;
        if (br.readLine() == null) {
            log.debug("Файл пуст!");
        }

        while ((line = br.readLine()) != null) {
            new CurrencyAnalyzer(currentCurrencyStatistic).analyze(line);
        }
    }
}
