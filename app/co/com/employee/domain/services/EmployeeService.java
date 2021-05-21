package co.com.employee.domain.services;

import co.com.ajac.concurrency.FutureEither;
import co.com.employee.domain.errors.AppError;
import co.com.employee.domain.errors.BusinessError;
import co.com.employee.domain.models.ContractType;
import co.com.employee.domain.models.Employee;
import co.com.employee.domain.ports.EmployeeProvider;
import io.vavr.collection.List;

import javax.inject.Inject;

import static io.vavr.API.*;

public class EmployeeService {

    private final EmployeeProvider employeeProvider;

    @Inject
    public EmployeeService(EmployeeProvider employeeProvider) {
        this.employeeProvider = employeeProvider;
    }

    public FutureEither<AppError, List<Employee>> calculateAnualSalaryAllEmployees() {
        return employeeProvider.getEmployees()
          .map(listEmployees -> listEmployees
            .map(this::calculateAnualSalary)
          );
    }

    public FutureEither<AppError, Employee> calculateAnualSalaryOneEmpleyee(int idEmployee) {
        return employeeProvider.getEmployees()
          .flatMap(listEmployees -> FutureEither.fromEither(
            listEmployees
              .find(employee -> employee.getId() == idEmployee)
              .map(this::calculateAnualSalary)
              .toEither(BusinessError.EMPLOYEE_NOT_FOUND)
            )
          );
    }

    public Employee calculateAnualSalary(Employee employee) {
        final Double anualSalary = Match(employee.getContractType()).of(
          Case($(ContractType.HOURLY_SALARY), () -> calculateAnualSalaryFromHourlySalary(employee.getHourlySalary())),
          Case($(ContractType.MONTHLY_SALARY), () -> calculateAnualSalaryFromMonthlySalary(employee.getMonthlySalary())),
          Case($(), () -> 0.0)
        );

        return employee
          .withAnnualSalary(anualSalary);
    }

    public double calculateAnualSalaryFromHourlySalary(double hourlySalary) {
        return 120 * hourlySalary * 12;
    }

    public double calculateAnualSalaryFromMonthlySalary(double monthlySalary) {
        return 120 * monthlySalary * 12;
    }
}
