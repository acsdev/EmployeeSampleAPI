package br.model.services;

import br.model.Connection;
import br.model.entities.Employee;

import java.util.List;

public class EmployeeService implements BaseService<Employee> {

    @Override
    public void save( Employee e) {

        Connection.get().save( e );

        if ( e.getDirectReports() != null ) {

            e.getDirectReports().forEach( dr -> save( dr ) );
        }

    }

    @Override
    public void delete( Employee e) {

        Connection.get().delete( e );
    }

    @Override
    public List<Employee> findAll() {

        return Connection.get().find(Employee.class).asList();
    }

}
