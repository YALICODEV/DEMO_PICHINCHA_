package com.banco.demosoporte.presentation.advice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class HttpErrorResponse {
    private String message;
    private int status;
    private Map<String, String> errors;

    @JsonProperty("general_errors")
    private List<String> generalErrors;
}

