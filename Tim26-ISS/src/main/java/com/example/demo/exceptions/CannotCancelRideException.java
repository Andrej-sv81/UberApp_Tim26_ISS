package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Cannot cancel a ride that is not in status PENDING or STARTED!")
public class CannotCancelRideException extends RuntimeException {
}
