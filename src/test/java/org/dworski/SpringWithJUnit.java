package org.dworski;

import org.apache.log4j.Logger;
import org.dworski.dao.EmployeeDao;
import org.dworski.model.Employee;
import org.hibernate.SessionFactory;
import org.hibernate.exception.SQLGrammarException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public class SpringWithJUnit {

    private static final Logger logger = Logger.getLogger(SpringWithJUnit.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EmployeeDao employeeDao;

    @BeforeTransaction
    public void beforeTransaction() {
        logger.info("Before transaction " + new Date());
    }

    @AfterTransaction
    public void afterTransaction() {
        logger.info("After transaction " + new Date());
    }

    @Test
    public void shouldSessionFactoryBeInjected() throws Exception {
        // given
        // when
        // then
        assertNotNull(sessionFactory);
    }

    @Test
    @Transactional
    public void shouldEmployeeBeSaved() throws Exception {
        // given
        String firstName = "Peter";
        String lastName = "Griffin";
        Employee employee = new Employee(firstName, lastName);
        // when
        long savedEntityId = employeeDao.save(employee);
        sessionFactory.getCurrentSession().flush();
        // then
        assertTrue(savedEntityId != 0);
        Employee fetchedEntity = employeeDao.read(savedEntityId);
        assertNotNull(fetchedEntity);
        assertEquals(firstName, fetchedEntity.getFirstName());
        assertEquals(lastName, fetchedEntity.getLastName());
    }

    @Test
    @DirtiesContext
    @Transactional
    public void shouldCountAllEmployees() throws Exception {
        // given
        // when
        int count = employeeDao.countEmployees();
        // then
        assertEquals(0, count);
    }

    @Test
    @ExpectedException(SQLGrammarException.class)
    @Timed(millis = 5000)
    @Repeat(1)
    @Transactional
    @DirtiesContext
    @Rollback(false)
    public void shouldThrowException() throws Exception {
        // given
        // when
        sessionFactory.getCurrentSession().createSQLQuery("This is not a correct query").executeUpdate();
    }
}
