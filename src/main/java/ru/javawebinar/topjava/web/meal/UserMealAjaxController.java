package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by Admin on 25.07.2016.
 */
@RestController
@RequestMapping(value = "/ajax/profile/meals")
public class UserMealAjaxController extends AbstractUserMealController{

    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createOrUpdate(@RequestParam(value = "id") int id,
                               @RequestParam(value = "dateTime") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dateTime,
                               @RequestParam(value = "description") String description,
                               @RequestParam(value = "calories") int calories) {
        UserMeal meal = new UserMeal (id, dateTime, description, calories);
        if (id == 0){
            super.create(meal);
        }
        else {
            super.update(meal, id);
        }
    }

    @Override
    @RequestMapping(value = "/filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getBetween(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "startTime", required = false) LocalTime startTime,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "endTime", required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
