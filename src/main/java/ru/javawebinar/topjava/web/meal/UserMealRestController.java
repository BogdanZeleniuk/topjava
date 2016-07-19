package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@RestController
public class UserMealRestController extends AbstractUserMealController {


    @RequestMapping(value = "/rest/meals/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserMeal get(@PathVariable ("id") int id) {
        return super.get(id);
    }

    @RequestMapping(value = "/rest/meals/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable ("id") int id) {
        super.delete(id);
    }

    @RequestMapping(value = "/rest/meals", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserMealWithExceed> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/rest/meals/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody UserMeal meal, @PathVariable ("id") int id) {
        super.update(meal, id);
    }

    @RequestMapping(value = "/rest/meals", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserMeal> createWithLocation(@RequestBody UserMeal meal) {
        UserMeal create = super.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/rest/meals" + "/{id}")
                .buildAndExpand(create.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(create);
    }

    @RequestMapping(value = "/rest/meals/between", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    public List<UserMealWithExceed> getBetween(@RequestParam ("startDate") LocalDate startDate, @RequestParam ("startTime") LocalTime startTime,
                                               @RequestParam ("endDate") LocalDate endDate, @RequestParam ("endTime") LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}