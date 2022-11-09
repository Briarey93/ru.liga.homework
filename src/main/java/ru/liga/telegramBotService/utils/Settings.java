package ru.liga.telegramBotService.utils;

import lombok.Getter;

/**
 * Пользовательские настройки.
 */
@Getter
public class Settings {

    /**
     * Источник валюты.
     */
    private String source;

    /**
     * Алгорифм рассчёта.
     */
    private String algorithm;

    /**
     * Период рассчёта.
     */
    private String period;

    public Settings(String source, String algorithm, String period) {
        this.source = source;
        this.algorithm = algorithm;
        this.period = period;
    }
}
