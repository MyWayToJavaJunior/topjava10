package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );



        List<UserMealWithExceed> test = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        test.forEach(System.out::println);

    }

    //cycle with for-each
    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        //count of per day calories
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        for (UserMeal u : mealList) {
            //my version
            /*dayCalories.put(u.getDateTime().toLocalDate(),
                    dayCalories.get(u.getDateTime().toLocalDate()) == null ? u.getCalories() : dayCalories.get(u.getDateTime().toLocalDate()) + u.getCalories());*/

           //merge version
            dayCalories.merge(u.getDateTime().toLocalDate(),
                    u.getCalories(),(a,b)-> a+b);
        }

        //convert UserMeal to UserMealWithExceed
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal u : mealList) {
            if (TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime)) {
                result.add(new UserMealWithExceed(
                        u.getDateTime(),
                        u.getDescription(),
                        u.getCalories(),
                        dayCalories.get(u.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return result;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededWithStreams(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay){
        //count of per day calories
       /* Map<LocalDate, Integer> dayCalories = mealList.stream()
                .collect(Collectors.toMap(x -> x.getDateTime().toLocalDate(),
                                            UserMeal::getCalories,
                                            (a, b) -> a+b) );*/
        Map<LocalDate, Integer> dayCalories = mealList.stream()
                .collect(Collectors.toMap(x -> x.getDateTime().toLocalDate(),
                        UserMeal::getCalories,
                        Integer::sum ));

        //convert UserMeal to UserMealWithExceed
        return mealList.stream()
                .filter(s -> TimeUtil.isBetween(s.getDateTime().toLocalTime(),startTime,endTime))
                .map(s -> new UserMealWithExceed(
                        s.getDateTime(),
                        s.getDescription(),
                        s.getCalories(),
                        dayCalories.get(s.getDateTime().toLocalDate()) >= caloriesPerDay))
                .collect(Collectors.toList());
    }
}
