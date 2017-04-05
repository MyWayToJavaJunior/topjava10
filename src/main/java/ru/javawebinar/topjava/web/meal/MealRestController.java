package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal get(int id) {
        int userId = AuthorizedUser.id();
        return service.get(id, userId);
    }

    public Meal save(Meal meal) {
         int userId = AuthorizedUser.id();
        return service.save(meal, userId);
    }

    public void delete(int id)  {
        int userId = AuthorizedUser.id();
        service.delete(id, userId);
    }

    public Meal create(Meal meal){
        checkNew(meal);
        int userId = AuthorizedUser.id();
        return service.save(meal, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.id();
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal update(Meal meal, int id)  {
        checkIdConsistent(meal, id);
        int userId = AuthorizedUser.id();
        return service.update(meal, userId);
    }


}
