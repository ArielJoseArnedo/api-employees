package co.com.employee.domain.ports;

import co.com.ajac.concurrency.FutureEither;
import co.com.employee.domain.errors.AppError;
import co.com.employee.domain.models.Employee;
import io.vavr.collection.List;

public interface EmployeeProvider {
    FutureEither<AppError, List<Employee>> getEmployees();
}
