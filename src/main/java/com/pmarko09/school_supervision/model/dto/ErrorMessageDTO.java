package com.pmarko09.school_supervision.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorMessageDTO {

    private String message;
    private LocalDateTime time;
    private HttpStatus httpStatus;

}
