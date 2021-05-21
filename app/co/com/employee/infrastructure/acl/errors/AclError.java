package co.com.employee.infrastructure.acl.errors;

import co.com.employee.domain.errors.AppError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AclError implements AppError {

    ERROR_QUERYING_EMPLOYEES("Error querying employees to the API.");

    private final String name;

    @Override
    public String message() {
        return name;
    }
}
