package com.pokemon.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table (name="games")
@Data
@NoArgsConstructor
public class Game {

    /* This class represents a single Pokémon title */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private GamePlatform gamePlatform;

    @Column(precision = 10, scale = 2)
    private BigDecimal loosePrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal cibPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal newPrice;

    @Column(name = "image_path")
    private String imagePath;

    public Game(String title, GamePlatform gamePlatform,
                BigDecimal loosePrice, BigDecimal cibPrice, BigDecimal newPrice) {
        this.title = title;
        this.gamePlatform = gamePlatform;
        this.loosePrice = loosePrice;
        this.cibPrice = cibPrice;
        this.newPrice = newPrice;

    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", gamePlatform=" + gamePlatform +
                ", loosePrice=" + loosePrice +
                ", cibPrice=" + cibPrice +
                ", newPrice=" + newPrice +
                '}';
    }
}
