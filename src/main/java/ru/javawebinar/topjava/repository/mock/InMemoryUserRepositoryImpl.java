package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete user" + id);
        boolean result = false;
        if(repository.containsKey(id)){
            repository.remove(id);
            result = true;
        }
        return result;
    }

    @Override
    public User save(User user) {
        LOG.info("save user" + user);
        if(!repository.containsValue(user.getName())){
            if(user.isNew()){
                user.setId(counter.incrementAndGet());
            }
            repository.put(user.getId(), user);
        }
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get user" + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll users");
        List<User> result = Collections.emptyList();
        if(!repository.isEmpty()){
            result = (List<User>)(repository.values());
        }
        return result;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail user" + email);
        return null;
    }
}
