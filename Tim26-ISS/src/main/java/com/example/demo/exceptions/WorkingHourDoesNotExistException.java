package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "Workig hour with this id does not exist")
public class WorkingHourDoesNotExistException extends RuntimeException{
}
