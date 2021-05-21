package co.com.employee.domain.models;

import io.vavr.collection.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ContractType {

    HOURLY_SALARY("HourlySalaryEmployee"),
    MONTHLY_SALARY("MonthlySalaryEmployee"),
    UNKNOWN("");


    private final String name;

    public static ContractType fromName(String contractTypeName) {
        return List.of(ContractType.values())
          .find(contractType -> contractType.name.equals(contractTypeName))
          .getOrElse(UNKNOWN);
    }

    @Override
    public String toString() {
        return name;
    }
}
