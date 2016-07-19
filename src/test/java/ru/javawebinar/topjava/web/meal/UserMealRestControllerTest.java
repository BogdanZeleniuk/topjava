package ru.javawebinar.topjava.web.meal;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by Admin on 19.07.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest {

    @Autowired
    UserMealService userMealService;

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get("/rest/meals/"+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/rest/meals/"+MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), userMealService.getAll(START_SEQ));
    }

    @Test
    public void testGetAll() throws Exception {

        mockMvc.perform(get("/rest/meals"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), userMealService.getAll(START_SEQ));
    }

    @Test
    public void testUpdate() throws Exception {

        UserMeal meal = new UserMeal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        meal.setCalories(200);
        meal.setDescription("Обновленный завтрак");

        mockMvc.perform(put("/rest/meals/"+MEAL1_ID)
                .content(JsonUtil.writeValue(meal))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertEquals(getUpdated(), userMealService.get(MEAL1_ID, START_SEQ));
    }

    @Test
    public void testCreateWithLocation() throws Exception {

        UserMeal meal = getCreated();

        ResultActions actions = mockMvc.perform(post("/rest/meals")
                .content(JsonUtil.writeValue(meal)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        UserMeal userMeal = MATCHER.fromJsonAction(actions);
        meal.setId(userMeal.getId());

        MATCHER.assertEquals(meal,userMeal);
        MATCHER.assertCollectionEquals(Arrays.asList(meal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), userMealService.getAll(START_SEQ));

    }

    @Test
    public void testGetBetween() throws Exception {

        mockMvc.perform(get("/rest/meals/between"))
                .andExpect(status().isOk())
                .andDo(print());

        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1),userMealService.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.MAX, START_SEQ));
    }

}