package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;

import java.time.Month;
import java.util.Collection;


import static ru.javawebinar.topjava.MealTestData.*;
/**
 * Created by Alex on 11.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        int userId = 100000;
        LocalDateTime startTime = LocalDateTime.of(2015, Month.MAY, 30, 01, 0);
        LocalDateTime endTime = LocalDateTime.of(2015, Month.MAY, 30, 23, 0);
        InMemoryMealRepositoryImpl testFilter = new InMemoryMealRepositoryImpl();
        MATCHER.assertCollectionEquals(testFilter.getBetween(startTime, endTime, userId), service.getBetweenDateTimes(startTime, endTime, userId));
    }

    @Test
    public void update() throws Exception {
        int userId = 100000;
        Meal updated = new Meal(LocalDateTime.now(), "test meal", 1000);
        updated.setCalories(100);
        updated.setDescription("updated");
        service.update(updated, userId);
        MATCHER.assertEquals(updated, service.get(updated.getId(),userId));
    }


    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, 100000);
    }

    @Test(expected = NotFoundException.class)
    public void getAlienMeal() throws Exception {
        service.get(1,10001);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlienMeal() throws Exception {
        service.delete(1,10001);
    }

    @Test
    public void getAll() throws Exception {
        int userId = 100000;
        Collection<Meal> all = service.getAll(userId);
        MATCHER.assertCollectionEquals(new InMemoryMealRepositoryImpl().getAll(userId), all);
    }



    @Test
    public void save() throws Exception {
        int userId = 100000;
        Meal newMeal = new Meal( LocalDateTime.now(), "test meal", 1000);
        Meal created = service.save(newMeal, userId);
        newMeal.setId(created.getId());
        MATCHER.assertEquals(newMeal, service.get(newMeal.getId(),userId));
    }
}