package com.etc.orderms.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Standard API error response returned to clients
 * when an exception occurs.
 */
@Data
@Builder
public class ApiErrorResponse {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private List<String> details;
}
