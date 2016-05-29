package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.security.KeyStore;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = new HashMap<>();
        List<UserMeal> list = mealList;
        for (int i=0; i<list.size(); i++){
            int sum = 0;
            LocalDate localDate = list.get(i).getDateTime().toLocalDate();
            for (int j=0; j<list.size(); j++){
                if(list.get(j).getDateTime().toLocalDate().equals(localDate)){
                    sum+=list.get(j).getCalories();
                }
            }
            if (!map.containsKey(localDate)) {
                map.put(localDate, sum);
            }
        }
        List<UserMealWithExceed> userMealWithExceedList = new ArrayList<>();
        for (Map.Entry<LocalDate,Integer> entry : map.entrySet()){
            LocalDate key = entry.getKey();
            Integer value = entry.getValue();
            for (UserMeal um : list){
                if (um.getDateTime().toLocalDate().equals(key) &&
                        TimeUtil.isBetween(um.getDateTime().toLocalTime(),startTime,endTime) && value>caloriesPerDay){
                    userMealWithExceedList.add(new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),false));
                }
                else if (um.getDateTime().toLocalDate().equals(key) &&
                        TimeUtil.isBetween(um.getDateTime().toLocalTime(),startTime,endTime) && value<=caloriesPerDay){
                    userMealWithExceedList.add(new UserMealWithExceed(um.getDateTime(),um.getDescription(),um.getCalories(),true));
                }
            }
        }

        return userMealWithExceedList;
    }
}