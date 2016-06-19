package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMealService service;

    public List<UserMeal> getAll(int userId) {
        LOG.info("getAll");
        return service.getAll(userId);
    }

    public UserMeal get(int id, int userId) {
        LOG.info("get " + id);
        return service.get(id, userId);
    }

    public UserMeal create(UserMeal userMeal, int userId) {
        userMeal.setId(null);
        LOG.info("create " + userMeal);
        return service.save(userMeal, userId);
    }

    public void delete(int id, int userId) {
        LOG.info("delete " + id);
        service.delete(id, userId);
    }

    public void update(UserMeal userMeal, int id) {
        userMeal.setId(id);
        LOG.info("update " + userMeal);
        service.update(userMeal, id);
    }

    public UserMeal getMealById(int id, int userId) {
        LOG.info("getByEmail");
        return service.getMealById(id, userId);
    }

    public List<UserMealWithExceed> getFilteredList(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int userId) {
        LOG.info("getFilteredList");
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        return UserMealsUtil.getFilteredWithExceeded(service.getFilteredList(LocalDateTime.of(startDate, LocalTime.MIN), LocalDateTime.of(endDate, LocalTime.MAX),userId),startTime,endTime,UserMealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

}
