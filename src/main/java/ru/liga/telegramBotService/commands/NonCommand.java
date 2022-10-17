package ru.liga.telegramBotService.commands;

import lombok.extern.slf4j.Slf4j;
import ru.liga.telegramBotService.TelegramBotService;
import ru.liga.telegramBotService.exception.IllegalArgumentAlgException;
import ru.liga.telegramBotService.exception.IllegalArgumentPrdException;
import ru.liga.telegramBotService.exception.IllegalArgumentRateException;
import ru.liga.telegramBotService.utils.Constants;
import ru.liga.telegramBotService.utils.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Обработка сообщения, не являющегося командой.
 */
@Slf4j
public class NonCommand {

    /**
     * Обработка сообщения, не являющегося командой.
     */
    public String nonCommandExecute(Long chatId, String userName, String text) {
        log.debug(String.format("Пользователь %s. Начата обработка сообщения \"%s\", не являющегося командой",
                userName, text));

        String answer;

        try {
            log.debug(String.format("Пользователь %s. Пробуем создать объект настроек из сообщения \"%s\"",
                    userName, text));

            Settings settings = createSettings(chatId, text);
            saveUserSettings(chatId, settings);

            log.debug(String.format("Пользователь %s. Объект настроек из сообщения \"%s\" создан и сохранён",
                    userName, text));
            answer = "Настройки обновлены. Вы всегда можете их посмотреть с помощью /settings";
        } catch (IllegalArgumentException e) {
            log.debug(String.format("Пользователь %s. Не удалось создать объект настроек из сообщения \"%s\". " +
                    "%s", userName, text, e.getMessage()));
            answer = e.getMessage() +
                    "\n\n❗ Настройки не были изменены. Вы всегда можете их посмотреть с помощью /settings";
        } catch (Exception e) {
            log.debug(String.format("Пользователь %s. Не удалось создать объект настроек из сообщения \"%s\". " +
                    "%s. %s", userName, text, e.getClass().getSimpleName(), e.getMessage()));
            answer = "Простите, я не понимаю Вас. Похоже, что Вы ввели сообщение, не соответствующее формату. " +
                    "\n\n" +
                    "Возможно, Вам поможет /help";
        }

        log.debug(String.format("Пользователь %s. Завершена обработка сообщения \"%s\", не являющегося командой",
                userName, text));
        return answer;
    }

    /**
     * Создание настроек из полученного пользователем сообщения.
     *
     * @param chatId id чата.
     * @param text   пользовательское сообщение.
     * @return новые настройки пользователя.
     * @throws IllegalArgumentException пробрасывается, если сообщение пользователя не соответствует формату.
     */
    private Settings createSettings(Long chatId, String text) {
        //отсекаем файлы, стикеры, гифки и прочий мусор
        if (text == null) {
            throw new IllegalArgumentException("Сообщение не является текстом");
        }

        if (!text.contains("-rate") && !text.contains("-alg") && !text.contains("-prd")) {
            throw new IllegalArgumentException(String.format("Сообщение \"%s\" не содержит команд", text));
        }

        text = text.replaceAll("\n", " ")
                .replaceAll(", ", " ")
                .replaceAll(" {3}", " ")
                .replaceAll(" {2}", " ");

        List<String> parametersList = Arrays.asList(text.split(" "));
        String rate = getRate(chatId, parametersList);
        String algorithm = getAlgorithm(chatId, parametersList);
        String period = getPeriod(chatId, parametersList);


        return new Settings(rate, algorithm, period);
    }

    /**
     * Получение источника валюты от пользовательского сообщения.
     * Если команды "-rate" нет, то возвращает старое значение пользователя.
     * Если пользовательского значения нет, то возвращает значение по умолчанию.
     *
     * @param chatId         id чата.
     * @param parametersList список параметров, введенных пользователем.
     * @return источник валюты.
     * @throws IllegalArgumentRateException пробрасывается, если сообщение пользователя не соответствует формату.
     */
    private String getRate(Long chatId, List<String> parametersList) {
        String result;

        if (!parametersList.contains("-rate")) {
            try {
                result = TelegramBotService.getUserSettings().get(chatId).getSource();
            } catch (Exception e) {
                result = TelegramBotService.getDefaultSettings().getSource();
            }
        } else {
            result = parametersList.get(parametersList.indexOf("-rate") + 1).toUpperCase(Locale.ROOT);
            if (!Constants.getRATE().contains(result)) {
                throw new IllegalArgumentRateException("Недопустимые параметры команды \"-rate\"\n" +
                        "Рекомендую ознакомиться с содержанием команды /source");
            }
        }

        return result;
    }

    /**
     * Получение алгоритма предсказания от пользовательского сообщения.
     * Если команды "-alg" нет, то возвращает старое значение пользователя.
     * Если пользовательского значения нет, то возвращает значение по умолчанию.
     *
     * @param chatId         id чата.
     * @param parametersList список параметров, введенных пользователем.
     * @return алгоритм предсказания.
     * @throws IllegalArgumentAlgException пробрасывается, если сообщение пользователя не соответствует формату.
     */
    private String getAlgorithm(Long chatId, List<String> parametersList) {
        String result;

        if (!parametersList.contains("-alg")) {
            try {
                result = TelegramBotService.getUserSettings().get(chatId).getAlgorithm();
            } catch (Exception e) {
                result = TelegramBotService.getDefaultSettings().getAlgorithm();
            }
        } else {
            result = parametersList.get(parametersList.indexOf("-alg") + 1).toUpperCase(Locale.ROOT);
            if (!Constants.getALGORITHM().contains(result)) {
                throw new IllegalArgumentAlgException("Недопустимые параметры команды \"-alg\"\n" +
                        "Рекомендую ознакомиться с содержанием команды /algorithm");
            }
        }

        return result;
    }

    /**
     * Получение периода предсказания от пользовательского сообщения.
     * Если команды "-prd" нет, то возвращает старое значение пользователя.
     * Если пользовательского значения нет, то возвращает значение по умолчанию.
     *
     * @param chatId         id чата.
     * @param parametersList список параметров, введенных пользователем.
     * @return период предсказания.
     * @throws IllegalArgumentPrdException пробрасывается, если сообщение пользователя не соответствует формату.
     */
    private String getPeriod(Long chatId, List<String> parametersList) {
        String result;

        if (!parametersList.contains("-prd")) {
            try {
                result = TelegramBotService.getUserSettings().get(chatId).getPeriod();
            } catch (Exception e) {
                result = TelegramBotService.getDefaultSettings().getPeriod();
            }
        } else {
            result = parametersList.get(parametersList.indexOf("-prd") + 1).toUpperCase(Locale.ROOT);
            if (!Constants.getPERIOD().contains(result)) {
                throw new IllegalArgumentPrdException("Недопустимые параметры команды \"-prd\"\n" +
                        "Рекомендую ознакомиться с содержанием команды /period");
            }
        }

        return result;
    }

    /**
     * Добавление настроек пользователя в мапу, чтобы потом их использовать для этого пользователя при генерации файла.
     * Если настройки совпадают с дефолтными, они не сохраняются, чтобы впустую не раздувать мапу.
     *
     * @param chatId   id чата
     * @param settings настройки
     */
    private void saveUserSettings(Long chatId, Settings settings) {
        if (!settings.equals(TelegramBotService.getDefaultSettings())) {
            TelegramBotService.getUserSettings().put(chatId, settings);
        } else {
            TelegramBotService.getUserSettings().remove(chatId);
        }
    }
}
