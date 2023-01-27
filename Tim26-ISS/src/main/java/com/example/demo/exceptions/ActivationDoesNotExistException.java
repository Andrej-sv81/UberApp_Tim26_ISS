package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Activation with entered id does not exist!")
public class ActivationDoesNotExistException extends RuntimeException {
}
