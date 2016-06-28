package ru.javawebinar.topjava.repository.jpa;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User: gkisline
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JpaUserMealRepositoryImpl implements UserMealRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public UserMeal save(UserMeal userMeal, int userId){
        if (userMeal.isNew()) {
            User ref = entityManager.getReference(User.class, userId);
            userMeal.setUser(ref);
            entityManager.persist(userMeal);
            return userMeal;
        }else {
            if (entityManager.createNamedQuery(UserMeal.UPDATE)
                .setParameter("dateTime",userMeal.getDateTime())
                .setParameter("description",userMeal.getDescription())
                .setParameter("calories",userMeal.getCalories())
                .setParameter("id",userMeal.getId())
                .setParameter("userId",userId)
                .executeUpdate() == 0)
                return null;
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(UserMeal.DELETE).setParameter("id",id).setParameter("userId",userId).executeUpdate() !=0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> userMeals = entityManager.createNamedQuery(UserMeal.GET_MEAL)
                .setParameter("id",id)
                .setParameter("userId",userId)
                .getResultList();
        if (userMeals.size()==1 && userMeals.stream().iterator().next().getUser().getId()==userId) {
            return entityManager.find(UserMeal.class, id);
        }
        return null;
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return entityManager.createNamedQuery(UserMeal.ALL_SORTED,UserMeal.class)
                .setParameter("userId",userId)
                .getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return entityManager.createNamedQuery(UserMeal.BETWEEN,UserMeal.class)
                .setParameter("1",startDate)
                .setParameter("2",endDate).getResultList();
    }

}