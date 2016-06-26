package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.BaseEntity;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final UserMeal USER_MEAL_1 = new UserMeal(100002, LocalDateTime.of(2016, Month.JUNE,25,9,0),
            "breakfast",300);
    public static final UserMeal USER_MEAL_2 = new UserMeal(100003, LocalDateTime.of(2016, Month.JUNE,25,12,20),
            "lunch",200);
    public static final UserMeal USER_MEAL_3 = new UserMeal(100004, LocalDateTime.of(2016, Month.JUNE,25,15,15),
            "dinner",500);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

}
