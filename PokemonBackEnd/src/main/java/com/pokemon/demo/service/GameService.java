package com.pokemon.demo.service;

import com.pokemon.demo.dto.GameDto;

import java.util.List;

public interface GameService {

    /* Uses GameListingServiceRepository to interact with database and API
     * Processes data for controller use */

    List<GameDto> getGameSelection();

    GameDto findGameByTitle(String title);

    void insertImageSource();




}
