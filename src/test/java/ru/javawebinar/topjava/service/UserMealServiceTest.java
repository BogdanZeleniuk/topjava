package ru.javawebinar.topjava.service;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserMealServiceTest {

    private static final Logger log = LoggerFactory.logger(UserMealServiceTest.class);

    @Autowired
    protected UserMealService service;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDelete() throws Exception {
        long startTime = System.currentTimeMillis();
        service.delete(MealTestData.MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), service.getAll(USER_ID));
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testDelete() "+time);
        throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        long startTime = System.currentTimeMillis();
        service.delete(MEAL1_ID, 1);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testDeleteNotFound() "+time);
    }

    @Test
    public void testSave() throws Exception {
        long startTime = System.currentTimeMillis();
        UserMeal created = getCreated();
        service.save(created, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testSave() "+time);
        throw new Exception();
    }

    @Test
    public void testGet() throws Exception {
        long startTime = System.currentTimeMillis();
        UserMeal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL, actual);
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testGet() "+time);
        throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        long startTime = System.currentTimeMillis();
        service.get(MEAL1_ID, ADMIN_ID);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testGetNotFound() "+time);
    }

    @Test
    public void testUpdate() throws Exception {
        long startTime = System.currentTimeMillis();
        UserMeal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL1_ID, USER_ID));
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testUpdate() "+time);
        throw new Exception();
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        long startTime = System.currentTimeMillis();
        UserMeal item = service.get(MEAL1_ID, USER_ID);
        service.update(item, ADMIN_ID);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testNotFoundUpdate() "+time);
    }

    @Test
    public void testGetAll() throws Exception {
        long startTime = System.currentTimeMillis();
        MATCHER.assertCollectionEquals(USER_MEALS, service.getAll(USER_ID));
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("time of testGetAll() "+time);
        throw new Exception();
    }

    @Test
    public void testGetBetween() throws Exception {
        long startTime = System.currentTimeMillis();
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),
                service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID));
        thrown.expect(Exception.class);
        long endTime = System.currentTimeMillis();
        long time = endTime-startTime;
        log.info("testGetBetween() "+time);
        throw new Exception();
    }

}