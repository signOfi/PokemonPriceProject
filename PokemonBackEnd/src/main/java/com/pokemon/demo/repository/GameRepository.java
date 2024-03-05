package com.pokemon.demo.repository;

import com.pokemon.demo.entity.Game;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    /* CRUD Operations on Game Entity */

    @NonNull
    List<Game> findAll();

    Optional<Game> findByTitle(String title);


}
