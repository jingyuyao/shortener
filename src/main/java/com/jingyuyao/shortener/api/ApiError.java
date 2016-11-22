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

    /**
     * Creates an {@link ApiError} from a set of {@link ConstraintViolation}s
     * @param violations the set of {@link ConstraintViolation}s for this error
     * @return an {@link ApiError} with a list of error messages extracted from the violations
     */
    public static <T> ApiError create(Set<ConstraintViolation<T>> violations) {
        // TODO: Gotta know the fields causing the erros
        List<String> errorMessages = violations
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return new ApiError(errorMessages);
    }
}
