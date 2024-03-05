package com.pokemon.demo.dto;

import com.pokemon.demo.entity.Game;
import com.pokemon.demo.entity.GamePlatform;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GameDto {

    /* Data used for frontend and backend */

    private long id;
    private String title;
    private String imageUrl;
    private GamePlatform gamePlatform;

    private BigDecimal loosePrice;
    private BigDecimal cibPrice;
    private BigDecimal newPrice;

    /* Constructor used for main~page */
    public GameDto(String title, String imageUrl, GamePlatform gamePlatform) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.gamePlatform = gamePlatform;
    }
    public GameDto(String title, GamePlatform gamePlatform, BigDecimal cibPrice,
                   BigDecimal loosePrice, BigDecimal newPrice, String imageUrl) {
        this.title = title;
        this.gamePlatform = gamePlatform;
        this.cibPrice = cibPrice;
        this.loosePrice = loosePrice;
        this.newPrice = newPrice;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "GameDto{" +
                "title='" + title + '\'' +
                ", gamePlatform=" + gamePlatform +
                ", loosePrice=" + loosePrice +
                ", cibPrice=" + cibPrice +
                ", newPrice=" + newPrice +
                '}';
    }
}
