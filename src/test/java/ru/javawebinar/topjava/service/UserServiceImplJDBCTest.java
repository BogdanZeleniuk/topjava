package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.util.exception.NotFoundException;

/**
 * Created by Admin on 04.07.2016.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, "jdbc"})
public class UserServiceImplJDBCTest extends UserServiceTest {
    @Test
    public void testSave() throws Exception {
        super.testSave();
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailSave() throws Exception {
       super.testDuplicateMailSave();
    }

    @Test
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        super.testNotFoundDelete();
    }

    @Test
    public void testGet() throws Exception {
        super.testGet();
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Test
    public void testGetByEmail() throws Exception {
        super.testGetByEmail();
    }

    @Test
    public void testGetAll() throws Exception {
        super.testGetAll();
    }

    @Test
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

}