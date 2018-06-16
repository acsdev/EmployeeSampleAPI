package br.model.entities;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Indexes(
    @Index(value = "salary", fields=@Field("salary"))
)
public class Employee extends Base implements Serializable {

    private String name;

    @Reference
    private List<Employee> directReports;

    @Property("wage")
    private BigDecimal salary;

    public Employee() {
        this.setId( new ObjectId() );
    }

    public Employee(String name, BigDecimal salary) {
        this();
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getDirectReports() {
        if (directReports == null) return null;
        return new ArrayList<>(directReports); // DEFENSE PROGRAMING
    }

    public void setDirectReports(List<Employee> directReports) {
        this.directReports = directReports;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Employee addDirectReport(Employee e) {

        this.directReports = Optional.ofNullable(this.directReports).orElse(new ArrayList<>());

        this.directReports.add( e );

        return e;
    }
}

