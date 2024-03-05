package com.pokemon.demo.controller;

import com.pokemon.demo.dto.GameDto;
import com.pokemon.demo.entity.Condition;
import com.pokemon.demo.entity.Game;
import com.pokemon.demo.entity.GamePlatform;
import com.pokemon.demo.exception.GameNotFoundException;
import com.pokemon.demo.repository.GameRepository;
import com.pokemon.demo.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/games")
public class GameController {

    /*
     * Entry point for any HTTP Request and sends response back to the client
     * Starting with the /games
     */

    private final GameService gameService;

    @Autowired
    public GameController (GameService gameService) {
        this.gameService = gameService;
    }

    // public ResponseEntity<>


    @GetMapping("/select")
    public ResponseEntity<List<GameDto>> getGameSelection () {

        /*
         Get: Request, This is the initial page
         This will show all the images and redirect the user to the gameName page
         where they can see ALL data for the game they have chosen
         */

        List<GameDto> listOfGames = gameService.getGameSelection();
        return ResponseEntity.ok(listOfGames);
    }

    @GetMapping("/select/{gameName}")
    public ResponseEntity<GameDto> getSelectedGame(@PathVariable String gameName) {
            GameDto gameDto = gameService.findGameByTitle(gameName);
            return ResponseEntity.ok(gameDto);
    }




}
