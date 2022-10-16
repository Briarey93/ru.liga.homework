package ru.liga.telegramBotService;

import lombok.Getter;

@Getter
public class Settings {

    private String source;

    private String algorithm;

    private String period;

    public Settings(String source, String algorithm, String period) {
        this.source = source;
        this.algorithm = algorithm;
        this.period = period;
    }
}
