package co.com.employee.infrastructure.interfaces.queries;

import co.com.ajac.concurrency.FutureEither;
import co.com.employee.domain.errors.AppError;
import co.com.employee.domain.services.EmployeeService;
import com.fasterxml.jackson.databind.JsonNode;
import io.vavr.collection.List;
import io.vavr.control.Option;
import play.libs.Json;

import javax.inject.Inject;

import static io.vavr.API.*;
import static io.vavr.Patterns.$None;
import static io.vavr.Patterns.$Some;

public class EmployeeQuery {

    private final EmployeeService employeeService;

    @Inject
    public EmployeeQuery(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public FutureEither<AppError, JsonNode> execute(Option<Integer> idEmployeeOpt) {
        return Match(idEmployeeOpt).of(
          Case($None(), () -> employeeService.calculateAnualSalaryAllEmployees().map(List::toJavaList).map(Json::toJson)),
          Case($Some($()), idEmployee -> employeeService.calculateAnualSalaryOneEmpleyee(idEmployee).map(Json::toJson))
        );
    }
}
