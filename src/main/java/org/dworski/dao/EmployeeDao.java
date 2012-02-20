package org.dworski.dao;

import org.dworski.model.Employee;

public interface EmployeeDao {

    public long save(Employee employee);
    public Employee read(long id);

    int countEmployees();
}
