package com.etc.orderms.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ApiErrorResponse {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private List<String> details;
}
