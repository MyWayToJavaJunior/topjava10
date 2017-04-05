package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.USER_ID;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer,Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(m -> save(m,USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        Map<Integer, Meal> tmpMeal = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        tmpMeal.put(meal.getId(), meal);
        repository.putIfAbsent(userId, tmpMeal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        boolean result = false;
        if(repository.containsKey(userId)){
            Map<Integer, Meal> tmpMeal = repository.get(userId);
            tmpMeal.remove(id);
            repository.put(userId, tmpMeal);
            result = true;
        }
        return result;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> tmpMeal = new ConcurrentHashMap<>();
        tmpMeal = repository.get(userId);
        return tmpMeal.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> tmpMeal = repository.get(userId);
        Map<Integer, Meal> tmpMeal1 = tmpMeal.entrySet()
                .stream()
                .sorted(Comparator.comparing(e -> e.getValue().getDateTime().toLocalTime()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return tmpMeal1.values();
    }
}

