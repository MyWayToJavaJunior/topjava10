package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Alex on 25.03.2017.
 */
public class MealDaoImpl implements MealDao{
    //our container in memory and auto-counter
    private  Map<Integer, Meal> mealInMemory = new ConcurrentHashMap<>();
    private  AtomicInteger count = new AtomicInteger(0);

    //initialize our start Meal list from MealsUtil. Used singleton.
    private static MealDaoImpl instance;
    private MealDaoImpl(){}

    public static MealDaoImpl getInstance(){
        if(instance == null){
            instance = new MealDaoImpl();
            for(Meal m : MealsUtil.getMealsList()){
                instance.addMeal(m);
            }
        }
        return instance;
    }

    //add new Meal
    public void addMeal(Meal m){
        if (mealInMemory.containsKey(m.getId())){
            mealInMemory.put(m.getId(),m);
        } else{
            m.setId(getNewId());
            mealInMemory.put(m.getId(), m);
        }
    }
    /*public void addMeal(Meal m){
        if (m.getId() == 0){
            m.setId(getNewId());
        } else{
            mealInMemory.put(m.getId(), m);mealInMemory.put(m.getId(),m);
        }
    }*/

    //update Meal, only for method update in MealServlet
    public void updateMeal(int id, Meal m){
        mealInMemory.put(id, m);
    }

    //return Meal by id
    public Meal getMeal(int id){
        return mealInMemory.get(id);
    }

    //delete Meal by id
    public void deleteMeal(int id){
        mealInMemory.remove(id);
    }

    //return our start values from MealsUtil.class
    public Collection<Meal> getAllMeal(){
        return mealInMemory.values();
    }

    //return new id for new Meal
    public  int getNewId(){return count.incrementAndGet();}


}
