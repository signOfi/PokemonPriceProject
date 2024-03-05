package com.pokemon.demo.service;

import com.pokemon.demo.entity.Game;

import java.util.List;

public interface CsvService {

    List<Game> readCsv(String csvFilePath);

}
