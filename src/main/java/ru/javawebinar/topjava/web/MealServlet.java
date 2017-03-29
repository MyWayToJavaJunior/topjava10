package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealDao;
import ru.javawebinar.topjava.DAO.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private MealDao memory = MealDaoImpl.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals.jsp");

        List<MealWithExceed> mealsList = MealsUtil.getFilteredWithExceeded(memory.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000);
        String action = req.getParameter("action");
       if (action == null){
           req.setAttribute("mealsList", mealsList);
           req.getRequestDispatcher("/meals.jsp").forward(req, resp);
       }
       else if (action.equalsIgnoreCase("update")){
           int id = Integer.parseInt(req.getParameter("id"));
           Meal m = memory.getMeal(id);
           req.setAttribute("meals", m);
           req.getRequestDispatcher("/addMeal.jsp").forward(req,resp);
        }
       else if (action.equalsIgnoreCase("delete")){
            int id = Integer.parseInt(req.getParameter("id"));
            memory.deleteMeal(id);
            req.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(memory.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000));
           req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
        else{
           req.getRequestDispatcher("/addMeal.jsp").forward(req,resp);
       }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));

        if(id == null || id.isEmpty()){
            memory.addMeal(meal);
        } else{
            meal.setId(Integer.valueOf(id));
            memory.updateMeal(Integer.valueOf(id), meal);
        }

        req.setAttribute("mealsList", MealsUtil.getFilteredWithExceeded(memory.getAllMeal(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
