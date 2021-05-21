package co.com.employee;

import co.com.employee.domain.ports.EmployeeProvider;
import co.com.employee.infrastructure.acl.employees.EmployeeProviderClient;
import com.google.inject.AbstractModule;

import java.time.Clock;
import java.time.ZoneId;

public class StartModule extends AbstractModule {

    @Override
    public void configure() {
        bind(Clock.class).toInstance(Clock.system(ZoneId.of("America/Bogota")));
        bind(EmployeeProvider.class).to(EmployeeProviderClient.class).asEagerSingleton();
    }
}

