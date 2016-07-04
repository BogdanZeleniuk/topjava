package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Admin on 04.07.2016.
 */
@ActiveProfiles({Profiles.ACTIVE_DB, "jdbc"})
public class UserMealServiceImplJDBCTest extends UserMealServiceTest {

    @Test
    public void testDelete() throws Exception {
        super.testDelete();
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        super.testDeleteNotFound();
    }

    @Test
    public void testSave() throws Exception {
        super.testSave();
    }

    @Test
    public void testGet() throws Exception {
        super.testGet();
    }

    @Test
    public void testGetNotFound() throws Exception {
        super.testGetNotFound();
    }

    @Test
    public void testUpdate() throws Exception {
        super.testUpdate();
    }

    @Test
    public void testNotFoundUpdate() throws Exception {
        super.testNotFoundUpdate();
    }

    @Test
    public void testGetAll() throws Exception {
       super.testGetAll();
    }

    @Test
    public void testGetBetween() throws Exception {
        super.testGetBetween();
    }
}