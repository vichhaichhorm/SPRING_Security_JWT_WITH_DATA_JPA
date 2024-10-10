package com.example.GROUP1_ASSIGMENT_PROJECT.modal.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse <T>{
    String message;
    HttpStatus status;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = CodeFilter.class)
    int code;
    T payload;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    String token;
    public static class CodeFilter {
        @Override
        public boolean equals(Object other) {
            return (other instanceof Integer) && ((Integer) other) == 0;
        }
    }
}
