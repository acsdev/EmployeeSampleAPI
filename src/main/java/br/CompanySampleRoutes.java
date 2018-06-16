package br;

import br.model.entities.Employee;
import br.model.services.EmployeeService;
import br.model.util.ResponseData;
import br.model.util.Util;
import spark.Spark;

import java.util.List;


public class CompanySampleRoutes {

    public static final String HOST = "localhost";

    public static final int PORT = 9000;

    public static void main(String[] args) {

        Spark.ipAddress( HOST );

        Spark.port( PORT );

        Spark.after(((request, response) -> response.type("application/json")));

        Spark.get("/hello", (request, response) -> "br.CompanySampleRoutes Word !" );

        Spark.get("/employee", (request, response) -> {

            List<Employee> all = new EmployeeService().findAll();

            String asJson = Util.getListAsJson( all );

            return asJson;
        });

        Spark.post("/employee", (request, response) -> {

            String json = request.body();

            Employee asObject = Util.getAsObject(json, Employee.class);

            new EmployeeService().save( asObject );

            return "SUCCESS";
        });

        Spark.exception(IllegalStateException.class, (e, request, response) -> {
            response.status( 400 );
            response.body( e.getMessage() );
        });

        Spark.exception(RuntimeException.class, (e, request, response) -> {
            response.status( 500 );
            response.body( "Unexpected error!" );
        });

    }
}
