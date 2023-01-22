package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "User Id does not belong to the currently logged-in user!")
public class UserIdNotMatchingException extends RuntimeException {
}
