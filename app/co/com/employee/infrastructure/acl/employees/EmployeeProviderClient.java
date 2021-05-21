package co.com.employee.infrastructure.acl.employees;

import co.com.ajac.concurrency.FutureEither;
import co.com.employee.domain.errors.AppError;
import co.com.employee.domain.models.Employee;
import co.com.employee.domain.ports.EmployeeProvider;
import co.com.employee.infrastructure.acl.employees.responses.EmployeeResponse;
import co.com.employee.infrastructure.acl.errors.AclError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.AsyncFeign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Either;

import javax.inject.Inject;

public class EmployeeProviderClient implements EmployeeProvider {

    private static final String URL_EMPLOYEE = "http://masglobaltestapi.azurewebsites.net";

    private final EmployeeFeignClient employeeFeignClient;

    @Inject
    public EmployeeProviderClient(ObjectMapper mapper) {
        this.employeeFeignClient = AsyncFeign.asyncBuilder()
          .decoder(new JacksonDecoder(mapper))
          .encoder(new JacksonEncoder(mapper))
          .logger(new Slf4jLogger(EmployeeProviderClient.class))
          .logLevel(Logger.Level.FULL)
          .target(EmployeeFeignClient.class, URL_EMPLOYEE);
    }

    @Override
    public FutureEither<AppError, List<Employee>> getEmployees() {
        return FutureEither.of(consultEmployees())
          .map(listEmployees -> listEmployees.map(EmployeeTransformer::toDomain));
    }

    private Future<Either<AppError, List<EmployeeResponse>>> consultEmployees() {
        return Future.fromCompletableFuture(employeeFeignClient.consultEmployees())
          .map(listJava -> Either.<AppError, List<EmployeeResponse>>right(List.ofAll(listJava)))
          .recover(throwable -> Either.left(AclError.ERROR_QUERYING_EMPLOYEES));
    }
}
