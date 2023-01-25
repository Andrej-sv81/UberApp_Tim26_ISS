package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cannot start a ride that is not in status ACCEPTED!")
public class CannotStartRideException extends RuntimeException {
}
