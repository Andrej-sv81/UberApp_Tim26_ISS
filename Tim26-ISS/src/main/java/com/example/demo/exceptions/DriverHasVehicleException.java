package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Driver already has vehicle you can not add new vehicle, you can either change it or update it.")
public class DriverHasVehicleException extends RuntimeException{
}
