package com.pokemon.demo.service;


import java.util.concurrent.CompletableFuture;

public interface DataRefreshService {

    CompletableFuture<String> refreshData();

}
