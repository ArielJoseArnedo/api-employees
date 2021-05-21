package co.com.employee.domain.services;

import co.com.ajac.concurrency.FutureEither;
import co.com.employee.domain.errors.AppError;
import co.com.employee.domain.models.ContractType;
import co.com.employee.domain.models.Employee;
import co.com.employee.domain.ports.EmployeeProvider;
import io.vavr.collection.List;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class EmployeeServiceTest {

    private EmployeeService employeeService;

    @Mock
    private EmployeeProvider employeeProvider;

    @BeforeEach
    void setUp() {
        this.employeeService = new EmployeeService(employeeProvider);
    }

    @Test
    void calculateAnualSalaryAll() {
        final Employee employeeHourlySalary = Employee.builder()
          .id(1)
          .contractType(ContractType.HOURLY_SALARY)
          .hourlySalary(15.0)
          .build();

        final Employee employeeMonthlySalary = Employee.builder()
          .id(2)
          .contractType(ContractType.MONTHLY_SALARY)
          .monthlySalary(12.0)
          .build();

        final List<Employee> listEmployees = List.of(employeeHourlySalary, employeeMonthlySalary);

        when(employeeProvider.getEmployees()).thenReturn(FutureEither.right(listEmployees));

        final FutureEither<AppError, List<Employee>> allEmployeesFuture = employeeService.calculateAnualSalaryAllEmployees();

        final Either<AppError, List<Employee>> listEmployeesListEither = allEmployeesFuture.getValue().get();

        assertThat(listEmployeesListEither.isRight()).isTrue();
        assertThat(listEmployeesListEither.get()).hasSize(2);
    }

    @Test
    void calculateAnualSalaryOneEmployee() {
        final Employee employeeHourlySalary = Employee.builder()
          .id(1)
          .contractType(ContractType.HOURLY_SALARY)
          .hourlySalary(15.0)
          .build();

        final Employee employeeMonthlySalary = Employee.builder()
          .id(2)
          .contractType(ContractType.MONTHLY_SALARY)
          .monthlySalary(12.0)
          .build();

        final List<Employee> listEmployees = List.of(employeeHourlySalary, employeeMonthlySalary);

        when(employeeProvider.getEmployees()).thenReturn(FutureEither.right(listEmployees));

        final FutureEither<AppError, Employee> result = employeeService.calculateAnualSalaryOneEmpleyee(1);

        final Either<AppError, Employee> employeesEither = result.getValue().get();

        assertThat(employeesEither.isRight()).isTrue();
        assertThat(employeesEither.get()).satisfies(employee -> {
           assertThat(employee.getId()).isEqualTo(1);
           assertThat(employee.getHourlySalary()).isEqualTo(15.0);
           assertThat(employee.getAnnualSalary()).isEqualTo(21600.0);
        });
    }

    @Test
    void calculateAnualSalaryToContractTypeHourlySalaryEmployee() {
        final Employee employee = Employee.builder()
          .contractType(ContractType.HOURLY_SALARY)
          .hourlySalary(15.0)
          .build();

        final Employee employeeResult = employeeService.calculateAnualSalary(employee);

        assertThat(employeeResult.getAnnualSalary())
          .as("The calculation of the annual salary for a hourly salary contract for input %s", employee.getMonthlySalary())
          .isEqualTo(21600.0);
    }

    @Test
    void calculateAnualSalaryToContractTypeMonthlySalaryEmployee() {
        final Employee employee = Employee.builder()
          .contractType(ContractType.MONTHLY_SALARY)
          .monthlySalary(12.0)
          .build();

        final Employee employeeResult = employeeService.calculateAnualSalary(employee);

        assertThat(employeeResult.getAnnualSalary())
          .as("The calculation of the annual salary for a monthly salary contract for input %s", employee.getMonthlySalary())
          .isEqualTo(17280.0);
    }

    @Test
    void calculateAnualSalaryFromHourlySalary() {
        final double hourlySalary = 10.0;
        final double anualSalary = employeeService.calculateAnualSalaryFromHourlySalary(hourlySalary);

        assertThat(anualSalary)
          .as("The result of the annual salary calculation operation should be: 14400.0")
          .isEqualTo(14400.0);
    }

    @Test
    void calculateAnualSalaryFromMonthlySalary() {
        final double monthlySalary = 11.0;
        final double anualSalary = employeeService.calculateAnualSalaryFromMonthlySalary(monthlySalary);

        assertThat(anualSalary)
          .as("The result of the annual salary calculation operation should be: 15840.0")
          .isEqualTo(15840.0);
    }
}