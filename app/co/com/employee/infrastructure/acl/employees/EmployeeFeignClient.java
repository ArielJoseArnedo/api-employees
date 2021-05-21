package co.com.employee.infrastructure.acl.employees;

import co.com.employee.infrastructure.acl.employees.responses.EmployeeResponse;
import feign.Headers;
import feign.RequestLine;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface EmployeeFeignClient {
    @RequestLine("GET /api/employees")
    @Headers({
      "Content-Type: application/json"
    })
    CompletableFuture<List<EmployeeResponse>> consultEmployees();
}
