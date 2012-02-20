package org.dworski.model;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "EMPLOYEE_SEQ", initialValue = 1, allocationSize = 1, sequenceName = "employee_seq")
@Table(name ="Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_SEQ")
    private long id;
    @Column(length = 100)
    private String firstName;
    @Column(length = 100)
    private String lastName;

    public Employee(){

    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
