package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Favorite location does not exist!")
public class FavoriteRideDoesNotExistException extends RuntimeException {
}
