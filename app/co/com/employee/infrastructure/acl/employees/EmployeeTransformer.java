package co.com.employee.infrastructure.acl.employees;

import co.com.employee.domain.models.ContractType;
import co.com.employee.domain.models.Employee;
import co.com.employee.infrastructure.acl.employees.responses.EmployeeResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
class EmployeeTransformer {

    public static Employee toDomain(EmployeeResponse employeeResponse) {
        return Employee.builder()
          .id(employeeResponse.getId())
          .name(employeeResponse.getName())
          .contractType(ContractType.fromName(employeeResponse.getContractTypeName()))
          .roleId(employeeResponse.getRoleId())
          .roleName(employeeResponse.getRoleName())
          .roleDescription(employeeResponse.getRoleDescription())
          .hourlySalary(employeeResponse.getHourlySalary())
          .monthlySalary(employeeResponse.getMonthlySalary())
          .build();
    }
}
