package com.pokemon.demo.controller;

import com.pokemon.demo.service.GameDataImportService;
import com.pokemon.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataImportController {

    /* RestEndpoint to refresh/trigger the web scraping */

    private final GameDataImportService gameDataImportService;
    private final GameService gameService;


    @Autowired
    public DataImportController(GameDataImportService gameDataImportService, GameService gameService) {
        this.gameDataImportService = gameDataImportService;
        this.gameService = gameService;
    }

    @GetMapping("/refresh")
    public String importData() {
        gameDataImportService.executeScriptAndProcessData();
        return "Refreshing all data has started, aka web scraping";
    }

    @GetMapping("/setup")
    public void setImages() {
        /* This method is only called by admin since images are static and do NOT need to constantly need refresh */
        gameService.insertImageSource();
    }



}
