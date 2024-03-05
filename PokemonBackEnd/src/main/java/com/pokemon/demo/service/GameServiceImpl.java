package com.pokemon.demo.service;

import com.pokemon.demo.dto.GameDto;
import com.pokemon.demo.entity.Game;
import com.pokemon.demo.exception.GameNotFoundException;
import com.pokemon.demo.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl (GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public void insertImageSource() {

        /* This method just sets the image to the corresponding game */
        /* Only for the main line games, so no off or special games applicable */

        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            String title = checkIfWeHaveAnImage(game.getTitle());
            game.setImagePath(title);
            gameRepository.save(game);
        }
    }

    private String checkIfWeHaveAnImage(String title) {

        /* Sets an image for each game if we have a matching image for it */

        return switch (title) {

            /* GB/GBC Games */
            case "Pokemon Red" -> "pkmnRed.webp";
            case "Pokemon Blue" -> "pkmnBlue.webp";
            case "Pokemon Yellow" -> "pkmnYellow.webp";
            case "Pokemon Gold" -> "pkmnGold.webp";
            case "Pokemon Silver" -> "pkmnSilver.webp";
            case "Pokemon Crystal" -> "pkmnCrystal.webp";

            /* GBA Games */
            case "Pokemon LeafGreen Version" -> "pkmnLeafGreen.jpeg";
            case "Pokemon FireRed" -> "pkmnFireRed.webp";
            case "Pokemon Ruby" -> "pkmnRuby.webp";
            case "Pokemon Sapphire" -> "pkmnSapphire.webp";
            case "Pokemon Emerald" -> "pkmnEmerald.webp";

            /* DS Games */
            case "Pokemon Diamond" -> "pkmnDiamond.webp";
            case "Pokemon Pearl" -> "pkmnPearl.webp";
            case "Pokemon Platinum" -> "pkmnPlatinum.webp";
            case "Pokemon HeartGold Version" -> "pkmnHeartGold.webp";
            case "Pokemon SoulSilver Version" -> "pkmnSoulSilver.webp";
            case "Pokemon Black" -> "pkmnBlack.webp";
            case "Pokemon White" -> "pkmnWhite.webp";
            case "Pokemon Black Version 2" -> "pkmnBlack2.webp";
            case "Pokemon White Version 2" -> "pkmnWhite2.jpeg";

            /* GameCube Games */
            case "Pokemon XD: Gale of Darkness" -> "pkmnGaleOfDarkness.webp";
            case "Pokemon Colosseum" -> "pkmnColosseum.jpeg";

            /* 3DS Games */
            case "Pokemon X" -> "pkmnX.webp";
            case "Pokemon Y" -> "pkmnY.jpeg";
            case "Pokemon Omega Ruby" -> "pkmnOmegaRuby.webp";
            case "Pokemon Alpha Sapphire" -> "pkmnAlphaSappire.webp";
            case "Pokemon Sun" -> "pkmnSun.webp";
            case "Pokemon Moon" -> "pkmnMoon.webp";
            case "Pokemon Ultra Sun" -> "pkmnUltrasun.webp";
            case "Pokemon Ultra Moon" -> "pkmnUltramoon.webp";

            /* Nintendo Switch Games */
            case "Pokemon Let's Go Pikachu" -> "pkmnLetsPikachu.webp";
            case "Pokemon Let's Go Eevee" -> "pkmnLetsEevee.webp";
            case "Pokemon Sword" -> "pkmnSword.webp";
            case "Pokemon Shield" -> "pkmnShield.webp";
            case "Pokemon Brilliant Diamond" -> "pkmnBrilliantDiamond.webp";
            case "Pokemon Shining Pearl" -> "pkmnShiningPearl.webp";
            case "Pokemon Legends: Arceus" -> "pkmnLegendsArceus.webp";
            case "Pokemon Scarlet" -> "pkmnScarlet.webp";
            case "Pokemon Violet" -> "pkmnViolet.webp";

            /* Any other game hard card an invalid web page */
            default -> "invalid.webp";
        };
    }

    public List<GameDto> getGameSelection() {

        List<Game> games = gameRepository.findAll();

        return games.stream()
                .map( game -> new GameDto(game.getTitle(), game.getImagePath(), game.getGamePlatform()) )
                .toList();
    }

    @Override
    public GameDto findGameByTitle(String title) throws GameNotFoundException {

        /* Need to find the Game by title then convert to a DTO */
        Game game = gameRepository.findByTitle(title)
                .orElseThrow( () -> new GameNotFoundException("Game with title " + title + " not found") );

        /* Convert to gameDto to return back */
        return new GameDto(game.getTitle(), game.getGamePlatform(), game.getCibPrice(), game.getLoosePrice(),
                game.getNewPrice(), game.getImagePath());

    }
}

