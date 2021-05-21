package co.com.employee.domain.errors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BusinessError implements AppError {
    EMPLOYEE_NOT_FOUND("The ID provided does not remain with any employee.");

    private final String message;

    @Override
    public String message() {
        return message;
    }
}
