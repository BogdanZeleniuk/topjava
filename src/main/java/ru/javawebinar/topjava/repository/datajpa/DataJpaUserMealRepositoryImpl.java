package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

/**
 * GKislin
 * 27.03.2015.
 */
@Repository
public class DataJpaUserMealRepositoryImpl implements UserMealRepository{

    @Autowired
    private ProxyUserMealRepository proxyUserMealRepository;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        if (!userMeal.isNew() && get(userMeal.getId(),userId) == null){
            return null;
        }
        else {
            userMeal.setUser(manager.getReference(User.class,userId));
            proxyUserMealRepository.save(userMeal);
        }
        return userMeal;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return manager.createNamedQuery(UserMeal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        List<UserMeal> list = manager.createNamedQuery(UserMeal.GET)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        return manager.createNamedQuery(UserMeal.ALL_SORTED)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return manager.createNamedQuery(UserMeal.GET_BETWEEN)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("userId", userId)
                .getResultList();
    }
}
