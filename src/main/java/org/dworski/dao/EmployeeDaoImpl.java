package org.dworski.dao;

import org.dworski.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Service("employeeDao")
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private SessionFactory sessionFactory;

    public long save(Employee employee) {
        return (Long) sessionFactory.getCurrentSession().save(employee);
    }

    public Employee read(long id) {
        return (Employee) sessionFactory.getCurrentSession().load(Employee.class, id);
    }

    public int countEmployees() {
        return (Integer) sessionFactory.getCurrentSession().createSQLQuery("select count(*) as result from Employee").addScalar("result", IntegerType.INSTANCE).uniqueResult();
    }
}
