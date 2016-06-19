package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {

    @Autowired
    private UserMealRepository repository;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        return repository.save(userMeal,userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ExceptionUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public UserMeal get(int id, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public UserMeal getMealById(int id, int userId) throws NotFoundException {
        return ExceptionUtil.checkNotFoundWithId(repository.getMealById(id, userId), id);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return (List<UserMeal>) repository.getAll(userId);
    }

    @Override
    public void update(UserMeal userMeal, int userId) {
        repository.save(userMeal, userId);
    }

    @Override
    public List<UserMeal> getFilteredList(LocalDateTime startTime, LocalDateTime endTime, int userId) {
        Objects.requireNonNull(startTime);
        Objects.requireNonNull(endTime);
        return getAll(userId).stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime.toLocalTime(),endTime.toLocalTime()))
                .sorted(new Comparator<UserMeal>() {
                    @Override
                    public int compare(UserMeal o1, UserMeal o2) {
                        return o2.getDateTime().compareTo(o1.getDateTime());
                    }
                })
                .collect(Collectors.toList());
    }
}
