package co.com.employee.infrastructure.interfaces.controllers;

import co.com.employee.infrastructure.interfaces.queries.EmployeeQuery;
import io.vavr.control.Option;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Results;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class EmployeeController extends Controller {

    private final EmployeeQuery employeeQuery;

    @Inject
    public EmployeeController(EmployeeQuery employeeQuery) {
        this.employeeQuery = employeeQuery;
    }

    public CompletionStage<Result> getOrAllEmployees(Integer idEmployee) {
        final Option<Integer> idEmployeeOpt = Option.of(idEmployee);
        return employeeQuery.execute(idEmployeeOpt)
          .fold(appError ->
              badRequest(Json.toJson(appError)),
              Results::ok
          )
          .recover(throwable -> internalServerError())
          .toCompletableFuture();
    }
}
