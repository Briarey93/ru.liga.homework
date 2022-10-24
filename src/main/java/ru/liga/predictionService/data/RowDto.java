package ru.liga.predictionService.data;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class RowDto {
    private BigDecimal currency;
    private LocalDate date;


    public void setDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.M.yyyy", Locale.ENGLISH);
        date = LocalDate.parse(value, formatter);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCurrency(String value, String nominal) {
        String updatedValue = value.replaceAll(",", ".");
        String updatedNominal = nominal.replaceAll(" ", "");

        int nominalInt = Integer.parseInt(updatedNominal);

        currency = new BigDecimal(updatedValue)
                .divide(BigDecimal.valueOf(nominalInt), 2, RoundingMode.HALF_UP);
    }

    public void setCurrency(BigDecimal currency) {
        this.currency = currency;
    }
}
