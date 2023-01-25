package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Number of favorite rides cannot exceed 10!")
public class NumberOfFavoriteRidesException extends RuntimeException{
}
