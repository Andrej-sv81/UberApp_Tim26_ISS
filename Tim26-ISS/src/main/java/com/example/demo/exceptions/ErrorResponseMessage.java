package com.example.demo.exceptions;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ErrorResponseMessage {

    private String message;
}
