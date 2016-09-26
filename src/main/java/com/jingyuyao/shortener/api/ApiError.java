package com.jingyuyao.shortener.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base for all the errors returned by the service.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private List<String> errorMessages;

    public static <T> ApiError create(Set<ConstraintViolation<T>> violations) {
        List<String> errorMessages = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return new ApiError(errorMessages);
    }
}
