package co.com.employee.domain.models;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.With;

@Getter
@Builder
@RequiredArgsConstructor
public class Employee {
    private final int id;
    private final String name;
    private final ContractType contractType;
    private final int roleId;
    private final String roleName;
    private final String roleDescription;
    private final double hourlySalary;
    private final double monthlySalary;

    @With
    private final double annualSalary;
}
