package com.jingyuyao.shortner.api;

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
public class Error {
    private List<String> errorMessages;

    public static <T> Error create(Set<ConstraintViolation<T>> violations) {
        List<String> errorMessages = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return new Error(errorMessages);
    }
}
