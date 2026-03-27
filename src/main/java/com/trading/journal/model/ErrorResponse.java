package com.trading.journal.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @Schema(description = "The name of the parameter", example = "parameterName")
    private String parameter;

    @Schema(description = "The description of the problem", example = "Invalid value provided")
    private String problem;

    @Schema(description = "The value submitted for the parameter", example = "12345")
    private String valueSubmitted;
}