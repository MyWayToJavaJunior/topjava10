package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * Created by Alex on 28.03.2017.
 */
public interface MealDao {

    //add new Meal
    public void addMeal(Meal m);

    //update Meal, only for method update in MealServlet
    public void updateMeal(int id, Meal m);

    //return Meal by id
    public Meal getMeal(int id);

    //delete Meal by id
    public void deleteMeal(int id);

    //return our start values from MealsUtil.class
    public Collection<Meal> getAllMeal();

    //return new id for new Meal
    public  int getNewId();
}
