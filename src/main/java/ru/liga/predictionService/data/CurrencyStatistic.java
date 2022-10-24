package ru.liga.predictionService.data;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс агрегирующий статистику валюты.
 */
@Getter
public class CurrencyStatistic {

    private List<RowDto> rowsDto = new ArrayList<>();

    public void addRow(RowDto row) {
        rowsDto.add(row);
    }

    public void addRowAll(List<RowDto> rowsDto) {
        this.rowsDto.addAll(rowsDto);

    }
}
