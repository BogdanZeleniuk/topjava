package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),1);
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),1);

        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 10, 0), "Завтрак админ", 600),2);
        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 13, 0), "Обед админ", 1000),2);
        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 29, 20, 0), "Ужин админ", 500),2);
        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 10, 0), "Завтрак админ", 500),2);
        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 13, 0), "Обед админ", 1000),2);
        save(new UserMeal(LocalDateTime.of(2015, Month.APRIL, 30, 20, 0), "Ужин админ", 500),2);
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        else if (get(userMeal.getId(),userId) == null){
            return null;
        }
        Map<Integer, UserMeal> mapMap = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        mapMap.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer,UserMeal> mealMap = repository.get(userId);
        return mealMap.remove(id) != null && !mealMap.isEmpty();
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<Integer, UserMeal> mealMap = repository.get(userId);
        return mealMap.isEmpty() ? null : mealMap.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        Collection<UserMeal> userMealCollection = repository.get(userId).values();
        List<UserMeal> userMealList = new ArrayList<>();
        userMealList.addAll(userMealCollection);
        Collections.sort(userMealList, new Comparator<UserMeal>() {
            @Override
            public int compare(UserMeal o1, UserMeal o2) {
                return o2.getDateTime().compareTo(o1.getDateTime());
            }
        });
        return userMealList;
    }

    @Override
    public UserMeal getMealById(int id, int userId) {
        return getAll(userId).stream().filter(userMeal -> userMeal.getId()==id).findFirst().orElse(null);
    }
}

