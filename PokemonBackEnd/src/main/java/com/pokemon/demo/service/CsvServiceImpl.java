package com.pokemon.demo.service;

import com.pokemon.demo.entity.Game;
import com.pokemon.demo.entity.GamePlatform;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvServiceImpl implements CsvService {

    public BigDecimal parseToBigDecimal(String value) {

        /* Need to make sure we are able to parse any string in the CSV */

        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public GamePlatform matchGamePlatform(String str) {

        /* Need to match game~platform according to what read from the CSV file  */
        if (str.equals("gameboy-color") || str.equals("gameboy")) return GamePlatform.GB_GBC;
        if (str.equals("nintendo-3ds")) return GamePlatform.DS3D;
        if (str.equals("nintendo-ds"))  return GamePlatform.DS;
        if (str.equals("gameboy-advance")) return GamePlatform.GBA;
        if (str.equals("nintendo-switch")) return GamePlatform.SWITCH;
        if (str.equals("gamecube")) return GamePlatform.GAMECUBE;
        else return GamePlatform.OTHER;
    }

    public boolean validGame(Game game) {

        /* If A game does not have a CIB or NEW then this game is not a mass-produced game */
        /* Such as distribution cartridges that were only sold to employees, or we have a game~platform of other  */

        /* Check if the game has a title */
        boolean hasTitle = game.getTitle() != null;

        /* Check if it has a new price */
        boolean hasNewPrice = game.getNewPrice() != null ;

        /* Check if it has a cib price */
        boolean hasCibPrice = game.getCibPrice() != null;

        /* For Pokemon, we simply do not care about other platforms */
        boolean isNotOtherPlatform = game.getGamePlatform() != GamePlatform.OTHER;

        /* The game is valid only if all conditions are true */
        return hasTitle && hasNewPrice && hasCibPrice && isNotOtherPlatform;

    }



    public List<Game> readCsv(String csvFilePath) {

        /* Create a list of games */
        List<Game> gameList = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(csvFilePath))) {

            /* The First line has junk information, so we skip the first line */
            bufferedReader.readLine();
            String line;

            while ( (line = bufferedReader.readLine()) != null ) {
                String[] fields = line.split(",");

                /*
                 * Example Data From CSV file
                 * Title,Console,Loose Price,Complete Price,New Price
                 * Pokemon Omega Ruby,nintendo-3ds,26.75,32.99,51.59
                 */

                 /* Create a new game and to the array list */
                 Game newGame = new Game();
                 newGame.setTitle(fields[0]);
                 newGame.setGamePlatform(matchGamePlatform(fields[1]));
                 newGame.setLoosePrice(parseToBigDecimal(fields[2]));
                 newGame.setCibPrice(parseToBigDecimal(fields[3]));
                 newGame.setNewPrice(parseToBigDecimal(fields[4]));

                 /* Iff valid game we add it to our DataBase */
                 if (validGame(newGame)) gameList.add(newGame);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return gameList;
    }
}
