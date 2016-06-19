package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, User> repository = new ConcurrentHashMap();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(1, "user", "user@ukr.net", "password", Role.ROLE_USER));
        save(new User(2, "admin", "admin@ukr.net", "adminPassword", Role.ROLE_ADMIN));
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id)!=null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(),user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        List<User> list = repository.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        Collections.sort(list, (User o1, User o2) -> {
            String nameOne = o1.getName();
            String nameTwo = o2.getName();
            return nameOne.compareTo(nameTwo);
        });
        return list;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return getAll().stream().filter(user -> email.equals(user.getEmail())).findFirst().orElse(null);
    }
}
