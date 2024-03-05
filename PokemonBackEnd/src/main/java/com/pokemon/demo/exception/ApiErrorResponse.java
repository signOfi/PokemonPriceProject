package com.pokemon.demo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiErrorResponse {

    private String error;
    private String message;

}
