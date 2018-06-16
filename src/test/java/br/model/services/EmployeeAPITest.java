package br.model.services;

import br.CompanySampleRoutes;
import br.model.entities.Employee;
import br.model.util.ResponseData;
import br.model.util.Util;
import org.junit.*;
import spark.Spark;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeAPITest {

    @BeforeClass
    public static void setup() {

        CompanySampleRoutes.main(new String[]{});

        Spark.awaitInitialization();
    }

    @AfterClass
    public static void setdown() {

        Spark.stop();
    }

    @Before
    public void prepare() {
        EmployeeService service = new EmployeeService();
        service.findAll()
                .stream()
                .filter( e -> e.getName().startsWith("TST") )
                .forEach( e -> service.delete(e) );

    }

    @Test
    public void sayHello() {

        ResponseData responseData =
                HttpRequest.prepare(CompanySampleRoutes.HOST, CompanySampleRoutes.PORT)
                        .doRequest("GET", "hello");

        Assert.assertEquals( 200, responseData.getStatus() );
    }


    @Test
    public void save() {

        Employee manager = new Employee();
        manager.setName( "TST.Manager" );
        manager.setSalary( new BigDecimal(3500.0));

        Employee employee = new Employee();
        employee.setName( "TST.Employee" );
        employee.setSalary( new BigDecimal(2000.0));
        manager.addDirectReport( employee );

        Employee directReported01 = new Employee();
        directReported01.setName( "TST.DR01" );
        directReported01.setSalary( new BigDecimal(1700.0));
        employee.addDirectReport( directReported01 );

        Employee directReported02 = new Employee();
        directReported02.setName( "TST.DR02" );
        directReported02.setSalary( new BigDecimal(1700.0));
        employee.addDirectReport( directReported02 );

        String asJson = Util.getAsJson( manager );

        ResponseData responseData =
                HttpRequest.prepare(CompanySampleRoutes.HOST, CompanySampleRoutes.PORT)
                        .doRequest("POST", "employee", asJson);

        Assert.assertEquals( 200, responseData.getStatus() );

    }

    @Test
    public void findAll() {

        Employee manager = new Employee();
        manager.setName( "TST.Manager" );
        manager.setSalary( new BigDecimal(3500.0));

        Employee employee = new Employee();
        employee.setName( "TST.Employee" );
        employee.setSalary( new BigDecimal(2000.0));
        manager.addDirectReport( employee );

        Employee directReported01 = new Employee();
        directReported01.setName( "TST.DR01" );
        directReported01.setSalary( new BigDecimal(1700.0));
        employee.addDirectReport( directReported01 );

        Employee directReported02 = new Employee();
        directReported02.setName( "TST.DR02" );
        directReported02.setSalary( new BigDecimal(1700.0));
        employee.addDirectReport( directReported02 );

        String asJson = Util.getAsJson( manager );

        ResponseData responseDataSave =
                HttpRequest.prepare(CompanySampleRoutes.HOST, CompanySampleRoutes.PORT)
                        .doRequest("POST", "employee", asJson);

        Assert.assertEquals( 200, responseDataSave.getStatus() );

        ResponseData responseDataList =
                HttpRequest.prepare(CompanySampleRoutes.HOST, CompanySampleRoutes.PORT)
                        .doRequest("GET", "/employee");

        Assert.assertEquals( 200, responseDataList.getStatus() );

        List<Employee> e = Util.getListAsJson( responseDataList.getBody(), Employee.class );

        Assert.assertNotNull( e );
        Assert.assertEquals( 4 , e.size());

    }
}
