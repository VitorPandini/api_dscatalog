package com.vitorpandini.dscatalog.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class StandardError {

    private Instant timestamp;

    private String error;
    private String message;

    private String path;


}
