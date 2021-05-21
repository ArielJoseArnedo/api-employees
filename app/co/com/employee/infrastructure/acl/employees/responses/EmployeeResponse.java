package co.com.employee.infrastructure.acl.employees.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeResponse {
    private int id;
    private String name;
    private String contractTypeName;
    private int roleId;
    private String roleName;
    private String roleDescription;
    private double hourlySalary;
    private double monthlySalary;
}
